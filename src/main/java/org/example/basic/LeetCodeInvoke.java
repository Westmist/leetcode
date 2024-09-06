package org.example.basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.basic.convert.ConvertUtil;
import org.example.basic.convert.ano.Answer;
import org.example.basic.convert.ano.Convert;
import org.example.basic.convert.ano.Params;
import org.example.basic.convert.ano.Title;
import org.example.basic.convert.entity.MethodParams;
import org.example.basic.convert.inf.IConvertSection;
import org.example.basic.convert.inf.IMethodFilter;
import org.example.basic.section.SectionFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class LeetCodeInvoke {

    private static ObjectMapper MAPPER = new ObjectMapper();

    public static void invoke(Class<?> aClass) {
        Set<String> parentMethods = Arrays.stream(aClass.getSuperclass().getMethods()).map(Method::getName).collect(Collectors.toSet());
        invoke(aClass, allMethods -> {
            // 过滤掉父类的方法
            return Arrays.stream(allMethods).filter(method -> !parentMethods.contains(method.getName()) && method.isAnnotationPresent(Answer.class)).toArray(Method[]::new);
        });
    }

    public static void invoke(Class<?> aClass, String methodName) {
        invoke(aClass, allMethods -> Arrays.stream(allMethods).filter(method -> method.getName().equals(methodName)).toArray(Method[]::new));
    }

    public static void invoke(Class<?> aClass, IMethodFilter filter) {
        Object instance;
        try {
            Constructor<?> constructor = aClass.getDeclaredConstructor();
            instance = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        Method[] methods = filter.filterMethod(aClass.getMethods());

        Title title = aClass.getAnnotation(Title.class);
        String name = title == null ? "UNKNOWN" : title.value();
        log.info("开始测试 --------- {} - {} ---------", name, aClass.getSimpleName());
        for (Method method : methods) {
            doInvoke(instance, method);
        }
        log.info("\n");
    }

    public static void doInvoke(Object instance, Method method) {
        Object[] methodParams = buildParamObj(method);
        Answer answerAno = method.getAnnotation(Answer.class);
        Class<? extends IConvertSection> sClazz = answerAno.section();
        IConvertSection section = SectionFactory.find(sClazz);
        MethodParams mp = section.paramsAns(methodParams);
        Object commit = buildCommit(instance, method, mp);
        Object ans = buildAns(method, methodParams);
        match(ans, commit, method);
    }

    /**
     * 构建方法参数
     */
    private static Object[] buildParamObj(Method method) {
        // 参数类型列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        // 实参数据列表
        Params params = method.getAnnotation(Params.class);
        return ConvertUtil.parse(parameterTypes, params);
    }

    /**
     * 构建执行结果
     */
    private static Object buildCommit(Object instance, Method method, MethodParams mp) {
        Answer answerAno = method.getAnnotation(Answer.class);
        // 执行结果
        Object invokeR;
        try {
            invokeR = method.invoke(instance, mp.invokeParams());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return mp.commit() == null ? invokeR : mp.commit();
    }

    /**
     * 构建正确答案
     */
    private static Object buildAns(Method method, Object[] params) {
        Answer answerAno = method.getAnnotation(Answer.class);
        Convert annoC = answerAno.c();
        Class<?> ansType = method.getReturnType();
        return ConvertUtil.parse(ansType, annoC);
    }


    private static boolean match(Object ans, Object commit, Method method) {
        String methodName = method.getName();
        try {
            String ansStr = MAPPER.writeValueAsString(ans);
            String resultStr = MAPPER.writeValueAsString(commit);

            Title title = method.getAnnotation(Title.class);
            if (ansStr.equals(resultStr)) {
                log.info("{} - {}  : Answer Accept", title.value(), methodName);
                return true;
            } else {
                log.error("\n\t--------------------------------------------\n\t" +
                        "\t{} - {}  : Wrong Accept!\n" +
                        "\t--------------------------------------------\t", title.value(), methodName);
                return false;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}

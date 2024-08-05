package org.example.basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.comom.linkednode.TwoTuple;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LeetCodeInvoke {

    private static ObjectMapper MAPPER = new ObjectMapper();

    public static void invoke(Class<?> aClass) {
        Set<String> parentMethods = Arrays.stream(aClass.getSuperclass().getMethods())
                .map(Method::getName)
                .collect(Collectors.toSet());
        invoke(aClass, allMethods -> {
            // 过滤掉父类的方法
            return Arrays.stream(allMethods)
                    .filter(method -> !parentMethods.contains(method.getName())
                            && method.isAnnotationPresent(Param.class))
                    .toArray(Method[]::new);
        });
    }

    public static void invoke(Class<?> aClass, String methodName) {
        invoke(aClass, allMethods -> Arrays.stream(allMethods)
                .filter(method -> method.getName().equals(methodName))
                .toArray(Method[]::new));
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
        for (Method method : methods) {
            doInvoke(instance, method);
        }
    }

    public static void doInvoke(Object instance, Method method) {
        TwoTuple<Object[], Object[]> twoTuple = buildParamObj(method);
        Object commit = buildCommit(instance, method, twoTuple);
        Object ans = buildAns(method, twoTuple);
        match(ans, commit, method);
    }


    /**
     * 构建方法参数
     */
    private static TwoTuple<Object[], Object[]> buildParamObj(Method method) {
        // 参数类型列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        // 实参数据列表
        Param annotation = method.getAnnotation(Param.class);
        String[] valueStr = annotation.value();

        if (parameterTypes.length != valueStr.length) {
            System.out.println(new IllegalAccessException().getMessage());
        }

        Object[] parameObj = new Object[parameterTypes.length];
        Class[] convert = annotation.convert();
        Object[] hideListParam = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            if (convert.length > i && ConvertFactory.COVERTS.containsKey(convert[i])) {
                IConvert<?> iConvert = ConvertFactory.COVERTS.get(convert[i]);
                Class[] generic = annotation.genericType();
                Object convertRet = iConvert.convert(valueStr[i], generic.length > i ? generic[i] : Void.class);
                int[] ints = annotation.cvtIndex();
                if (ints.length > i) {
                    if (convertRet.getClass().isArray()) {
                        Object[] array = (Object[]) convertRet;
                        parameObj[i] = array[ints[i]];
                        hideListParam[i] = array[0];
                    }
                    if (List.class.isAssignableFrom(convertRet.getClass())) {
                        List list = (List) convertRet;
                        parameObj[i] = list.get(ints[i]);
                        hideListParam[i] = list.get(0);
                    }
                } else {
                    parameObj[i] = convertRet;
                }
                continue;
            }
            if (parameterTypes[i] == String.class) {
                parameObj[i] = valueStr[i];
                continue;
            }
            try {
                Object pObj = MAPPER.readValue(valueStr[i], parameterTypes[i]);
                parameObj[i] = pObj;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return new TwoTuple<>(parameObj, hideListParam);
    }

    /**
     * 构建正确答案
     */
    private static Object buildAns(Method method, TwoTuple<Object[], Object[]> params) {
        Answer answerAno = method.getAnnotation(Answer.class);
        Class<?> ansType = method.getReturnType();
        Class<?> resultConvert = answerAno.convert();

        if (answerAno.matchPattern() == MatchPattern.PARAM_ONE) {
            return params.first()[0];
        }

        // 预期的答案
        Object ans;
        try {
            if (resultConvert != Void.class && ConvertFactory.COVERTS.containsKey(resultConvert)) {
                IConvert<?> iConvert = ConvertFactory.COVERTS.get(resultConvert);
                ans = iConvert.convert(answerAno.value(), answerAno.genericType());
            } else if (ansType == String.class) {
                ans = answerAno.value();
            } else {
                ans = MAPPER.readValue(answerAno.value(), ansType);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }


    /**
     * 构建执行结果
     */
    private static Object buildCommit(Object instance, Method method, TwoTuple<Object[], Object[]> twoTuple) {
        // 执行结果
        Object invokeR;
        try {
            invokeR = method.invoke(instance, twoTuple.first());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        Answer answerAno = method.getAnnotation(Answer.class);
        return switch (answerAno.matchPattern()) {
            case MatchPattern.RESULT -> invokeR;
            case MatchPattern.PARAM_ONE -> twoTuple.first()[0];
            case MatchPattern.HIDE_PARAM_ONE -> twoTuple.second()[0];
            default -> throw new RuntimeException();
        };
    }


    private static boolean match(Object ans, Object commit, Method method) {
        String methodName = method.getName();
        try {
            String ansStr = MAPPER.writeValueAsString(ans);
            String resultStr = MAPPER.writeValueAsString(commit);
            if (ansStr.equals(resultStr)) {
                System.out.println(methodName + " : Answer Accept");
                return true;
            } else {
                System.out.println("\n--------------------------------\n\t" +
                        "\n* " + methodName + " : Wrong Accept!" + " *\n\t" +
                        "\n--------------------------------\n\t");
                return false;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}

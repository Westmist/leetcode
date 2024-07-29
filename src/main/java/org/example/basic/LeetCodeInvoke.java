package org.example.basic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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
                    .filter(method -> !parentMethods.contains(method.getName()))
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
        // 参数类型列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        // 实参数据列表
        Param annotation = method.getAnnotation(Param.class);
        String[] valueStr = annotation.value();

        if (parameterTypes.length != valueStr.length) {
            System.out.println(new IllegalAccessException().getMessage());
        }

        Object[] parameObj = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            try {
                Object pObj = MAPPER.readValue(valueStr[i], parameterTypes[i]);
                parameObj[i] = pObj;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }


        // 待验证的提交
        Object commit;
        // 执行结果
        Object invokeR;
        try {
            invokeR = method.invoke(instance, parameObj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        Answer answerAno = method.getAnnotation(Answer.class);
        Class<?> ansType;

        switch (answerAno.matchPattern()) {
            case MatchPattern.RESULT:
                ansType = method.getReturnType();
                commit = invokeR;
                break;
            case MatchPattern.PARAM_ONE:
                ansType = parameterTypes[0];
                commit = parameObj[0];
                break;
            default:
                throw new RuntimeException();
        }

        // 预期的答案
        Object ans;
        try {
            ans = MAPPER.readValue(answerAno.value(), ansType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        match(ans, commit, method);
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

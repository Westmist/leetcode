package org.example.basic.convert;

import javassist.Modifier;
import org.example.basic.convert.inf.IConvert;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConvertFactory {

    public static Map<Class<? extends IConvert>, IConvert<?>> COVERTS = new HashMap<>();

    static {
        String packageToScan = "org.example.basic.convert.imp";
        Reflections reflections = new Reflections(packageToScan, Scanners.SubTypes);

        // 获取该包下所有类的子类或实现类（非接口和非抽象类）
        Set<Class<? extends IConvert>> allClasses = reflections.getSubTypesOf(IConvert.class);
        for (Class<? extends IConvert> clazz : allClasses) {
            // 排除接口和抽象类
            if (Modifier.isAbstract(clazz.getModifiers()) && !clazz.isInterface()) {
                continue;
            }
            try {
                // 实例化对象
                IConvert<?> instance = clazz.getDeclaredConstructor().newInstance();
                COVERTS.put(clazz, instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T convertToType(String str, Class<T> type) {
        if (type == Integer.class || type == int.class) {
            return (T) Integer.valueOf(str);
        } else if (type == Long.class || type == long.class) {
            return (T) Long.valueOf(str);
        } else if (type == Double.class || type == double.class) {
            return (T) Double.valueOf(str);
        } else if (type == Float.class || type == float.class) {
            return (T) Float.valueOf(str);
        } else if (type == Character.class || type == char.class) {
            return (T) Character.valueOf(str.charAt(0));
        } else if (type == String.class || type == char[].class) {
            return (T) str;
        } else if (type == Boolean.class || type == boolean.class) {
            return (T) Boolean.valueOf(str);
        }
        // 其他类型的处理
        throw new IllegalArgumentException("Unsupported type: " + type.getName());
    }

}



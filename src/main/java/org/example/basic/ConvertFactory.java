package org.example.basic;

import org.example.basic.convert.*;

import java.util.HashMap;
import java.util.Map;

public class ConvertFactory {


    public static Map<Class<? extends IConvert<?>>, IConvert<?>> COVERTS = new HashMap<>();

    static {
        COVERTS.put(DoubleCharArrayConvert.class, new DoubleCharArrayConvert());
        COVERTS.put(CharArrayConvert.class, new CharArrayConvert());
        COVERTS.put(StringArrayConvert.class, new StringArrayConvert());
        COVERTS.put(ListNodeConvert.class, new ListNodeConvert());
        COVERTS.put(ListNodeListConvert.class, new ListNodeListConvert());
    }

    @SuppressWarnings("unchecked")
    public static <T> T convertToType(String str, Class<T> type) {
        if (type == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (type == Long.class) {
            return (T) Long.valueOf(str);
        } else if (type == Double.class) {
            return (T) Double.valueOf(str);
        } else if (type == Float.class) {
            return (T) Float.valueOf(str);
        } else if (type == Character.class) {
            return (T) Character.valueOf(str.charAt(0));
        } else if (type == String.class) {
            return (T) str;
        }
        // 其他类型的处理
        throw new IllegalArgumentException("Unsupported type: " + type.getName());
    }

}



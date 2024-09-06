package org.example.basic.convert.imp;

import org.example.basic.convert.ConvertFactory;
import org.example.basic.convert.inf.IConvert;

import java.lang.reflect.Array;

public class ArrayConvert implements IConvert<Object> {
    @Override
    public Object convert(String src, Class<?> tClazz) {
        // 去掉字符串的方括号和单引号
        src = src.replace("[", "").replace("]", "").replace("'", "");

        // 拆分字符串为字符数组
        String[] elements = src.split(",");

        // 初始化数组
        Object array = Array.newInstance(tClazz, elements.length);

        // 将每个元素转换为字符并存入数组
        for (int i = 0; i < elements.length; i++) {
            Object v = ConvertFactory.convertToType(elements[i].trim(), tClazz);
            Array.set(array, i, v);
        }
        return array;
    }

}

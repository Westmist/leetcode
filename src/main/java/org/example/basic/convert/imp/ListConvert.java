package org.example.basic.convert.imp;


import org.example.basic.convert.ConvertFactory;
import org.example.basic.convert.inf.IConvert;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ListConvert implements IConvert<List<Object>> {
    @Override
    public List<Object> convert(String src, Class<?> tClazz) {
        // 去掉字符串的方括号和单引号
        src = src.replace("[", "").replace("]", "").replace("'", "");

        // 拆分字符串为字符数组
        String[] elements = src.split(",");

        List<Object> rt = new ArrayList<>(elements.length);
        for (String element : elements) {
            Object o = ConvertFactory.convertToType(element, tClazz);
            rt.add(o);
        }
        return rt;
    }
}

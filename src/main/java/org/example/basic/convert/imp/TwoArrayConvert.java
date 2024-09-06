package org.example.basic.convert.imp;

import org.example.basic.convert.ConvertFactory;
import org.example.basic.convert.inf.IConvert;

import java.lang.reflect.Array;

public class TwoArrayConvert implements IConvert<Object> {
    @Override
    public Object convert(String src, Class<?> tClazz) {
        // 去掉 JSON 字符串的方括号
        src = src.substring(1, src.length() - 1);

        // 将单引号替换为双引号
        src = src.replace("'", "\"");

        // 分割每一行
        String[] rows = src.split("],\\[");

        // 处理第一行和最后一行的方括号
        rows[0] = rows[0].replace("[", "");
        rows[rows.length - 1] = rows[rows.length - 1].replace("]", "");

        // 创建二维数组，使用反射动态创建
        Object matrix = Array.newInstance(tClazz, rows.length, rows[0].split(",").length);

        // 遍历每一行
        for (int i = 0; i < rows.length; i++) {
            // 分割当前行的元素
            String[] elements = rows[i].split(",");
            // 遍历每一列的元素并赋值
            for (int j = 0; j < elements.length; j++) {
                // 去掉元素中的引号
                String strV = elements[j].replace("\"", "").trim();

                // 将字符串转换为目标类型
                Object value = ConvertFactory.convertToType(strV, tClazz);

                // 使用反射为二维数组赋值
                Array.set(Array.get(matrix, i), j, value);
            }
        }

        return matrix;
    }


    // 使用反射为二维数组赋值
    private void assignValueWithReflection(Object array, int row, int col, Object value) {
        // 获取指定行
        Object rowArray = Array.get(array, row);
        // 为该行的指定列赋值
        Array.set(rowArray, col, value);
    }

}

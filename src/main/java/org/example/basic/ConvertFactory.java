package org.example.basic;

import java.util.HashMap;
import java.util.Map;

public class ConvertFactory {


    public static Map<Class<? extends IConvert<?>>, IConvert<?>> COVERTS = new HashMap<>();

    static {
        COVERTS.put(DoubleCharArrayConvert.class, new DoubleCharArrayConvert());
        COVERTS.put(CharArrayConvert.class, new CharArrayConvert());
        COVERTS.put(StringArrayConvert.class, new StringArrayConvert());
    }

    /**
     * 转二维 char 数组
     */
    public static class DoubleCharArrayConvert implements IConvert<char[][]> {
        @Override
        public char[][] convert(String src) {
            // 去掉 JSON 字符串的方括号
            src = src.substring(1, src.length() - 1);

            // 将单引号替换为双引号
            src = src.replace("'", "\"");

            // 分割每一行
            String[] rows = src.split("],\\[");

            // 处理第一行和最后一行的方括号
            rows[0] = rows[0].replace("[", "");
            rows[rows.length - 1] = rows[rows.length - 1].replace("]", "");

            char[][] charMatrix = new char[rows.length][];

            for (int i = 0; i < rows.length; i++) {
                // 分割每一行的元素
                String[] elements = rows[i].split("\",\"");
                charMatrix[i] = new char[elements.length];

                for (int j = 0; j < elements.length; j++) {
                    // 去掉引号并处理空字符串
                    String value = elements[j].replace("\"", "");
                    charMatrix[i][j] = value.isEmpty() ? ' ' : value.charAt(0);
                }
            }
            return charMatrix;
        }
    }

    /**
     * 转一维 char 数组
     */
    public static class CharArrayConvert implements IConvert<char[]> {
        @Override
        public char[] convert(String src) {
            // 去掉字符串的方括号和单引号
            src = src.replace("[", "").replace("]", "").replace("'", "");

            // 拆分字符串为字符数组
            String[] elements = src.split(",");

            // 初始化 char 数组
            char[] charArray = new char[elements.length];

            // 将每个元素转换为字符并存入 char 数组
            for (int i = 0; i < elements.length; i++) {
                charArray[i] = elements[i].trim().charAt(0);
            }

            return charArray;
        }
    }

    /**
     * 转一维 string 数组
     */
    public static class StringArrayConvert implements IConvert<String[]> {
        @Override
        public String[] convert(String src) {
            // 去掉方括号和单引号
            src = src.replace("[", "")
                    .replace("]", "")
                    .replace("'", "");
            // 根据逗号分割字符串
            return src.split(",");
        }
    }

}

interface IConvert<T> {
    T convert(String src);
}

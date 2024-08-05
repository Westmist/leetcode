package org.example.basic;

import org.example.comom.linkednode.ListNode;

import java.util.HashMap;
import java.util.List;
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
            src = src.replace("[", "").replace("]", "").replace("'", "");
            // 根据逗号分割字符串
            return src.split(",");
        }
    }


    /**
     * 转链表返回头节点
     */
    public static class ListNodeConvert implements IConvert<ListNode> {
        @Override
        public ListNode convert(String src) {
            return null;
        }

        @Override
        public ListNode convert(String src, Class<?> tClazz) {
            // 去掉字符串的方括号和单引号
            src = src.replace("[", "").replace("]", "").replace("'", "");

            // 拆分字符串为字符数组
            String[] elements = src.split(",");

            Object[] rt = new Object[elements.length];

            for (int i = 0; i < elements.length; i++) {
                rt[i] = convertToType(elements[i], tClazz);
            }
            return ListNode.build(rt);
        }
    }

    /**
     * 转链表 List，包含全部的节点
     */
    public static class ListNodeListConvert implements IConvert<List<ListNode>> {

        @Override
        public List<ListNode> convert(String src) {
            return null;
        }

        @Override
        public List<ListNode> convert(String src, Class<?> tClazz) {
            // 去掉字符串的方括号和单引号
            src = src.replace("[", "").replace("]", "").replace("'", "");

            // 拆分字符串为字符数组
            String[] elements = src.split(",");

            Object[] rt = new Object[elements.length];

            for (int i = 0; i < elements.length; i++) {
                rt[i] = convertToType(elements[i], tClazz);
            }
            return ListNode.buildList(rt);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T convertToType(String str, Class<T> type) {
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


interface IConvert<T> {
    T convert(String src);

    default T convert(String src, Class<?> tClazz) {
        return convert(src);
    }
}

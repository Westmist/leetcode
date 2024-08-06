package org.example.basic.convert;


/**
 * 转一维 char 数组
 */
public class CharArrayConvert implements IConvert<char[]> {
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

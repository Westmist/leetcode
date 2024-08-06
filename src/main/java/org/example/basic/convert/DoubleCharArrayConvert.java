package org.example.basic.convert;

public class DoubleCharArrayConvert implements IConvert<char[][]> {
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

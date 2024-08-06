package org.example.basic.convert;


/**
 * 转一维 string 数组
 */
public class StringArrayConvert implements IConvert<String[]> {
    @Override
    public String[] convert(String src) {
        // 去掉方括号和单引号
        src = src.replace("[", "").replace("]", "").replace("'", "");
        // 根据逗号分割字符串
        return src.split(",");
    }
}

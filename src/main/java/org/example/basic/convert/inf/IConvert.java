package org.example.basic.convert.inf;


public interface IConvert<T> {
    /**
     * @param src    参数字符串
     * @param tClazz 参数泛型
     * @return 转换结果
     */
    T convert(String src, Class<?> tClazz);

    default Class<?> defaultConvertGenClazz(Class<?> tarClazz) {
        return int.class;
    }

}
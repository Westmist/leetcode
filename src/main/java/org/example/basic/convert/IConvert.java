package org.example.basic.convert;


public interface IConvert<T> {
    T convert(String src);

    default T convert(String src, Class<?> tClazz) {
        return convert(src);
    }
}
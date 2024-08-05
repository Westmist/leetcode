package org.example.basic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Param {

    /**
     * 原字符串
     */
    String[] value() default {};

    /**
     * 转换类
     */
    Class[] convert() default {};


    /**
     * 参数容器的泛型类
     */
    Class[] genericType() default {};

    /**
     * 取转换后数组的第 n 个元素
     */
    int[] cvtIndex() default {};

}

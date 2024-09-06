package org.example.basic.convert.ano;

import org.example.basic.convert.imp.JsonConvert;
import org.example.basic.convert.inf.IConvert;

//@Retention(RetentionPolicy.RUNTIME)
//@Target({ElementType.METHOD})
public @interface Convert {

    /**
     * 原字符串
     */
    String value() default "";

    /**
     * 转换类
     */
    Class<? extends IConvert<?>> convert() default JsonConvert.class;

    /**
     * 转换类的泛型类
     */
    Class<?> type() default Void.class;

}

package org.example.basic.convert.ano;

import org.example.basic.convert.cons.MatchPattern;
import org.example.basic.convert.inf.IConvertSection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Answer {

    Convert c();

    /**
     * 转换切面处理
     */
    Class<? extends IConvertSection> section() default IConvertSection.class;

    MatchPattern pattern() default MatchPattern.RESULT;

}


package org.example.basic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Answer {

    String value();

    int matchPattern() default MatchPattern.RESULT;

    Class<?> convert() default Void.class;

}


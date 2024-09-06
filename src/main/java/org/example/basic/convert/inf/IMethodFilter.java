package org.example.basic.convert.inf;

import java.lang.reflect.Method;

@FunctionalInterface
public interface IMethodFilter {
    Method[] filterMethod(Method[] methods) ;
}

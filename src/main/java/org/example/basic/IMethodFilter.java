package org.example.basic;

import java.lang.reflect.Method;

@FunctionalInterface
public interface IMethodFilter {
    Method[] filterMethod(Method[] methods) ;
}

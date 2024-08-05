package org.example.basic;

public interface MatchPattern {

    /**
     * 未知
     */
    int ERROR = -1;

    /**
     * 匹配返回值
     */
    int RESULT = 1;

    /**
     * 匹配第一个参数
     */
    int PARAM_ONE = 2;


    /**
     * 隐藏的参数部分
     */
    int HIDE_PARAM_ONE = 3;


}

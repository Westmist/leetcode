package org.example;


import org.example.basic.LeetCodeInvoke;
import org.example.book.easy.ArrayChapter;
import org.example.book.easy.LinkedNodeChapter;
import org.example.book.easy.StringChapter;

public class Main {

    public static void main(String[] args) {
        LeetCodeInvoke.invoke(ArrayChapter.class);
        LeetCodeInvoke.invoke(StringChapter.class);
        LeetCodeInvoke.invoke(LinkedNodeChapter.class);
    }

}
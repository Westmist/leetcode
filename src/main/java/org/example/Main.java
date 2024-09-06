package org.example;


import lombok.extern.slf4j.Slf4j;
import org.example.basic.LeetCodeInvoke;
import org.example.book.easy.ArrayChapter;
import org.example.book.easy.LinkedNodeChapter;
import org.example.book.easy.StringChapter;

@Slf4j
public class Main {

    public static void main(String[] args) {
        LeetCodeInvoke.invoke(ArrayChapter.class);
        LeetCodeInvoke.invoke(StringChapter.class);
        LeetCodeInvoke.invoke(LinkedNodeChapter.class);
    }

}
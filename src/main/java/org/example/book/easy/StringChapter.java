package org.example.book.easy;

import org.example.basic.Answer;
import org.example.basic.ConvertFactory;
import org.example.basic.MatchPattern;
import org.example.basic.Param;

import java.util.HashMap;
import java.util.Map;

/**
 * 力扣书籍 - 初级算法
 * 字符串章节
 *
 * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/x2uudv/'>...</a>}
 */
public class StringChapter {

    /**
     * 反转字符串
     *
     * @param s 原数组
     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnhbqj/'>...</a>}
     * 方法：相向双指针
     * 时间复杂度：O(n)
     */
    @Param(value = "['h','e','l','l','o']",
            convert = ConvertFactory.CharArrayConvert.class)
    @Answer(value = "['o','l','l','e','h']",
            matchPattern = MatchPattern.PARAM_ONE,
            convert = ConvertFactory.CharArrayConvert.class)
    public void reverseString(char[] s) {
        int left = 0, right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }


    /**
     * 整数反转
     *
     * @param x 原值
     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnx13t/'>...</a>}
     * 方法：相向双指针
     * 时间复杂度：O(n)
     */
    @Param("-123")
    @Answer("-321")
    public int reverse(int x) {
        char[] src = String.valueOf(x).toCharArray();
        int left = 0, right = src.length - 1;
        while (left < right) {
            if (src[left] == '-') {
                left++;
                continue;
            }
            char temp = src[left];
            src[left] = src[right];
            src[right] = temp;
            left++;
            right--;
        }
        StringBuilder builder = new StringBuilder();
        for (char c : src) {
            builder.append(c);
        }
        long l = Long.parseLong(builder.toString());
        if (l > Integer.MAX_VALUE || l < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) l;
    }


    /**
     * 字符串中的第一个唯一字符
     *
     * @param s 原字符串
     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xn5z8r/'>...</a>}
     * 方法：自制哈希表
     * 时间复杂度：O(n)
     */
    @Param("loveleetcode")
    @Answer("2")
    public int firstUniqChar(String s) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int key = c - 'a';
            map.compute(key, (k, ov) -> {
                if (ov == null) {
                    return 1;
                }
                return ov + 1;
            });
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int key = c - 'a';
            if (map.get(key) == 1) {
                return i;
            }
        }
        return -1;
    }

}

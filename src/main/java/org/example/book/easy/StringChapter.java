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

    /**
     * 有效的字母异位词
     *
     * @param s 字符串1
     * @param t 字符串2
     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xn96us/'>...</a>}
     * 方法：哈希表差值
     * 时间复杂度：O(n)
     */
    @Param({"anagram", "nagaram"})
    @Answer("true")
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.compute(s.charAt(i), (k, ov) -> {
                if (ov == null) {
                    return 1;
                }
                return ov + 1;
            });
        }
        for (int i = 0; i < t.length(); i++) {
            int count = map.getOrDefault(t.charAt(i), -1);
            if (count <= 0) {
                return false;
            }
            map.put(t.charAt(i), count - 1);
        }
        return true;
    }


    /**
     * 验证回文串
     *
     * @param s 字符串
     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xne8id/'>...</a>}
     * 方法：相向双指针
     * 时间复杂度：O(n)
     */
    @Param({"A man, a plan, a canal: Panama"})
    @Answer("true")
    public boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            char lc = Character.toLowerCase(s.charAt(left));
            if ((lc < 'a' || lc > 'z') && (lc < '0' || lc > '9')) {
                left++;
                continue;
            }

            char rc = Character.toLowerCase(s.charAt(right));
            if ((rc < 'a' || rc > 'z') && (rc < '0' || rc > '9')) {
                right--;
                continue;
            }

            if (lc != rc) {
                return false;
            }

            left++;
            right--;
        }
        return true;
    }

    /**
     * 字符串转换整数 (atoi)
     *
     * @param s 字符串
     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnoilh/'>...</a>}
     * 方法：流程逻辑
     * 时间复杂度：O(n)
     */
    @Param({" -042"})
    @Answer("-42")
    public int myAtoi(String s) {
        // TODO 考虑状态机
        StringBuilder builder = new StringBuilder();
        // 0: 初始阶段，1：已除去空格， 2: 已加完符号， 3：已处理完前置0
        int state = 0;
        boolean minus = false;
        for (int i = 0; i < s.length(); i++) {
            // 长度超出
            if (builder.length() > 11) {
                return minus ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            char c = s.charAt(i);
            // 1、去除空格
            if (state == 0 && c == ' ') {
                continue;
            }
            if (state == 0) {
                state = 1;
            }
            // 2、加符号
            if (state == 1) {
                if (c == '-' || c == '+' || Character.isDigit(c)) {
                    state = 2;
                    if (c == '-') {
                        minus = true;
                    }
                    if (c != '+') {
                        builder.append(c);
                    }
                    if (c > '0' && c <= '9') {
                        state = 3;
                    }
                    continue;
                }
            }

            // 3、去 0
            if (state == 2) {
                if (c == '0') {
                    continue;
                } else {
                    state = 3;
                }
            }

            // 4、加数字
            if (state == 3 && Character.isDigit(c)) {
                builder.append(c);
                continue;
            }
            break;
        }
        if (builder.isEmpty() || builder.toString().equals("-")) {
            builder.append(0);
        }
        long l = Long.parseLong(builder.toString());
        return l > Integer.MAX_VALUE ? Integer.MAX_VALUE : l < Integer.MIN_VALUE ? Integer.MIN_VALUE : (int) l;
    }

}

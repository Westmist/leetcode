package org.example.book.easy;

import org.example.basic.*;

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
     * @param s 原数组
     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnhbqj/'>...</a>}
     * 方法：相向双指针
     * 时间复杂度：O(n)
     */
    @Title("反转字符串")
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
     * @param x 原值
     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnx13t/'>...</a>}
     * 方法：相向双指针
     * 时间复杂度：O(n)
     */
    @Title("整数反转")
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
     * @param s 原字符串
     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xn5z8r/'>...</a>}
     * 方法：自制哈希表
     * 时间复杂度：O(n)
     */
    @Title("字符串中的第一个唯一字符")
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
    @Title("有效的字母异位词")
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
    @Title("验证回文串")
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
     * @param s 字符串
     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnoilh/'>...</a>}
     * 方法：流程逻辑
     * 时间复杂度：O(n)
     */
    @Title("字符串转换整数 (atoi)")
    @Param({" -042"})
    @Answer("-42")
    public int myAtoi(String s) {
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


    /**
     * 实现 strStr()
     *
     * @param haystack 字符串
     * @param needle   子串
     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnr003/'>...</a>}
     * 方法：字串对比
     * 时间复杂度：O(n^2)
     */
    @Title("实现 strStr()")
    @Param({"leetcode", "leet"})
    @Answer("0")
    public int strStr(String haystack, String needle) {
        for (int i = 0; i < haystack.length(); i++) {
            char tar = haystack.charAt(i);
            if (tar == needle.charAt(0)) {
                boolean match = true;
                for (int j = 1; j < needle.length(); j++) {
                    if (i + j >= haystack.length() || needle.charAt(j) != haystack.charAt(i + j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return i;
                }
            }
        }
        return -1;
    }


    /**
     * @param n 长度
     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnpvdm/'>...</a>}
     * 方法：双指针
     * 时间复杂度：O(n^2)
     */
    @Title("外观数列")
    @Param({"4"})
    @Answer("1211")
    public String countAndSay(int n) {
        String ans = "1";
        for (int i = 1; i < n; i++) {
            ans = doCountAndSay(ans);
        }
        return ans;
    }

    private String doCountAndSay(String str) {
        if (str.length() == 1) {
            return "1" + str.charAt(0);
        }
        StringBuilder builder = new StringBuilder();
        int count = 1;
        for (int left = 0, right = 1; right <= str.length(); ) {
            if (right >= str.length() || str.charAt(left) != str.charAt(right)) {
                builder.append(count);
                builder.append(str.charAt(left));
                count = 1;
                left = right;
                right += 1;
            } else {
                right++;
                count++;
            }
        }
        return builder.toString();
    }


    /**
     * @param strs 字符串数组
     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnmav1/'>...</a>}
     * 方法：多指针同步遍历
     * 时间复杂度：O(n)
     */
    @Title("最长公共前缀")
    @Param(value = {"['flower','flow','flight']"}, convert = ConvertFactory.StringArrayConvert.class)
    @Answer("fl")
    public String longestCommonPrefix(String[] strs) {
        String f = strs[0];
        int minLength = f.length();
        for (String str : strs) {
            minLength = Math.min(minLength, str.length());
        }
        if (minLength == 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < minLength; i++) {
            if (i >= f.length()) {
                break;
            }
            char c = f.charAt(i);
            boolean match = true;
            for (String str : strs) {
                if (i >= str.length() || str.charAt(i) != c) {
                    match = false;
                    break;
                }
            }
            if (!match) {
                break;
            }
            builder.append(c);
        }

        return builder.toString();
    }


}

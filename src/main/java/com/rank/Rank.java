package com.rank;

import lombok.Data;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Data
public class Rank<M extends IMember> {

    /**
     * 分数成员
     */
    private ConcurrentSkipListMap<Double, List<M>> rank = new ConcurrentSkipListMap<>(Comparator.naturalOrder());

    private ConcurrentHashMap<Long, M> members = new ConcurrentHashMap<>();

    public int findRank(Long id) {
        M member = members.get(id);
        if (member == null) {
            return -1;
        }
        return 8;
    }


    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        // 逆向双指针
        int index = m + n - 1;
        for (int i = m - 1, j = n - 1; index >= 0; ) {
            int n1 = i < 0 ? Integer.MIN_VALUE : nums1[i];
            int n2 = j < 0 ? Integer.MIN_VALUE : nums2[j];
            if (n1 >= n2) {
                nums1[index--] = n1;
                i--;
            } else {
                nums1[index--] = n2;
                j--;
            }
        }
    }

    /**
     * 贪心
     */
    public static int jump(int[] nums) {
        int ans = 0;
        for (int i = 0; ; ) {
            int cur = nums[i];
            if (i + cur >= nums.length - 1) {
                if (nums.length != 1) {
                    ans++;
                }
                break;
            }
            int maxIndex = i;
            // 找出最大的跳跃 index + value
            for (int j = 0; j <= cur; j++) {
                if (nums[i + j] + i + j > nums[maxIndex] + maxIndex) {
                    maxIndex = i + j;
                }
            }
            ans++;
            i = maxIndex;
        }
        return ans;
    }


    public static int minChanges(int n, int k) {
        String bn = Integer.toBinaryString(n);
        String bk = Integer.toBinaryString(k);
        if (bk.length() > bn.length()) {
            return -1;
        }
        // 对齐
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bn.length() - bk.length(); i++) {
            sb.append('0');
        }
        bk = sb.append(bk).toString();
        // bk 位为 1    bn 位为 0   不满足
        // bn 位为 1    bk 位为 0   计数
        int count = 0;
        for (int i = 0; i < bn.length(); i++) {
            char kc = i >= bk.length() ? '0' : bk.charAt(i);
            char nc = bn.charAt(i);
            if (kc == '1' && nc == '0') {
                return -1;
            }
            if (kc == '0' && nc == '1') {
                count++;
            }
        }
        return count;
    }

    public static int[] sortByBits(int[] arr) {
        Integer[] array = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        Arrays.sort(array, (o1, o2) -> {
            if (count(o1) == count(o2)) {
                return o1 - o2;
            }
            return count(o1) - count(o2);
        });
        return Arrays.stream(array).mapToInt(Integer::intValue).toArray();
    }

    public static int count(Integer o1) {
        int count = 0;
        String binaryString = Integer.toBinaryString(o1);
        for (int i = 0; i < binaryString.length(); i++) {
            char c = binaryString.charAt(i);
            if (c == '1') {
                count++;
            }
        }
        return count;
    }

    public static int hammingDistance(int x, int y) {
        int n = x ^ y;
        return count(n);
    }

    /**
     * @param citations
     * @return
     */
    public static int hIndex(int[] citations) {
        // 数组排序
        Arrays.sort(citations);
        int h = 0, i = citations.length - 1;
        while (i >= 0 && citations[i] > h) {
            h++;
            i--;
        }
        return h;
    }


    public static void main(String[] args) {
        int[] arr = {0};
        int i = hIndex(arr);
        System.out.println(i);
    }




}

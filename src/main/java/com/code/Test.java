package com.code;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;

public class Test {

    private final static Map<Integer, Integer> money = new HashMap<>();

    /**
     * 初始金额
     */
    private static final int initMoney = 100;

    /**
     * 人数
     */
    private static final int peopleCount = 100;

    /**
     * 交易轮次
     */
    private static final int cycle = 1000;


    public static void main(String[] args) {
        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();
        map.put(1, "1");
        map.put(3, "3");
        map.put(2, "2");
        map.put(4, "4");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    /**
     * 二分搜索
     */
    public static boolean bineryQuery(int[] nums, int x) {
        int left = 0, right = nums.length - 1;
        if (left == x || right == x) {
            return true;
        }
        int mid = (left + right) / 2;
        while (mid > left) {
            if (mid == x) {
                return true;
            }
            if (x < mid) {
                right = mid;
            }
            if (x > mid) {
                left = mid;
            }
            mid = (left + right) / 2;
        }
        return false;
    }


    /**
     * 选择排序
     */
    public static void selectOrder(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            int md = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[md]) {
                    md = j;
                }
            }
            swap(nums, i, md);
        }
    }

    /**
     * 插入排序
     */
    public static void insertOrder(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int c = nums[i];
            for (int j = i - 1; j >= 0; j--) {
                if (nums[j] > c) {
                    // 大的往后挪
                    nums[j + 1] = nums[j];
                    // 要插入最前面的话移完后要修改首个元素
                    if (j == 0) {
                        nums[j] = c;
                    }
                } else {
                    // 遇见小的
                    nums[j + 1] = c;
                    break;
                }
            }
        }
    }

    /**
     * 冒泡排序
     */
    public static void bubblingOrder(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    swap(nums, i, j);
                }
            }
        }
    }


    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    /**
     * 将 byte 转换为二进制字符串
     *
     * @param b 要转换的 byte 值
     * @return 二进制表示的字符串，长度为 8 位
     */
    public static String byteToBinaryString(byte b) {
        // 使用 Integer.toBinaryString，将 byte 转换为 int 后保留二进制位
        String binaryString = Integer.toBinaryString(b & 0xFF); // 与 0xFF  按位与，确保转为无符号值
        // 如果不足 8 位，在前面补 0
        return String.format("%8s", binaryString).replace(' ', '0');
    }


    private static void trade() {
        for (int i = 1; i <= peopleCount; i++) {
            money.put(i, initMoney);
        }
        Random random = new Random();
        for (int i = 1; i <= cycle; i++) {
            for (Map.Entry<Integer, Integer> entry : money.entrySet()) {
                if (entry.getValue() <= 0) {
                    continue;
                }
                int s;
                do {
                    s = random.nextInt(1, peopleCount + 1);
                } while (s == entry.getKey());
                entry.setValue(entry.getValue() - 1);
                money.compute(s, (k, v) -> v + 1);
            }
        }

        double gini = calculateGiniIndex();
        System.out.println(gini);
    }

    /**
     * 计算基尼系数
     *
     * @return
     */
    private static double calculateGiniIndex() {
        double total = 0;
        for (Map.Entry<Integer, Integer> entry : money.entrySet()) {
            Integer v = entry.getValue();
            for (Map.Entry<Integer, Integer> e : money.entrySet()) {
                total += Math.abs(v - e.getValue());
            }
        }
        return total / (2d * peopleCount * peopleCount * initMoney);
    }


}
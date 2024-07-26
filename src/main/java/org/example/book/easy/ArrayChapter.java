package org.example.book.easy;

import org.example.basic.MatchPattern;
import org.example.basic.Param;
import org.example.basic.Result;

public class ArrayChapter {

    /**
     * 原地删除数组
     *
     * @param nums 原数组 (递增)
     * @return 新数组长度
     * @link {<a href="https://leetcode.cn/leetbook/read/top-interview-questions-easy/x2gy9m/">...</a>}
     */
    @Param("[0,0,1,1,1,2,2,3,3,4]")
    @Result("5")
    public int removeDuplicates(int[] nums) {
        // 双指针，移位交换
        int count = 0;
        for (int right = 1, left = 0; right < nums.length; ) {
            if (nums[left] >= nums[right]) {
                right++;
            } else {
                nums[left + 1] = nums[right];
                left++;
                count++;
            }
        }
        return count + 1;
    }

    /**
     * 买股票的最佳时机
     *
     * @param prices 股票价格
     * @return 最大利润
     * @link {<a href="https://leetcode.cn/leetbook/read/top-interview-questions-easy/x2zsx1/">...</a>}
     */
    @Param("[7,1,5,3,6,4]")
    @Result("7")
    public int maxProfit(int[] prices) {
        int ans = 0;
        int f = -1;
        // 贪心策略
        for (int today = 0; today < prices.length; today++) {
            int tomorrowPrices = today + 1 < prices.length ? prices[today + 1] : -1;
            // 无持仓 且 明天会涨，保证持有
            if (f <= 0 || tomorrowPrices > prices[today]) {
                f = prices[today];
            }
            // 有持仓 且 明天会跌或已是最后一天，抛售
            if (f > 0 && (tomorrowPrices < prices[today] || tomorrowPrices <= 0)) {
                ans += prices[today] - f;
                f = -1;
            }
        }
        return ans;
    }


    /**
     * 旋转数组
     *
     * @param nums 原数组
     * @link {<a href="https://leetcode.cn/leetbook/read/top-interview-questions-easy/x2skh7/">...</a>}
     */
    @Param({"[1,2,3,4,5,6,7]", "3"})
    @Result(value = "[5,6,7,1,2,3,4]", matchPattern = MatchPattern.PARAM_ONE)
    public void rotate(int[] nums, int k) {
        // 翻转次数是数组长度的整数倍时，数组保持不变
        k = k % nums.length;
        if (k <= 0) {
            return;
        }
        // 多次翻转
        // 1、翻转全部
        dorotate(nums, 0, nums.length - 1);

        // 2、翻转前k部分
        dorotate(nums, 0, k - 1);

        // 3、翻转后k部分
        dorotate(nums, k, nums.length - 1);
    }

    private void dorotate(int[] nums, int h, int t) {
        while (h < t) {
            int tmp = nums[h];
            nums[h] = nums[t];
            nums[t] = tmp;
            h++;
            t--;
        }
    }


}

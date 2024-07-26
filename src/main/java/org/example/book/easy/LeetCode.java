package org.example.book.easy;

import org.example.basic.Param;
import org.example.basic.Result;

public class LeetCode {

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

}

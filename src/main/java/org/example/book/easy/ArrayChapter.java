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
     * 方法：双指针，移位交换
     * 时间复杂度：O(n)
     */
    @Param("[0,0,1,1,1,2,2,3,3,4]")
    @Result("5")
    public int removeDuplicates(int[] nums) {
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
     * 方法：贪心策略
     * 时间复杂度：O(n)
     */
    @Param("[7,1,5,3,6,4]")
    @Result("7")
    public int maxProfit(int[] prices) {
        int ans = 0;
        int f = -1;
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
     * 方法：规律技巧，多次翻转
     * 时间复杂度：O(n)
     */
    @Param({"[1,2,3,4,5,6,7]", "3"})
    @Result(value = "[5,6,7,1,2,3,4]", matchPattern = MatchPattern.PARAM_ONE)
    public void rotate(int[] nums, int k) {
        // 翻转次数是数组长度的整数倍时，数组保持不变
        k = k % nums.length;
        if (k <= 0) {
            return;
        }
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

    /**
     * 存在重复元素
     *
     * @param nums 原数组
     * @link {<a href="https://leetcode.cn/leetbook/read/top-interview-questions-easy/x248f5/">...</a>}
     * 方法：位运算
     * 时间复杂度：O(n)
     */
    @Deprecated  // 超出内存限制
    @Param({"[1,2,3,1]"})
    @Result(value = "true")
    public boolean containsDuplicate(int[] nums) {
        if (nums.length == 0) {
            return false;
        }
        // 1、查找最大值和最小值用于确定位图大小
        int min = nums[0];
        int max = nums[0];
        for (int num : nums) {
            min = Math.min(num, min);
            max = Math.max(num, max);
        }
        // 2、构建位图数组
        int dis = max - min + 1;
        int length = (int) Math.ceil(dis / 64.0);
        long[] bitMapArr = new long[length];

        // 3、维护位图逻辑
        for (int num : nums) {
            // 第cDis个，从1开始
            int cDis = num - min;
            int index = cDis / 64;
            // 查找所在区域
            long bitMap = bitMapArr[index];
            int bitIndex = cDis % 64;
            // 判断该位是已存在
            if ((bitMap & (1L << bitIndex)) != 0) {
                return true;
            }
            bitMap |= (1L << bitIndex);
            bitMapArr[index] = bitMap;
        }

        return false;
    }


}

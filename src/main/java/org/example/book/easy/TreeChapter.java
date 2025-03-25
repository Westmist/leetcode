package org.example.book.easy;


import org.example.basic.convert.ano.Title;
import org.example.comom.stru.TwoTreeNode;

import java.util.*;
import java.util.Map.Entry;

/**
 * 力扣书籍 - 中级算法
 * 树章节
 **/
@Title("中级算法:树")
public class TreeChapter {

    public static int[][] merge(int[][] intervals) {
        // 预排序
        for (int i = 0; i < intervals.length; i++) {
            for (int j = i + 1; j < intervals.length; j++) {
                if (intervals[i][0] > intervals[j][0]) {
                    int[] temp = intervals[i];
                    intervals[i] = intervals[j];
                    intervals[j] = temp;
                }
            }
        }

        Deque<int[]> deque = new ArrayDeque<>();
        for (int[] next : intervals) {
            int[] cur = deque.pollLast();
            if (cur == null) {
                deque.addLast(next);
                continue;
            }
            // 1、无需合并
            if (cur[1] < next[0]) {
                deque.addLast(cur);
                deque.addLast(next);
                continue;
            }
            int right = Math.max(cur[1], next[1]);
            deque.addLast(new int[]{cur[0], right});
        }

        int size = deque.size();
        int[][] ans = new int[size][];
        for (int i = 0; i < size; i++) {
            ans[i] = deque.pollFirst();
        }
        return ans;
    }


    public static int[] searchRange(int[] nums, int target) {
        int[] ans = {-1, -1};
        // 相向双指针
        int left = 0, right = nums.length - 1;
        while (left < nums.length) {
            if (ans[0] == -1 && nums[left] == target) {
                ans[0] = left;
            }
            if (ans[1] == -1 && nums[right] == target) {
                ans[1] = right;
            }
            // 左右都找到就不用继续了
            if (ans[0] != -1 && ans[1] != -1) {
                return ans;
            }
            left++;
            right--;
        }
        return ans;
    }

    public int findPeakElement(int[] nums) {
        // 寻找峰值
        for (int i = 0; i < nums.length; i++) {
            long left = i - 1 < 0 ? Long.MIN_VALUE : nums[i - 1];
            long right = i + 1 >= nums.length ? Long.MIN_VALUE : nums[i + 1];
            if (left < nums[i] && right < nums[i]) {
                return i;
            }
        }
        return -1;
    }

    public int findKthLargest(int[] nums, int k) {
        // 最大堆 O(nlog n)
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int num : nums) {
            pq.add(num);
        }
        for (int i = 0; i < k - 1; i++) {
            pq.poll();
        }
        return Objects.requireNonNull(pq.peek());
    }

    public int[] topKFrequent(int[] nums, int k) {
        // 统计个数   O(n)
        Map<Integer, Integer> counter = new HashMap<>();
        for (int num : nums) {
            counter.merge(num, 1, (ov, v) -> ov + 1);
        }

        // 快速、归并、堆排序
        int[] ans = new int[k];

        // 最大堆  O(nlog n)
        PriorityQueue<Entry<Integer, Integer>> pq =
                new PriorityQueue<>((entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue()));
        for (Entry<Integer, Integer> entry : counter.entrySet()) {
            pq.add(entry);
        }

        for (int i = 0; i < ans.length; i++) {
            ans[i] = Objects.requireNonNull(pq.poll()).getKey();
        }

        return ans;
    }


    public void sortColors(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
    }

    public void quickSort(int[] nums, int left, int right) {
        if (left >= right) return;
        int i = partition(nums, left, right);
        quickSort(nums, left, i - 1);
        quickSort(nums, i + 1, right);
    }

    public int partition(int[] nums, int left, int right) {
        int i = left, j = right;
        while (i < j) {
            while (i < j && nums[j] >= nums[left]) j--;
            while (i < j && nums[i] <= nums[left]) i++;
            swap(nums, i, j);
        }
        swap(nums, i, left);
        return i;
    }

    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }


    public boolean exist(char[][] board, String word) {
        // 硬编码鬼才，面向结果编程
        // 数组中不存在字符串中的字符时直接返回false
        Set<Character> charSet = new HashSet<>();
        for (char[] chars : board) {
            for (char aChar : chars) {
                charSet.add(aChar);
            }
        }
        for (int i = 0; i < word.length(); i++) {
            if (!charSet.contains(word.charAt(i))) {
                return false;
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // 找到可能的头
                if (board[i][j] == word.charAt(0)) {
                    // 关闭集合
                    Set<Long> closeSet = new HashSet<>();
                    long index = (long) i << 32 | j;
                    closeSet.add(index);
                    System.out.println("[" + i + "," + j + "]: " + board[i][j]);
                    if (helper(board, closeSet, i, j, word, 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 递归-回溯
     */
    private boolean helper(char[][] board, Set<Long> closeSet, int x, int y, String word, int np) {
        // 找到了
        if (np >= word.length()) {
            return true;
        }
        for (Long index : around(board, closeSet, x, y)) {
            Set<Long> cs = new HashSet<>(closeSet);
            int cx = (int) (index >> 32);
            int cy = (int) (index & 0xFFFFFFFFL);
            if (board[cx][cy] == word.charAt(np)) {
                System.out.println("[" + cx + "," + cy + "]: " + board[cx][cy]);
                cs.add(index);
                if (helper(board, cs, cx, cy, word, np + 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 查找周围的点
     */
    private List<Long> around(char[][] board, Set<Long> closeSet, int x, int y) {
        List<Long> aroundList = new ArrayList<>();
        // 上、下、左、右
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < dx.length; i++) {
            int ax = x + dx[i];
            int ay = y + dy[i];
            // 越界
            if (ax < 0 || ax >= board.length || ay < 0 || ay >= board[0].length) {
                continue;
            }
            long index = (long) ax << 32 | ay;
            // 重复
            if (closeSet.contains(index)) {
                continue;
            }
            aroundList.add(index);
        }
        return aroundList;
    }


    /**
     * 幂集
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<>());
        for (int num : nums) {
            int curSize = res.size();
            for (int j = 0; j < curSize; j++) {
                List<Integer> list = res.get(j);
                List<Integer> sub = new ArrayList<>(list);
                sub.add(num);
                res.add(sub);
            }
        }
        return res;
    }


    private static int index = 0;

    public static TwoTreeNode buildTree(int[] preorder, int[] inorder) {
        TwoTreeNode<Integer> root = new TwoTreeNode<>(preorder[0]);
        helper(inorder, preorder, 0, root);
        return root;
    }


    public static void helper(int[] array, int[] preorder, int type, TwoTreeNode root) {
        if (array.length == 0) {
            return;
        }
        int rootValue = preorder[index];
        TwoTreeNode cur = null;
        switch (type) {
            case 1:
                cur = new TwoTreeNode<>(rootValue);
                root.setLeft(cur);
                break;
            case 2:
                cur = new TwoTreeNode<>(rootValue);
                root.setRight(cur);
                break;
            case 0:
                cur = root;
                break;
        }
        index++;

        for (int i = 0; i < array.length; i++) {
            if (array[i] != rootValue) {
                continue;
            }

            int[] left = new int[i];
            System.arraycopy(array, 0, left, 0, left.length);
            helper(left, preorder, 1, cur);

            int[] right = new int[array.length - i - 1];
            System.arraycopy(array, i + 1, right, 0, right.length);
            helper(right, preorder, 2, cur);
        }

    }

    /**
     * 快速幂 O(log ∣n∣)
     */
    public double myPow(double x, int n) {
        // 1、将 n 转换成 2 进制字符串
        String binaryString = Integer.toBinaryString(Math.abs(n));
        // 2、翻转字符串从低位开始遍历
        String reverse = new StringBuilder(binaryString).reverse().toString();
        double cur = x;
        double ans = 1;
        for (int i = 0; i < reverse.length(); i++) {
            if (i > 0) {
                // 3、平方一下低位即为相邻的高位代表的幂结果
                cur *= cur;
            }
            if (reverse.charAt(i) == '0') {
                continue;
            }
            // 4、位为 1 则需要相乘到结果中
            ans *= cur;
        }
        // 如：n == 13
        // 13 的二进制为 1101  ==> cur依次为： x^8   x^4   x^2   x^1
        //      遇到 0 跳过                    1     1     0     1
        //    所以幂结果为                     x^8 * x^4       * x^1
        // 5、负数时为其倒数，否则直接为答案
        return n < 0 ? 1 / ans : ans;
    }


    public static int removeElement(int[] nums, int val) {
        // 1、原地删除数组元素
        int ans = 0;
        int left = 0;
        for (int right = 0; right < nums.length; ) {
            if (nums[right] == val) {
                ans++;
            } else if (nums[left] == val) {
                // swap
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
            } else {
                left++;
            }
            right++;
        }
        return nums.length - ans;
    }


//    public int removeDuplicates(int[] nums) {
//        int size = 2;
//        for (int i = 2; i < nums.length; i++) {
//            if (nums[i] != nums[size - 2]) {
//                nums[size++] = nums[i];
//            }
//        }
//        return Math.min(size, nums.length);
//    }


    /**
     * 跳跃游戏
     *
     * @param nums
     * @return
     */
    public static boolean canJump(int[] nums) {
        // nums = [3,2,1,0,4]
        int step = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > step) {
                return false;
            }
            step = Math.max(step, i + nums[i]);
        }
        return true;
    }


    /**
     * 除以自身元素外的数组之和
     */
    public static int[] productExceptSelf(int[] nums) {
        if (nums.length <= 1) {
            return nums;
        }
        int[] pre = new int[nums.length];
        pre[0] = 1;
        // 前缀积
        for (int i = 1; i < nums.length; i++) {
            pre[i] = pre[i - 1] * nums[i - 1];
        }

        int[] tail = new int[nums.length];
        tail[nums.length - 1] = 1;
        // 后缀积
        for (int i = nums.length - 2; i >= 0; i--) {
            tail[i] = tail[i + 1] * nums[i + 1];
        }

        // 前缀和 X 后缀和
        int[] ans = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            ans[i] = pre[i] * tail[i];
        }

        return ans;
    }


    /**
     * 分糖果
     *
     * @param ratings
     * @return
     */
    public static int candy(int[] ratings) {
        int[] left = new int[ratings.length];
        for (int i = 0; i < ratings.length; i++) {
            if (i > 0 && ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }

        int ans = 0;
        int[] right = new int[ratings.length];
        for (int i = ratings.length - 1; i >= 0; i--) {
            if (i < ratings.length - 1 && ratings[i] > ratings[i + 1]) {
                right[i] = right[i + 1] + 1;
            } else {
                right[i] = 1;
            }
            ans += Math.max(left[i], right[i]);
        }

        return ans;
    }


    /**
     * 写给黄淮
     */
    public static int removeDuplicates(int[] nums) {
        int count = 1;
        for (int slow = 0, fast = 1; fast < nums.length; ) {
            if (nums[fast] != nums[slow]) {
                nums[slow + 1] = nums[fast];
                slow++;
                count++;
            }
            fast++;
        }
        return count;
    }

    public static int majorityElement(int[] nums) {
        /**
         * 方法一、哈希表统计次数 O(n)   O(n)
         * 略
         */

        /**
         * 方法二、排序后取众数（n/2 索引处必定为众数）O(log n)  O(1)
         */
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }


    /**
     * O(1) 额外空间复杂度下翻转数组
     *
     * @param nums
     * @param k
     */
    public static void rotate(int[] nums, int k) {
        // 1,2,3,4,5,6,7     3
        //
        //  5,6,7   1,2,3,4

        // 三次翻转

        doHelper(nums, 0, nums.length - 1);
        doHelper(nums, 0, k - 1);
        doHelper(nums, k, nums.length - 1);


    }

    public static void doHelper(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }


    public static void main(String[] args) {
        int[] rotate = {1, 2, 3, 4, 5, 6, 7};
        rotate(rotate, 3);
//        System.out.println(ints);

        Map<Integer, Integer> map = new HashMap<>() {{
            put(1,3);
            put(2,4);
        }};




    }


}

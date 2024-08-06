package org.example.book.easy;

import org.example.basic.*;
import org.example.comom.linkednode.ListNode;

/**
 * 力扣书籍 - 初级算法
 * 链表章节
 *
 * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/x2t7vj/'>...</a>}
 */
public class LinkedNodeChapter {

    /**
     * @param node 链表
     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnarn7/'>...</a>}
     * 方法：链表节点挪动
     * 时间复杂度：O(n)
     */
    @Title("删除链表中的节点")
    @Param(value = "[4,5,1,9]", convert = ConvertFactory.ListNodeListConvert.class, genericType = {Integer.class}, cvtIndex = {1})
    @Answer(value = "[4,1,9]", convert = ConvertFactory.ListNodeConvert.class, genericType = Integer.class, matchPattern = MatchPattern.HIDE_PARAM_ONE)
    public void deleteNode(ListNode node) {
        ListNode cur = node;
        while (cur.next != null) {
            cur.val = cur.next.val;
            if (cur.next.next == null) {
                cur.next = null;
                break;
            }
            cur = cur.next;
        }
    }


    /**
     * @param head 链表
     * @param n    删除倒数第 n 个
     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xn2925/'>...</a>}
     * 方法：双指针遍历删除链表指定索引的节点
     * 时间复杂度：O(n)
     */
    @Title("删除链表的倒数第N个节点")
    @Param(value = {"[1,2,3,4,5]", "2"}, convert = ConvertFactory.ListNodeConvert.class, genericType = {Integer.class})
    @Answer(value = "[1,2,3,5]", convert = ConvertFactory.ListNodeConvert.class, genericType = Integer.class)
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 两个指针间距为 n, back 节点即为待删除节点的上一个节点
        // 1、初始化双指针
        ListNode ans = head;
        ListNode pre = head;
        for (int i = 0; i < n; i++) {
            if (pre == null) {
                throw new IllegalArgumentException("no nth node");
            }
            pre = pre.next;
        }
        ListNode back = head;
        // 2、边界判断，pre == null 即被删除的为头节点
        if (pre == null) {
            return ans.next;
        }
        // 3、双指针同时前进，直至前指针到达最后一个元素
        while (pre.next != null) {
            pre = pre.next;
            back = back.next;
        }
        back.next = back.next.next;
        return ans;
    }


    /**
     * @param head 链表
     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnnhm6/'>...</a>}
     * 方法：翻转链表节点指向
     * 时间复杂度：O(n)
     */
    @Title("反转链表")
    @Param(value = "[1,2]", convert = ConvertFactory.ListNodeConvert.class, genericType = {Integer.class})
    @Answer(value = "[2,1]", convert = ConvertFactory.ListNodeConvert.class, genericType = Integer.class)
    public ListNode reverseList(ListNode head) {
        // 1、边界判断，只有一个节点时直接返回根节点
        if (head == null || head.next == null) {
            return head;
        }
        // 2、双指针遍历交换指向
        ListNode pre = head.next;
        ListNode last = head;
        while (pre.next != null) {
            // 3、注意需要新建两个指针，避免干扰原指针的遍历
            ListNode r = pre;
            ListNode l = last;

            // last的指向已经翻转了，故此处需要指向pre,而非next
            last = pre;
            pre = pre.next;

            // 4、引用指向翻转,
            r.next = l;

            // 头变尾 (防止成环)
            if (head == l) {
                l.next = null;
            }
        }
        // 5、翻转最后一段
        pre.next = last;
        // 只有两个节点时还是可能成环
        if (last.next == pre) {
            last.next = null;
        }
        return pre;
    }


}

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


}

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


}

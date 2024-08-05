package org.example.comom.linkednode;

import java.util.ArrayList;
import java.util.List;

public class ListNode<T> {

    public T val;

    public ListNode<T> next;

    public ListNode(T val) {
        this.val = val;
    }

    public ListNode(T val, ListNode<T> next) {
        this.val = val;
    }

    public void addNext(ListNode<T> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        // TODO 处理环形链表的情况
        List<T> list = new ArrayList<>();
        ListNode<T> p = this;
        while (p != null) {
            list.add(p.val);
            p = p.next;
        }
        return list.toString();
    }

    public static <T> ListNode<T> build(T... val) {
        if (val == null || val.length == 0) {
            return null;
        }
        ListNode<T> root = null;
        ListNode<T> p = null;
        for (T t : val) {
            ListNode<T> cur = new ListNode<>(t);
            if (root == null) {
                root = cur;
                p = cur;
            } else {
                p.addNext(cur);
                p = p.next;
            }
        }
        return root;
    }


    public static List<ListNode> buildList(Object[] val) {
        List<ListNode> list = new ArrayList<>();
        if (val == null || val.length == 0) {
            return list;
        }
        ListNode root = null;
        ListNode p = null;
        for (Object t : val) {
            ListNode cur = new ListNode<>(t);
            list.add(cur);
            if (root == null) {
                root = cur;
                p = cur;
            } else {
                p.addNext(cur);
                p = p.next;
            }
        }
        return list;
    }

}

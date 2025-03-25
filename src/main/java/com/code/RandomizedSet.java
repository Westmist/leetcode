package com.code;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * 简易 哈希表
 * 插入、删除和随机返回一个元素的时间复杂度都为 O(1)
 */
public class RandomizedSet {

    /**
     * 数组 + 链表
     */
    private Node<Integer>[] arr;

    /**
     * 保存元素的 hash
     * hash + index
     */
    private List<Long> hashIndex;

    /**
     * 随机器
     */
    private Random random;

    /**
     * 扩容负载因子
     */
    private static float loadFactor = 0.75f;

    public RandomizedSet() {
        arr = new Node[8];
        random = new Random();
        hashIndex = new ArrayList<>();
    }

    public boolean contain(int val) {
        int index = findIndex(val);
        Node<Integer> node = arr[index];
        if (node == null) {
            return false;
        }
        return node.con(val);
    }

    public boolean insert(int val) {
        if (contain(val)) {
            return false;
        }
        checkRehash();
        int index = findIndex(val);
        if (arr[index] == null) {
            arr[index] = new Node<>(val);
            hashIndex.add(nodeHashPos(index, 0));
        } else {
            int pos = arr[index].put(val);
            hashIndex.add(nodeHashPos(index, pos));
        }
        return true;
    }

    public boolean remove(int val) {
        int index = findIndex(val);
        Node<Integer> node = arr[index];
        if (node == null) {
            return false;
        }
        return rmNode(index, val);
    }

    public int getRandom() {
        if (hashIndex.isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = random.nextInt(hashIndex.size());
        Long hash = hashIndex.get(index);
        int high = (int) (hash >> 32);
        int low = (int) (hash & 0xFFFFFFFFL);
        return pos(high, low);
    }

    /**
     * 检查是否需要扩容和 rehash
     *
     * @return
     */
    public boolean checkRehash() {
        if ((float) hashIndex.size() / arr.length >= loadFactor) {
            return false;
        }
        // 扩容
        Node<Integer>[] nArr = new Node[arr.length << 1];
        hashIndex.clear();
        for (int i = 0; i < arr.length; i++) {
            Node<Integer> node = arr[i];
            if (node == null) {
                continue;
            }
            while (node != null) {
                Node<Integer> n = node.next;
                node.next = null;
                int nIndex = findIndex(node.val, nArr.length);
                if (nArr[nIndex] == null) {
                    nArr[nIndex] = node;
                    hashIndex.add(nodeHashPos(nIndex, 0));
                } else {
                    int pos = nArr[nIndex].put(node.val);
                    hashIndex.add(nodeHashPos(nIndex, pos));
                }
                node = n;
            }
        }
        arr = nArr;
        return true;
    }

    public int pos(int index, int pos) {
        Node<Integer> node = arr[index];
        if (node == null) {
            throw new NoSuchElementException();
        }
        for (int i = 0; i < pos; i++) {
            node = node.next;
        }
        return node.val;
    }

    public boolean rmNode(int index, int val) {
        Node<Integer> node = arr[index];
        if (node.val == val) {
            arr[index] = null;
            hashIndex.remove(nodeHashPos(index, 0));
            return true;
        }
        Node<Integer> cur = node;
        int pos = 1;
        while (cur.next != null) {
            if (cur.next.val == val) {
                cur.next = cur.next.next;
                hashIndex.remove(nodeHashPos(index, pos));
                return true;
            }
            pos++;
            cur = cur.next;
        }
        return false;
    }

    /**
     * 查找索引
     */
    private int findIndex(int val, int length) {
        int hs = ((Integer) val).hashCode();
        int sign = (hs < 0) ? 1 : 0;
        return Math.abs((hs << sign) % length);
    }

    private int findIndex(int val) {
        return findIndex(val, arr.length);
    }

    private long nodeHashPos(int index, int pos) {
        return (long) index << 32 | pos & 0xFFFFFFFFL;
    }

    static class Node<T> {
        private T val;
        private Node<T> next;

        public Node(T val) {
            this.val = val;
        }

        public boolean con(T val) {
            Node p = this;
            while (p != null) {
                if (p.val == val) {
                    return true;
                }
                p = p.next;
            }
            return false;
        }

        /**
         * 返回插入链表的位置
         */
        public int put(T val) {
            Node p = this;
            int pos = 1;
            while (p.next != null) {
                p = p.next;
                pos++;
            }
            p.next = new Node(val);
            return pos;
        }
    }

    public static void main(String[] args) {
        List<Integer> t = new ArrayList<>();
        RandomizedSet randomizedSet = new RandomizedSet();
        randomizedSet.insert(-1);
        randomizedSet.remove(-2);
        randomizedSet.insert(-2);
        int val = randomizedSet.getRandom();
        randomizedSet.remove(-1);
        randomizedSet.insert(-2);
        int val2 = randomizedSet.getRandom();


        System.out.println(randomizedSet);
    }

}

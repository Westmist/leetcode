package com.junmp;

import lombok.Data;

@Data
public class Node {
    private int x, y; // 坐标
    private int g;  // 起点到当前节点的代价
    private int h;  // 当前节点到终点的预估代价
    Node parent;   //

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.g = Integer.MAX_VALUE;
        this.h = 0;
        this.parent = null;
    }

    // 总代价-用于计算权值，优先尝试
    public int f() {
        return g + h;
    }

    public boolean eq(Node goal) {
        return x == goal.x && y == goal.y;
    }

    public boolean eq(int x, int y) {
        return this.getX() == x && this.getY() == y;
    }

    public void cost(Node parent, Node goal) {
        int g1 = parent == null ? 0 : parent.getG();
        setG(g1 + 1);
        setH(heuristic(this, goal));
        setParent(parent);
    }

    private int heuristic(Node a, Node b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }


}

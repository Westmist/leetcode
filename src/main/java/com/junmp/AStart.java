package com.junmp;

import java.util.*;

public class AStart {
    // 方向常量，用于找邻接的节点 8个方向：右、左、下、上、右下、左下、右上、左上
    private static final int[] dx = {1, -1, 0, 0, 1, -1, 1, -1}; // 8 directions
    private static final int[] dy = {0, 0, 1, -1, 1, 1, -1, -1};

    // 起点和终点
    private Node start, goal;
    // 开放队列
    private PriorityQueue<Node> openList;
    // 关闭集合
    private Set<Node> closedList;

    public AStart(Node start, Node goal) {
        this.start = start;
        this.goal = goal;
        this.openList = new PriorityQueue<>(Comparator.comparingInt(Node::f));
        this.closedList = new HashSet<>();
    }

    // 开始搜索
    public List<Node> findPath(GameMap map) {
        start.setG(0);
        start.setH(heuristic(start, goal));
        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            // 1、已到达目标点
            if (goal.eq(current)) {
                return reconstructPath(current);
            }
            // 已探索过的点放到关闭列表
            closedList.add(current);

            // 2、邻接节点
            for (int i = 0; i < 8; i++) {
                int aX = dx[i] + current.getX();
                int aY = dy[i] + current.getY();
                if (containsCloseList(aX, aY)) {
                    continue;
                }
                Node around = map.buildNode(aX, aY);
                if (around == null) {
                    continue;
                }
                around.cost(current, goal);
                openList.add(around);
            }
        }
        return null;
    }


    private boolean containsCloseList(int x, int y) {
        for (Node node : closedList) {
            if (node.eq(x, y)) {
                return true;
            }
        }
        return false;
    }

    // Jumping function to jump over unnecessary nodes
    private List<Node> jump(GameMap map, Node current, int dirX, int dirY) {
        List<Node> neighbors = new ArrayList<>();
        int x = current.getX() + dirX;
        int y = current.getY() + dirY;

        // 检查是否越界
        if (!map.walkable(x, y)) return neighbors;

        Node next = map.buildNode(x, y);
        if (next != null) {
            neighbors.add(next);
            return neighbors;
        }
        return neighbors;
    }

    // Check if a node is a jump point (important point in the path)
    private boolean isJumpPoint(GameMap map, int x, int y, int dirX, int dirY) {
        if (dirX == 0 && dirY == 0) {
            return true;
        }

        if (dirX != 0 && dirY != 0) {
            // Diagonal direction
            return (map.walkable(x - dirX, y) || map.walkable(x, y - dirY));
        } else {
            // Horizontal or vertical
            return (map.walkable(x + dirX, y) || map.walkable(x, y + dirY));
        }
    }

    // Heuristic function (Manhattan Distance)
    private int heuristic(Node a, Node b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    // 整理路径 - 查找和汇总经历过的节点，然后翻转
    private List<Node> reconstructPath(Node current) {
        List<Node> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }


}

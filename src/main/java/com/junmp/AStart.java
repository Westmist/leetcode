package com.junmp;

import java.util.*;


/**
 * TODO List
 * <p>
 * 常用算法入门
 * <p>
 * 1、寻路算法
 * 2、视野算法
 * 3、跳表排序算法
 * 4、令牌桶限流算法
 * 5、贪心背包算法
 * 6、缓存淘汰
 */
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
                GameMap.Block aroundBlock = map.findAroundBlock(current.getBlock(), dx[i], dy[i]);
                if (aroundBlock == null || !aroundBlock.walkable()) {
                    continue;
                }
                if (contains(closedList, aroundBlock)) {
                    continue;
                }
                if (!contains(openList, aroundBlock)) {
                    Node around = new Node(aroundBlock);
                    around.cost(current, goal);
                    openList.add(around);
                } else {
                    // 在开放列表者更新 cost (移除后新加)

                }
            }
        }
        return null;
    }

    private boolean contains(Collection<Node> collection, GameMap.Block block) {
        for (Node node : collection) {
            if (block == node.getBlock()) {
                return true;
            }
        }
        return false;
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

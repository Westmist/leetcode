package com.junmp;

public class GameMap {

    private int[][] grid = new int[][]{
            {0, 0, 0, 0, 0},
            {1, 1, 1, 0, 0},
            {0, 0, 0, 1, 0},
            {0, 1, 0, 0, 1},
            {0, 0, 0, 0, 0}
    };

    public int width() {
        return grid.length;
    }

    public int height() {
        return grid[0].length;
    }

    public boolean walkable(int x, int y) {
        return x >= 0 && y >= 0 && x < width() && y < height() && grid[x][y] != 1;
    }

    public Node buildNode(int x, int y) {
        if (!walkable(x, y)) {
            return null;
        }
        return new Node(x, y);
    }


}

package com.junmp;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

public class GameMap {

    private int[][] grid = new int[][]{
            {0, 0, 0, 0, 0},
            {1, 1, 1, 0, 0},
            {0, 0, 0, 1, 0},
            {0, 1, 0, 0, 1},
            {0, 0, 0, 0, 0}
    };

    private Map<Integer, Block> blocks = new HashMap<>();

    public int width() {
        return grid.length;
    }

    public int height() {
        return grid[0].length;
    }

    public GameMap() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                byte type = (byte) grid[i][j];
                int id = (i + 1) << 16 | (j + 1) & 0xffff;
                blocks.put(id, new Block((short) (i + 1), (short) (j + 1), type));
            }
        }
    }

    public Block findBlock(int x, int y) {
        int id = x << 16 | y & 0xffff;
        return blocks.get(id);
    }


    public Block findAroundBlock(Block block, int dx, int dy) {
        int x = block.getX() + dx;
        int y = block.getY() + dy;
        int id = x << 16 | y & 0xffff;
        return blocks.get(id);
    }


    public boolean walkable(int x, int y) {
        return x >= 0 && y >= 0 && x < width() && y < height() && grid[x][y] != 1;
    }

    @Data
    public static class Block {
        private final short x, y;
        private final byte type;

        public boolean walkable() {
            return type != 1;
        }
    }


}

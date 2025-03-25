package com.junmp;

import java.util.List;

public class Bootstrap {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.test();
    }

    public void test() {
        GameMap map = new GameMap();
        GameMap.Block startBlock = map.findBlock(1, 1);
        GameMap.Block endBlock = map.findBlock(5, 5);
        Node start = new Node(startBlock);
        Node goal = new Node(endBlock);
        AStart AStart = new AStart(start, goal);
        List<Node> path = AStart.findPath(map);
        System.out.println(path);
    }

}
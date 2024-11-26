package com.junmp;

import java.util.List;

public class Bootstrap {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.test();
    }

    public void test() {
        GameMap map = new GameMap();
        Node start = new Node(0, 0);
        Node goal = new Node(4, 4);
        AStart AStart = new AStart(start, goal);
        List<Node> path = AStart.findPath(map);
        System.out.println(path);
    }

}
package com.example.scotland_yard_board_game;

import java.util.List;
import java.util.Random;

public class Node {
    private final int number;
    private boolean isFree;
    private List<Node> neighbourTaxi; 
    private List<Node> neighbourBus;
    private List<Node> neighbourUground;
    private List<Node> neighbourFerry;

    public Node(int number) {
        this.number = number;
        this.isFree = true;
    }

    //Hier there are just some nodes, MUST change start nodes to those in the real game
    //TODO: implement a check that the start node is free
    public static Node chooseStartNode() {
        int[] startNodes = {11, 23, 45, 60, 123, 145, 166, 190};
        Random random = new Random();
        int number = random.nextInt(startNodes.length);
        Node startNode = new Node(startNodes[number]);
        startNode.isFree = false;
        return startNode;
    }
}

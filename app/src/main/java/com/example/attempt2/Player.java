package com.example.attempt2;

public abstract class Player {
    private String nickname;
    private Colours colour;
    private Node currentNode;
    private Node startNode;         //will be chosen randomly
    private boolean myMove;

    //how many tickets do a player have
    protected int busTickets;
    protected int taxiTickets;
    protected int undergroundTickets;
    protected int ferryTickets;

    public Player(Colours colour, String nickname) {
        this.colour = colour;
        if (nickname != null) {
            this.nickname = nickname;
        }
        startNode = Node.chooseStartNode();
        currentNode = startNode;

    }

    public abstract void makeMove();

}

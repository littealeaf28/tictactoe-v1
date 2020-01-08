package com.tealeaf.tictactoe;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private String moveToken;
    private int startTurn;

    public Player(String name) {
        this.name = name;
        this.moveToken = "X";
        this.startTurn = 0;
    }

    public String getMoveToken() { return moveToken; }
    public int getStartTurn() { return startTurn; }

    public void setMoveToken (String moveToken) {
        this.moveToken = moveToken;
    }
    public void setStartTurn (int startTurn) {
        this.startTurn = startTurn;
    }

    public String toString() { return name; }

}

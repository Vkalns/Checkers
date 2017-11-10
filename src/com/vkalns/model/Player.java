package com.vkalns.model;

import java.util.Stack;

/**
 * Player class will keep all the moves done by player
 */
public class Player
{
    String name;
    String pieceColour;
    public Stack<MoveBundle> movesTaken = new Stack<>();
    public Stack<MoveBundle> movesUndo = new Stack<>();
    public Player(String name, String pieceColour)
    {
        this.name = name;
        this.pieceColour = pieceColour;
    }

    public Player(String pieceColour) {
        this.pieceColour = pieceColour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPieceColour() {
        return pieceColour;
    }

    public void setPieceColour(String pieceColour)
    {
        this.pieceColour = pieceColour;
    }
}

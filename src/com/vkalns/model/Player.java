package com.vkalns.model;

public class Player
{
    String name;
    String pieceColour;
    String piece;

    public Player(String name, String pieceColour)
    {
        this.name = name;
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

    public void setPieceColour(String pieceColour) {
        this.pieceColour = pieceColour;
    }
}

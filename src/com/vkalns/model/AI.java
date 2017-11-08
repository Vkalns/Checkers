package com.vkalns.model;

import java.util.ArrayList;

public class AI extends Player
{
    public ArrayList<Move> allPossibleMoves = new ArrayList<>();

    public AI(String playerName,String pieceColour)
    {
        super(playerName,pieceColour);
    }

    public void setAllPossibleMoves(ArrayList<Move> allPossibleMoves) {
        this.allPossibleMoves = allPossibleMoves;
    }

    public ArrayList<Move> getAllPossibleMoves()
    {
        return allPossibleMoves;
    }



}

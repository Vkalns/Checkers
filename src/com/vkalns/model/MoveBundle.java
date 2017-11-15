package com.vkalns.model;

import java.util.ArrayList;

public class MoveBundle
{
    ArrayList<Move> allMoves = new ArrayList<>();
    public MoveBundle(Move move)
    {
        allMoves.add(move);
    }

    public ArrayList<Move> getAllMoves()
    {
        return allMoves;
    }

    public void addMove(Move move)
    {
        allMoves.add(move);
    }

    public Move getLastMove(){return allMoves.get(allMoves.size()-1);}
}

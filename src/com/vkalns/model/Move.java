package com.vkalns.model;

import java.util.Stack;

public class Move
{
    Position startingPos = new Position();
    Position targetPos = new Position();



    public boolean isLegal(Piece piece)
    {
        if ((startingPos.getyPos()<=targetPos.getyPos())&& piece.getState().equals("normal"))
        {
            System.out.println("You can't move this figure there.");
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean hasFigure()
    {
        //TODO checks if the move starting position has Piece on it
        return true;
    }

}

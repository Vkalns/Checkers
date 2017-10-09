package com.vkalns.model;

import java.util.Stack;

public class Move

{
    Board board = new Board();
    String[] input;
    String startingPos = "";
    String targetPos = "";

    public Move(String [] input,Board board)
    {
        this.board = board;
        this.input = input;
        startingPos = input[0];
        targetPos = input[1];
    }

    //getters

    public String getStartingPos()
    {
        return startingPos;
    }

    public String getTargetPos()
    {
        return targetPos;
    }


    //    public boolean isLegal(Piece piece)
//    {
//        if ((startingPos.getyPos()<=targetPos.getyPos())&& piece.getState().equals("normal"))
//        {
//            System.out.println("You can't move this figure there.");
//            return false;
//        }
//        else
//        {
//            return true;
//        }
//    }

    public boolean hasFigureToMove(String startingPos)//temp? piece with starting coordinates?
    {

        if(true)//TODO checks if the move starting position has Piece on it
        {
        return true;
        }
        return false;
    }

    public void undoMove(){}

}

package com.vkalns.model;

import java.util.ArrayList;

public class Board
{

    public String [][] board  = {
            {"w"," ","w"," ","w"," ","w"," "},
            {" ","w"," ","w"," ","w"," ","w"},
            {"w"," ","w"," ","w"," ","w"," "},
            {" ","*"," ","*"," ","*"," ","*"},
            {"*"," ","*"," ","*"," ","*"," "},
            {" ","b"," ","b"," ","b"," ","b"},
            {"b"," ","b"," ","b"," ","b"," "},
            {" ","b"," ","b"," ","b"," ","b"}};



    public Board  (){};



    public void drawBoard()
    {
        for(int y=1;y<9;y++ )
        {
            System.out.print(y+"|");
            for(int x=1;x<9;x++ )
            {
                System.out.print(board[y-1][x-1]+"|");
                if (x==8)
                {
                    System.out.println("");
                }
            }
            if (y==8)
            {
                System.out.println("  A B C D E F G H");
            }
        }
    }

    public void updateBoard(Move move, String colour,boolean undo, boolean redo)
            //String playerColour,int[]startingCoordinates,int[]endCoordinates,boolean undo
    {
        if(undo==false)
        {//if we moving players or AI piece we swap the array elements
            board[move.getStartingPosNummeric()[1]-1][move.getStartingPosNummeric()[0]-1]="*";

            board[move.getTargetPosNummeric()[1]-1][move.targetPosNummeric[0]-1]=colour;



            drawBoard();
        }
        else //if we undo the move we swap the elements from w/b back to *
            {
                board[move.getStartingPosNummeric()[1]-1][move.getStartingPosNummeric()[0]-1]=colour;

                board[move.getTargetPosNummeric()[1]-1][move.targetPosNummeric[0]-1]="*";
                drawBoard();
            }
    }

    public int checkPieceCount(String colour)
    {//checks how many elements in Board array equals piece colour
        int count = 0;
        for(int y=0;y<8;y++ )
        {
            for(int x=0;x<8;x++ )
            {
                if (board[y][x].equalsIgnoreCase(colour))
                {
                    count++;
                }
            }
        }
        return count;
    }


}

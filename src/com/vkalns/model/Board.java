package com.vkalns.model;

import java.util.ArrayList;

public class Board
{
    private ArrayList<Piece> myPieces = new ArrayList<>();
    private ArrayList<Piece> aiPieces = new ArrayList<>();
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

    public void updateBoard(String playerColour,int[]startingCoordinates,int[]endCoordinates)
    {
        board[startingCoordinates[1]-1][startingCoordinates[0]-1]="*";

        board[endCoordinates[1]-1][endCoordinates[0]-1]=playerColour;

        drawBoard();

//        System.out.println(board[startingCoordinates[0]-1]
//                                [startingCoordinates[1]-1]);
//        System.out.println(board[endCoordinates[1]-1][endCoordinates[0]-1]);


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

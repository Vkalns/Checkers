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


}

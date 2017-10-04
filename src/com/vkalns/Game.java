package com.vkalns;

import com.vkalns.model.Board;
import com.vkalns.model.Move;

import java.io.Console;
import java.util.Stack;

public class Game
{
    Stack<Move> movesTaken = new Stack<>();
    Board board = new Board();

    public void start()
    {
        String [][] board  = {
                {"w"," ","w"," ","w"," ","w"," "},
                {" ","w"," ","w"," ","w"," ","w"},
                {"w"," ","w"," ","w"," ","w"," "},
                {" ","*"," ","*"," ","*"," ","*"},
                {"*"," ","*"," ","*"," ","*"," "},
                {" ","b"," ","b"," ","b"," ","b"},
                {"b"," ","b"," ","b"," ","b"," "},
                {"_","b","_","b","_","b","_","b"}};

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
                System.out.println("  1 2 3 4 5 6 7 8");
            }
        }
    }
        //here all the methods will be called to play the game till the result
        //This method will be called in Main class


    public void undoMove()
    {
        //TODO: remove last element from movesTaken Stack
    }

    public void end()
    {
        //Game ending method to
        System.out.println("The game has ended");
    }

}

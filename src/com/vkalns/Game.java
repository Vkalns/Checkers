package com.vkalns;

import com.vkalns.model.Move;

import java.util.Stack;

public class Game
{
    Stack<Move> movesTaken = new Stack<>();

    public void start()
    {
        //here all the methods will be called to play the game till the result
        //This method will be called in Main class

    }

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

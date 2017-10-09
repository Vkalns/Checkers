package com.vkalns;

import com.vkalns.model.Board;
import com.vkalns.model.Move;
import com.vkalns.model.Prompter;

import java.io.Console;
import java.util.Stack;

public class Game
{
    Stack<Move> movesTaken = new Stack<>();
    Stack<Move> movesUndo = new Stack<>();
    Board board = new Board();
    Prompter prompter = new Prompter();

    public void start()
    {
        board.drawBoard();
        movesTaken.push(new Move(prompter.askForMove(),board));
        System.out.println("Moving a piece from: "+movesTaken.peek().getStartingPos()+
                " to "+movesTaken.peek().getTargetPos());
    
    }
        //here all the methods will be called to play the game till the result
        //This method will be called in Main class


    public void undoMove()
    {
        //TODO: remove last element from movesTaken Stack
        if(!movesTaken.isEmpty())
        {//takes move off and puts it in another undo move stack
            //movesTaken.peek().run();
            movesUndo.push(movesTaken.pop());
        }
    }

    public void end()
    {
        //Game ending method to
        System.out.println("The game has ended");
    }

}

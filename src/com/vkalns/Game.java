package com.vkalns;

import com.vkalns.model.Board;
import com.vkalns.model.Move;
import com.vkalns.model.Prompter;

import java.io.Console;
import java.util.Scanner;
import java.util.Stack;

public class Game
{
    Stack<Move> movesTaken = new Stack<>();
    Stack<Move> movesUndo = new Stack<>();
    Board board = new Board();
    String playerColour = "w";
    String aiColour = "b";
    Prompter prompter = new Prompter();
    Scanner scanner = new Scanner(System.in);
    String input = "";

    public void startVsAi()
    {
        board.drawBoard();//Draws a starting board
        while(board.checkPieceCount(playerColour)>0)//Keep playing while you have pieces on board
        {
            System.out.println("Please enter your next move starting and ending coordinates separated by comma");
            System.out.println("If you want to undo your last move please enter \"undo\"");
            System.out.println("If you want to redo your last undo please type \"redo\"");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("undo"))
            {
                undoMove();
            }
            else if (input.equalsIgnoreCase("redo"))
            {
                redoMove();
            }
            else
                {
                    movesTaken.push(new Move(prompter.askForMove(input),board));//add new move
                    System.out.println("Moving a piece from: "+movesTaken.peek().getStartingPos()+
                            " to "+movesTaken.peek().getTargetPos());
                    //draws an updated position of the board when basic move is made
                    board.updateBoard(playerColour,
                            movesTaken.peek().getStartingPosNummeric(),
                            movesTaken.peek().getTargetPosNummeric(),false);
                }
            displayMovesHistory(movesTaken);
        }
    }




    //here all the methods will be called to play the game till the result
    //This method will be called in Main class


    public void displayMovesHistory(Stack<Move> movesTaken)
    {//displays players move history, so he can cancel move if he wants
        int count = 1;
        System.out.println("List of moves made by player:");
        for (Move move : movesTaken)
        {
            System.out.println(count+": "+ move.getStartingPos()+ " to "+move.getTargetPos());
            count++;
        }
    }

    public void undoMove()
    {
        //TODO: remove last element from movesTaken Stack
        if(!movesTaken.isEmpty())
        {//takes move off and puts it in another undo move stack
            board.updateBoard(playerColour,
                    movesTaken.peek().getStartingPosNummeric(),
                    movesTaken.peek().getTargetPosNummeric(),true);
            movesUndo.push(movesTaken.pop());
        }
        else
            {
                System.out.println("No moves to undo");
            }
    }

    public void redoMove()
    {
        //TODO: remove last element from movesUndo Stack
        if(!movesUndo.isEmpty())
        {//takes move off and puts it in another undo move stack
            board.updateBoard(playerColour,
                    movesUndo.peek().getStartingPosNummeric(),
                    movesUndo.peek().getTargetPosNummeric(),false);
            movesTaken.push(movesUndo.pop());
        }
        else
            {
                System.out.println("No mo moves to redo");
            }
    }

    public void end()
    {
        //Game ending method to
        System.out.println("The game has ended");
    }

}

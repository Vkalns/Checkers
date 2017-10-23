package com.vkalns;

import com.vkalns.model.Board;
import com.vkalns.model.Move;
import com.vkalns.model.Player;
import com.vkalns.model.Prompter;

import java.io.Console;
import java.util.Scanner;
import java.util.Stack;

public class Game
{
    Stack<Move> movesTaken = new Stack<>();
    Stack<Move> movesUndo = new Stack<>();
    Board board = new Board();
    Player playerOne = new Player("human","w");
    Player playerTwo = new Player("computer","b");
    Prompter prompter = new Prompter();
    Scanner scanner = new Scanner(System.in);
    String input = "";

    public void startVsAi()
    {
        board.drawBoard();//Draws a starting board
        while(board.checkPieceCount(playerOne.getPieceColour())>0)//Keep playing while you have pieces on board
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

                    if (!movesTaken.peek().hasFigureToMove(playerOne.getPieceColour()))
                        //this checks if the starting coordinates provided has the piece on it
                    {
                        System.out.println("There is no piece on coordinates: "+movesTaken.peek().getStartingPos().toUpperCase());
                        movesTaken.pop();//removes move from stack as it's not valid
                    }
                    else
                        {
                            System.out.println("Moving a piece from: " + movesTaken.peek().getStartingPos() +
                                    " to " + movesTaken.peek().getTargetPos());
                            //draws an updated position of the board when basic move is made
                            board.updateBoard(movesTaken.peek(),playerOne.getPieceColour(),false,false);
                        }
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
            board.updateBoard(movesTaken.peek(),playerOne.getPieceColour(),true,false);
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
            board.updateBoard(movesTaken.peek(),playerOne.getPieceColour(),false,true);
            movesTaken.push(movesUndo.pop());
        }
        else
            {
                System.out.println("No mo moves to redo");
            }
    }

    public boolean canMoveBeValid(Move move)
    {
        boolean isValid = false;
//        if(move.hasFigureToMove(playerOne.getPieceColour())
//                &&move.getTargetPosNummeric()[0]
//
//        {
//
//        }
        return isValid;
    }

    public void end()
    {
        //Game ending method to
        System.out.println("The game has ended");
    }

}

package com.vkalns;

import com.vkalns.model.Board;
import com.vkalns.model.Player;
import com.vkalns.model.Prompter;
import com.vkalns.model.Move;

import java.util.Arrays;
import java.util.Scanner;

public class Game
{

    Board board = new Board();
//    Player playerOne = new Player("human","w");
//    Player playerTwo = new Player("computer","b");
    Prompter prompter = new Prompter(board);
    Scanner scanner = new Scanner(System.in);
    String input = "";


    public void startVsAi(String playerName)
    {

        board.drawBoard();//Draws a starting board
        Player human = new Player(playerName,"w");
        Player computer = new Player("b");
        while(board.checkPieceCount(human.getPieceColour())>0)//Keep playing while you have pieces on board
        {
            System.out.println(board.checkForCapture("b"));

            System.out.println("Please enter your next move starting and ending coordinates separated by comma");
            System.out.println("If you want to undo your last move please enter \"undo\"");
            System.out.println("If you want to redo your last undo please type \"redo\"");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("undo"))
            {
                undoMove(human);
            }
            else if (input.equalsIgnoreCase("redo"))
            {
                redoMove(human);
            }

            else if(2>3)
            {
                //TODO: here I need to add checking for captures
            }

            else
                {
                    String [] coordinates = prompter.askForMove(input);//ask for move coordinates and validate them
                    human.movesTaken.push(new Move(coordinates,board));//add new move
                    System.out.println("Moving a piece from: " + human.movesTaken.peek().getStartingPos() +
                                    " to " + human.movesTaken.peek().getTargetPos());
                    System.out.println(Arrays.toString(human.movesTaken.peek().getStartingPosNummeric()));
                    System.out.println(Arrays.toString(human.movesTaken.peek().getTargetPosNummeric()));
                    System.out.println(board.checkRightCapture(2,2,"w"));
                    System.out.println(board.checkLeftCapture(2,2,"w"));
//                    if (human.movesTaken.peek().getTargetPosNummeric()[1]==human.movesTaken.peek().getStartingPosNummeric()[1]+2)
//                    {
//                        human.movesTaken.peek().advancedMove(human.movesTaken.peek().getStartingPosNummeric(),
//                                human.movesTaken.peek().getTargetPosNummeric(),human.getPieceColour());
//                    }
//                    //draws an updated position of the board when basic move is made
                    board.updateBoard(human.movesTaken.peek(),human.getPieceColour(),false,false);
                }
            displayMovesHistory(human);
        }
    }




    //here all the methods will be called to play the game till the result
    //This method will be called in Main class


    public void displayMovesHistory(Player player)
    {//displays players move history, so he can cancel move if he wants
        int count = 1;
        System.out.println("List of moves made by "+player.getName()+ ":");
        for (Move move : player.movesTaken)
        {
            System.out.println(count+": "+ move.getStartingPos()+ " to "+move.getTargetPos());
            count++;
        }
    }

    public void undoMove(Player player)
    {
        //TODO: remove last element from movesTaken Stack
        if(!player.movesTaken.isEmpty())
        {//takes move off and puts it in another undo move stack
            board.updateBoard(player.movesTaken.peek(),player.getPieceColour(),true,false);
            player.movesUndo.push(player.movesTaken.pop());
        }
        else
            {
                System.out.println("No moves to undo");
            }
    }

    public void redoMove(Player player)
    {
        //TODO: remove last element from movesUndo Stack
        if(!player.movesUndo.isEmpty())
        {//takes move off and puts it in another undo move stack
            board.updateBoard(player.movesTaken.peek(),player.getPieceColour(),false,true);
            player.movesTaken.push(player.movesUndo.pop());
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

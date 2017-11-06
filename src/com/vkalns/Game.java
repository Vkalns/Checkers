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
            doHumanMove(human);
        }
    }

    public void startVsPlayer(String player1,String player2)
    {
        board.drawBoard();//Draws a starting board
        Player playerOne = new Player(player1,"w");
        Player playerTwo = new Player(player2, "b");
        while(board.checkPieceCount(playerOne.getPieceColour())>0 || board.checkPieceCount(playerTwo.getPieceColour())>0)//Keep playing while you have pieces on board
        {

            doHumanMove(playerTwo);
            doHumanMove(playerOne);
        }
    }

    public void doHumanMove(Player player)
    {
//        System.out.println(board.checkForCapture("w"));
        if(!board.checkForCapture(player.getPieceColour()).isEmpty())
        {
            //System.out.println(board.checkForCapture(player.getPieceColour()));
            String[] captureMoveCoordinates = prompter.askForCaptureMove(board.checkForCapture(player.getPieceColour()),player);
            //we ask for valid coordinates

            player.movesTaken.push(new Move(captureMoveCoordinates,board));//when we get them we create the move and update screen
            System.out.println("Moving a piece from: " + player.movesTaken.peek().getStartingPos() +
                    " to " + player.movesTaken.peek().getTargetPos());

            //TODO: need to get capture figure update method
            player.movesTaken.peek().advancedMove(player.movesTaken.peek().getStartingPosNummeric(),
                    player.movesTaken.peek().getTargetPosNummeric(),player.getPieceColour());
            //this updates capturedPieces coordinates

            board.updateBoard(player.movesTaken.peek(),player.getPieceColour(),false,false);
            displayMovesHistory(player);
        }
        else
        {
            System.out.println(player.getName()+" please enter your next move starting and ending coordinates separated by comma");
            System.out.println("If you want to undo your last move please enter \"undo\"");
            System.out.println("If you want to redo your last undo please type \"redo\"");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("undo"))
            {
                undoMove(player);
                doHumanMove(player);
            }
            else if (input.equalsIgnoreCase("redo"))
            {
                redoMove(player);
            }
            else
            {
                String [] coordinates = prompter.askForMove(input,player);//ask for move coordinates and validate them
                player.movesTaken.push(new Move(coordinates,board));//add new move
                System.out.println("Moving a piece from: " + player.movesTaken.peek().getStartingPos() +
                        " to " + player.movesTaken.peek().getTargetPos());
                System.out.println(Arrays.toString(player.movesTaken.peek().getStartingPosNummeric()));
                System.out.println(Arrays.toString(player.movesTaken.peek().getTargetPosNummeric()));
                //System.out.println(board.checkRightCapture(2,2,"w"));
                //System.out.println(board.checkLeftCapture(2,2,"w"));
//                    if (human.movesTaken.peek().getTargetPosNummeric()[1]==human.movesTaken.peek().getStartingPosNummeric()[1]+2)
//                    {
//                        human.movesTaken.peek().advancedMove(human.movesTaken.peek().getStartingPosNummeric(),
//                                human.movesTaken.peek().getTargetPosNummeric(),human.getPieceColour());
//                    }
//                    //draws an updated position of the board when basic move is made
                board.updateBoard(player.movesTaken.peek(),player.getPieceColour(),false,false);
            }
            displayMovesHistory(player);

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

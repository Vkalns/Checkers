package com.vkalns;

import com.vkalns.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game
{

    Board board = new Board();
    Prompter prompter = new Prompter(board);
    Scanner scanner = new Scanner(System.in);
    String input = "";


    public void startVsAi(String playerName)
    {

        board.drawBoard();//Draws a starting board
        Player human = new Player(playerName,"w");
        AI computer = new AI("Computer","b");
        while(board.checkPieceCount(human.getPieceColour())>0)//Keep playing while you have pieces on board
        {
            doHumanMove(human,computer);
            doComputerMove(computer);
        }
    }

    public void startVsPlayer(String player1,String player2)
    {
        board.drawBoard();//Draws a starting board
        Player playerOne = new Player(player1,"w");
        Player playerTwo = new Player(player2, "b");
        while(board.checkPieceCount(playerOne.getPieceColour())>0 || board.checkPieceCount(playerTwo.getPieceColour())>0)//Keep playing while you have pieces on board
        {
            doHumanMove(playerOne,playerTwo);
            doHumanMove(playerTwo,playerOne);

        }
    }

    public void startAiVsAi()
    {
        board.drawBoard();//Draws a starting board
        AI computer1 = new AI("Omega","w");
        AI computer2 = new AI("Grand Master","b");
        while(board.checkPieceCount(computer1.getPieceColour())>0&&(board.checkPieceCount(computer2.getPieceColour())>0))//Keep playing while you have pieces on board
        {
            doComputerMove(computer1);
            doComputerMove(computer2);
        }

    }

    public void doHumanMove(Player player1,Player player2)
    {

//        System.out.println(board.checkForCapture("w"));
        if(!board.checkForCapture(player1.getPieceColour()).isEmpty())
        {
            System.out.println(player1.getName()+" you must capture a piece. Please enter your next move starting and ending coordinates separated by comma");
            System.out.println("If you want to undo your last move please enter \"undo\"");
            System.out.println("If you want to redo your last undo please type \"redo\"");
            input = scanner.nextLine();

            String [] coordinates = prompter.askForMove(input,player1);//ask for move coordinates and validate them
            System.out.println(Arrays.toString(coordinates));
            Move move = new Move(coordinates,board);
            MoveBundle moveBundle= new MoveBundle(move);
            player1.movesTaken.push(moveBundle);
            if (!board.isCaptureMove(move.getStartingPosNummeric(),player1.getPieceColour()))//check if it's a capture move
            {
                move=null;
                player1.movesTaken.pop();
                doHumanMove(player1,player2);
            }

            move.addCapturedPiecePositions(move.getStartingPosNummeric(),move.getTargetPosNummeric(),player1.getPieceColour());

            //here I should check if there is still capture at the final coordinates.

//            //System.out.println(board.checkForCapture(player.getPieceColour()));
//            String[] captureMoveCoordinates = prompter.askForCaptureMove(board.checkForCapture(player1.getPieceColour()),player1);
//            //we ask for valid coordinates
//
//            player1.movesTaken.push(new Move(captureMoveCoordinates,board));//when we get them we create the move and update screen
//            System.out.println("Moving a piece from: " + player1.movesTaken.peek().getStartingPos() +
//                    " to " + player1.movesTaken.peek().getTargetPos());
//
//            //TODO: need to get capture figure update method
//            player1.movesTaken.peek().addCapturedPiecePositions(player1.movesTaken.peek().getStartingPosNummeric(),
//                    player1.movesTaken.peek().getTargetPosNummeric(),player1.getPieceColour());
//            //this updates capturedPieces coordinates

            board.updateBoard(player1.movesTaken.peek().getAllMoves().get(0),player1.getPieceColour(),false,false);

            displayMovesHistory(player1);
        }

        else
        {
            System.out.println(player1.getName()+" please enter your next move starting and ending coordinates separated by comma");
            System.out.println("If you want to undo your last move please enter \"undo\"");
            System.out.println("If you want to redo your last undo please type \"redo\"");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("undo"))
            {
                undoMove(player1,player2);
                //undoMove(player1);
                doHumanMove(player1,player2);
            }
            else if (input.equalsIgnoreCase("redo"))
            {
                redoMove(player1,player2);
                doHumanMove(player1,player2);
            }
            else
            {
                String [] coordinates = prompter.askForMove(input,player1);//ask for move coordinates and validate them
                Move move = new Move(coordinates,board);
                player1.movesTaken.push(new MoveBundle(move));//add new moveBundle
                System.out.println("Moving a piece from: " + move.getStartingPos() +
                        " to " + move.getTargetPos());
//                System.out.println(Arrays.toString(player1.movesTaken.peek().getStartingPosNummeric()));
//                System.out.println(Arrays.toString(player1.movesTaken.peek().getTargetPosNummeric()));
                //System.out.println(board.checkRightCapture(2,2,"w"));
                //System.out.println(board.checkLeftCapture(2,2,"w"));
//                    if (human.movesTaken.peek().getTargetPosNummeric()[1]==human.movesTaken.peek().getStartingPosNummeric()[1]+2)
//                    {
//                        human.movesTaken.peek().addCapturedPiecePositions(human.movesTaken.peek().getStartingPosNummeric(),
//                                human.movesTaken.peek().getTargetPosNummeric(),human.getPieceColour());
//                    }
//                    //draws an updated position of the board when basic move is made
                board.updateBoard(move,player1.getPieceColour(),false,false);
            }
            displayMovesHistory(player1);

        }
    }

    public void doComputerMove(AI computer)
    {
        computer.setAllPossibleMoves(getAIMoves(computer,board));
        System.out.println(computer.getAllPossibleMoves().size());
        Random random = new Random();
        Move computerMove = computer.getAllPossibleMoves().get(random.nextInt(computer.getAllPossibleMoves().size()));
        //here we push a move to moves taken stack by choosing a random move from ArrayList
        computer.movesTaken.push(new MoveBundle(computerMove));
        //and then we update the screen
        try
        {
            Thread.sleep(1000);
            System.out.println(computer.getName()+" moving a piece from: " + computerMove.changeToLetters(computerMove.getStartingPosNummeric()) +
                    " to " + computerMove.changeToLetters(computerMove.getTargetPosNummeric()));
            Thread.sleep(1000);
            board.updateBoard(computerMove,computer.getPieceColour(),false,false);
            Thread.sleep(1000);
            displayMovesHistory(computer);
            Thread.sleep(3000);
        }
        catch (Exception ex)
        {
            System.out.println("Something went wrong with Thread.sleep()"+ex.getMessage());
        }

    }

    public ArrayList<Move> getAIMoves(AI computer,Board board)
    {
        ArrayList<Move>AiMoves = new ArrayList<>();
        if(board.checkForCapture(computer.getPieceColour()).isEmpty())//if there is no captures for computer to do
        {
            //we loop through the board to find AI pieces
            for (int y = 0; y < 8; y++)
            {
                for (int x = 0; x < 8; x++)
                {
                    if (board.board[y][x].equalsIgnoreCase(computer.getPieceColour()))
                    {
                        if ((computer.getPieceColour().equalsIgnoreCase("b")||
                                board.board[y][x].equals("W"))//if Ai plays with blacks
                                && ((board.checkRightUpwards(y + 1, x - 1))//this function checks yx coordinates are in range for captures that's why its edited
                                && board.board[y - 1][x + 1].equalsIgnoreCase("*")))
                        {
                            int[] startingCoordinates = {y, x};
                            int[] endCoordinates = {y - 1, x + 1};
                            AiMoves.add(new Move(startingCoordinates, endCoordinates, board));
                        }
                        else if ((computer.getPieceColour().equalsIgnoreCase("b")||
                                board.board[y][x].equals("W"))
                                //if Ai plays with blacks
                                && ((board.checkLeftUpwards(y + 1, x + 1))//this function checks yx coordinates are in range for captures that's why its edited
                                && board.board[y - 1][x - 1].equalsIgnoreCase("*")))
                        {
                            int[] startingCoordinates = {y, x};
                            int[] endCoordinates = {y - 1, x - 1};
                            AiMoves.add(new Move(startingCoordinates, endCoordinates, board));
                        }
                        else if ((computer.getPieceColour().equalsIgnoreCase("w")
                                ||
                                board.board[y][x].equals("B"))//if Ai plays with whites
                                && ((board.checkRightDownwards(y-1, x-1))//this function checks yx coordinates are in range for captures that's why its edited
                                && board.board[y + 1][x + 1].equalsIgnoreCase("*")))
                        {
                            int[] startingCoordinates = {y, x};
                            int[] endCoordinates = {y + 1, x + 1};
                            AiMoves.add(new Move(startingCoordinates, endCoordinates, board));
                        }
                        else if ((computer.getPieceColour().equalsIgnoreCase("w")||
                                board.board[y][x].equals("B"))//if Ai plays with whites
                                && ((board.checkLeftDownwards(y-1, x+1))//this function checks yx coordinates are in range for captures that's why its edited
                                && board.board[y + 1][x - 1].equalsIgnoreCase("*")))
                        {
                            int[] startingCoordinates = {y, x};
                            int[] endCoordinates = {y + 1, x - 1};
                            AiMoves.add(new Move(startingCoordinates, endCoordinates, board));
                        }
                    }
                }
            }
        }
        else//if there is capture to be done
            {
                int y = board.checkForCapture(computer.getPieceColour()).get(0);
                int x = board.checkForCapture(computer.getPieceColour()).get(1);
                //taking first coordinates from the list of pieces which can capture
                int[] startingCoordinates = {y,x};

            }

//        else
//            {
//                int[] startingPos = {board.checkForCapture(computer.getPieceColour()).get(0),
//                        board.checkForCapture(computer.getPieceColour()).get(1)};
//                int[] endingPos;
//                if(board.checkRightCapture(startingPos[0],startingPos[1],computer.getPieceColour()))
//                {
//                    if (board.checkRightUpwards(startingPos[0],startingPos[1]))
//                    {
//
//                    }
//                }
//            }
        return AiMoves;
    }




    //here all the methods will be called to play the game till the result
    //This method will be called in Main class


    public void displayMovesHistory(Player player)
    {//displays players move history, so he can cancel move if he wants
        if (player.getClass()==AI.class)
        {
            int count = 1;
            System.out.println("List of moves made by "+player.getName()+ ":");
            for (MoveBundle moveBundle : player.movesTaken)
            {
                Move firstMove = moveBundle.getAllMoves().get(0);
                if (moveBundle.getAllMoves().size()==1)
                    //if there is just one move in MoveBundle
                {
                    System.out.println(count+": "+firstMove.changeToLetters(firstMove.getStartingPosNummeric())+
                            " to "+firstMove.changeToLetters(firstMove.getTargetPosNummeric()));
                    //we print out history as normal
                }
                else
                    {
                        System.out.print(count+": "+firstMove.changeToLetters(firstMove.getStartingPosNummeric())+ " :");

                        for (Move move : moveBundle.getAllMoves())
                        {
                            System.out.print(move.changeToLetters(move.getTargetPosNummeric())+ " : ");
                        }


                    }

            }
        }
        else
            {
                int count = 1;
                System.out.println("List of moves made by "+player.getName()+ ":");
                for (MoveBundle moveBundle : player.movesTaken)
                {
                    Move firstMove = moveBundle.getAllMoves().get(0);
                    System.out.println(count+": "+ firstMove.getStartingPos()+ " to "+firstMove.getTargetPos());
                    count++;
                }
            }
    }

    public void undoMove(Player player1, Player player2)
    {
        //TODO: remove last element from movesTaken Stack
        if(!player1.movesTaken.isEmpty())
        {//takes move off and puts it in another undo move stack
            MoveBundle player2Bundle = player2.movesTaken.peek();
            MoveBundle player1Bundle = player2.movesTaken.peek();
            for (Move move : player2Bundle.getAllMoves())
            {//for each move in the latest bundle
                board.updateBoard(move,player2.getPieceColour(),true,false);
            }
            player2.movesUndo.push(player2.movesTaken.pop());
            for (Move move : player1Bundle.getAllMoves())
            {//for each move in the player1 latest bundle
                board.updateBoard(move,player1.getPieceColour(),true,false);
            }
            player1.movesUndo.push(player1.movesTaken.pop());
        }
        else
            {
                System.out.println("No moves to undo");
            }
    }

    public void redoMove(Player player1, Player player2)
    {
        //TODO: remove last element from movesUndo Stack
        if(!player1.movesUndo.isEmpty())

        {//takes move off and puts it in another undo move stack
            MoveBundle player2Bundle = player2.movesUndo.peek();
            MoveBundle player1Bundle = player2.movesUndo.peek();
            for (Move move : player2Bundle.getAllMoves())
            {//for each move in the latest bundle
                board.updateBoard(move,player2.getPieceColour(),false,true);
            }
            player2.movesTaken.push(player2.movesUndo.pop());
            for (Move move : player1Bundle.getAllMoves())
            {//for each move in the player1 latest bundle
                board.updateBoard(move,player1.getPieceColour(),false,true);
            }
            player1.movesTaken.push(player1.movesUndo.pop());
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

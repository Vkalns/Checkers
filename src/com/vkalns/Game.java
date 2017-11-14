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
        AI computer1 = new AI("Whites","w");
        AI computer2 = new AI("Blacks","b");
        while(board.checkPieceCount(computer1.getPieceColour())>0&&(board.checkPieceCount(computer2.getPieceColour())>0))//Keep playing while you have pieces on board
        {
            doComputerMove(computer1);
            doComputerMove(computer2);
        }

    }

    public void doHumanMove(Player player1,Player player2)
    {
        //checks if capture needs to be done
        if(!board.checkForCapture(player1.getPieceColour()).isEmpty())
        {
            System.out.println(player1.getName()+" you must capture a piece. Please enter your next move starting and ending coordinates separated by comma");
            System.out.println("If you want to undo your and opponents last move please enter \"undo\"");
            System.out.println("If you want to redo your last undo please type \"redo\"");
            String [] coordinates = new String[]{};
            try
            {
                input = scanner.nextLine();
                if (input.trim().equalsIgnoreCase("undo"))
                {
                    undoMove(player1,player2);
                    doHumanMove(player1,player2);
                }
                else if (input.trim().equalsIgnoreCase("redo"))
                {
                    redoMove(player1,player2);
                    doHumanMove(player1,player2);
                }

                else
                    {
                        coordinates = prompter.askForMove(input.trim(),player1);//ask for move coordinates and validate them
                    }


            }
            catch (Exception e)
            {
                //System.out.println("Something went wrong with your input");
                //System.out.println("Error message: "+e.getMessage());
                System.out.println(e.getMessage());
                doHumanMove(player1,player2);
            }

            Move move = new Move(coordinates,board);
            MoveBundle moveBundle= new MoveBundle(move);
            player1.movesTaken.push(moveBundle);
            if (!board.isCaptureMove(move.getStartingPosNummeric(),player1.getPieceColour()))//check if it's a capture move
            {
                move=null;
                player1.movesTaken.pop();
                doHumanMove(player1,player2);
            }
            //adding positions for any pieces which got captured
            move.addCapturedPiecePositions(move.getStartingPosNummeric(),move.getTargetPosNummeric(),player1.getPieceColour());
            //then update board
            board.updateBoard(move,player1.getPieceColour(),false,false);
            doAnotherCapture(player1.movesTaken.peek(),board,player1,player2);
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
                   //draws an updated position of the board when basic move is made
                board.updateBoard(move,player1.getPieceColour(),false,false);

            }
            displayMovesHistory(player1);

        }
    }

    public void doAnotherCapture(MoveBundle bundle, Board board,Player player1, Player player2 )
    {
        Move lastMove = bundle.getAllMoves().get(bundle.getAllMoves().size()-1);

        if (board.checkRightCapture(lastMove.getTargetPosNummeric()[0],lastMove.getTargetPosNummeric()[1],player1.getPieceColour())
                || board.checkLeftCapture(lastMove.getTargetPosNummeric()[0],lastMove.getTargetPosNummeric()[1],player1.getPieceColour()))
        {
            System.out.println(player1.getName()+" you can capture another piece");
            System.out.println("Please enter your next move starting and ending coordinates separated by comma");
            String [] coordinates = prompter.askForMove(scanner.nextLine(),player1);
            if((lastMove.getTargetPos().equalsIgnoreCase(coordinates[0]))&&
                prompter.isCoordinatesValid(lastMove.changeToNumbers(coordinates[0]),lastMove.changeToNumbers(coordinates[1]),player1.getPieceColour()))
            {
                Move move = new Move(coordinates,board);
                bundle.addMove(move);
                move.addCapturedPiecePositions(move.getStartingPosNummeric(),move.getTargetPosNummeric(),player1.getPieceColour());
                board.updateBoard(move,player1.getPieceColour(),false,false);
                doAnotherCapture(bundle,board,player1,player2);
            }
            else
                {
                    System.out.println("Wrong coordinates");
                    doAnotherCapture(bundle,board,player1,player2);
                }
        }

    }

    public void doComputerMove(AI computer)
    {
        computer.setAllPossibleMoves(getAIMoves(computer,board));
        //computer finds all the possible moves(if it finds a capture it stops looking for other moves
        System.out.println(computer.getName()+ " choosing between "+computer.getAllPossibleMoves().size()+" figures which can move");
        Random random = new Random();
        Move computerMove = computer.getAllPossibleMoves().get(random.nextInt(computer.getAllPossibleMoves().size()));
        computerMove.addCapturedPiecePositions(computerMove.getStartingPosNummeric(),computerMove.getTargetPosNummeric(),computer.getPieceColour());
        //here we push a move to moves taken stack by choosing a random move from ArrayList
        computer.movesTaken.push(new MoveBundle(computerMove));
        //and then we update the screen
        try
        {
            Thread.sleep(500);
            System.out.println(computer.getName()+" moving a piece from: " + computerMove.changeToLetters(computerMove.getStartingPosNummeric()) +
                    " to " + computerMove.changeToLetters(computerMove.getTargetPosNummeric()));
            Thread.sleep(500);
            board.updateBoard(computerMove,computer.getPieceColour(),false,false);
            Thread.sleep(500);
            displayMovesHistory(computer);
            Thread.sleep(1000);
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
                                board.board[y][x].equals("W"))//if Ai plays with blacks or with white KING
                                && ((board.checkRightUpwards(y + 1, x - 1))//this function checks yx coordinates are in range for captures that's why its edited
                                && board.board[y - 1][x + 1].equalsIgnoreCase("*")))
                        {
                            int[] startingCoordinates = {y, x};//setting the move coordinates accordingly
                            int[] endCoordinates = {y - 1, x + 1};
                            AiMoves.add(new Move(startingCoordinates, endCoordinates, board));
                        }
                        else if ((computer.getPieceColour().equalsIgnoreCase("b")||
                                board.board[y][x].equals("W"))
                                //if Ai plays with blacks or white KING
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
                int y = board.checkForCapture(computer.getPieceColour()).get(1)-1;
                int x = board.checkForCapture(computer.getPieceColour()).get(0)-1;
                //taking first coordinates from the list of pieces which can capture
                int[] startingCoordinates = {y,x};

                if(board.checkRightCapture(y,x,board.board[y][x]))//if capture is on right side
                {
                    if ((computer.getPieceColour().equalsIgnoreCase("w")||
                            board.board[y][x].equals("B"))//if Ai plays with whites or black King
                            && ((board.checkRightDownwards(y-1, x-1))//this function checks yx coordinates are in range for captures that's why its edited
                            && board.board[y + 2][x + 2].equalsIgnoreCase("*")))
                    {
                        int[] endCoordinates = {y + 2, x + 2};
                        AiMoves.add(new Move(startingCoordinates, endCoordinates, board));
                    }
                    else if ((computer.getPieceColour().equalsIgnoreCase("b")||
                            board.board[y][x].equals("W"))//if Ai plays with blacks or white KING
                            && ((board.checkRightUpwards(y + 1, x - 1))//this function checks yx coordinates are in range for captures that's why its edited
                            && (board.board[y - 2][x + 2].equalsIgnoreCase("*"))))
                    {
                        int[] endCoordinates = {y - 2, x + 2};
                        AiMoves.add(new Move(startingCoordinates, endCoordinates, board));
                    }
                }
                else if (board.checkLeftCapture(y,x,board.board[y][x]))//if capture is on left side
                {
                    if ((computer.getPieceColour().equalsIgnoreCase("w")||
                            board.board[y][x].equals("B"))//if Ai plays with whites or black KING
                            && ((board.checkLeftDownwards(y-1, x+1))//this function checks yx coordinates are in range for captures that's why its edited
                            && board.board[y + 2][x - 2].equals("*")))
                    {
                        int[] endCoordinates = {y + 2, x - 2};
                        AiMoves.add(new Move(startingCoordinates, endCoordinates, board));
                    }
                    else if ((computer.getPieceColour().equalsIgnoreCase("b")||
                            board.board[y][x].equals("W"))
                            //if Ai plays with blacks
                            && ((board.checkLeftUpwards(y + 1, x + 1))//this function checks yx coordinates are in range for captures that's why its edited
                            && board.board[y - 2][x - 2].equals("*")))
                    {
                        int[] endCoordinates = {y - 2, x - 2};
                        AiMoves.add(new Move(startingCoordinates, endCoordinates, board));
                    }
                }
            }
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
                    {//if the move has multiple captures
                        //System.out.print(count+": "+firstMove.changeToLetters(firstMove.getStartingPosNummeric())+ " : ");
                        System.out.print(count+": ");
                        for (Move move : moveBundle.getAllMoves())
                        {
                            System.out.print(move.changeToLetters(move.getStartingPosNummeric())
                                    +" : "+move.changeToLetters(move.getTargetPosNummeric())+ " ");
                        }
                    }
                count++;
            }
        }
        else
            {
                int count = 1;
                System.out.println("List of moves made by "+player.getName()+ ":");
                for (MoveBundle moveBundle : player.movesTaken)
                {
                    System.out.print(count);
                    for (Move eachMove: moveBundle.getAllMoves())
                    {
                        System.out.print(": "+ eachMove.getStartingPos()+ " to "+eachMove.getTargetPos());
                    }
                    System.out.println();

                    count++;
                }
            }
    }

    public void undoMove(Player player1, Player player2)
    {

        if(!player1.movesTaken.isEmpty())
        {//takes move off and puts it in another undo move stack
            MoveBundle player2Bundle = player2.movesTaken.peek();
            MoveBundle player1Bundle = player1.movesTaken.peek();

            ArrayList<Move>p2Moves = player2Bundle.getAllMoves();
            for (int index=p2Moves.size()-1;index>=0;index--)
            {//for each move in the latest bundle
                Move move = p2Moves.get(index);
                board.board=move.copyBoard(move.startOfTurnBoard);
            }
            System.out.println("Undoing "+player2.getName()+"'s last move");
            board.drawBoard();
            player2.movesUndo.push(player2.movesTaken.pop());

            ArrayList<Move>p1Moves = player1Bundle.getAllMoves();
            for (int index=p1Moves.size()-1;index>=0;index--)
            {//for each move in the latest bundle
                Move move = p1Moves.get(index);
                board.board=move.copyBoard(move.startOfTurnBoard);
            }

            System.out.println("Undoing "+player1.getName()+"'s last move");
            board.drawBoard();
            player1.movesUndo.push(player1.movesTaken.pop());

        }
        else
            {
                System.out.println("No moves to undo");
            }
    }

    public void redoMove(Player player1, Player player2)
    {
        if(!player1.movesUndo.isEmpty())

        {//takes move off and puts it in another undo move stack
            MoveBundle player2UndoBundle = player2.movesUndo.peek();
            MoveBundle player1UndoBundle = player1.movesUndo.peek();
            ArrayList<Move>p2UndoMoves = player2UndoBundle.getAllMoves();
            System.out.println("Redo-ing "+player2.getName()+"'s last undo move");
            for (int index=0;index<=p2UndoMoves.size()-1;index++)
            {//for each move in the latest bundle
                Move move = p2UndoMoves.get(index);
                board.updateBoard(move,player2.getPieceColour(),false,false);
            }

            //board.drawBoard();
            player2.movesTaken.push(player2.movesUndo.pop());

            ArrayList<Move>p1UndoMoves = player1UndoBundle.getAllMoves();
            System.out.println("Redo-ing "+player1.getName()+"'s last undo move");
            for (int index=0;index<=p1UndoMoves.size()-1;index++)
            {//for each move in the latest bundle
                Move move = p1UndoMoves.get(index);
                board.updateBoard(move,player1.getPieceColour(),false,false);
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

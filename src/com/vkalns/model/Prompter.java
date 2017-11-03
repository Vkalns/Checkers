package com.vkalns.model;

import com.vkalns.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Prompter
{
    String playersInput="";
    String[] coordinates;
    int[] startingPos;
    int[] targetPos;
    Board board;
    Player player;
//    String colour1 = "w";
//    String colour2 = "b";
    Scanner scanner = new Scanner(System.in);

    public Prompter()
    {}

    public Prompter(Board board)
    {
        this.board = board;
    }

    public void choseGameType()//method to choose what game type to start
    {
        System.out.println("Please choose your game type by entering number of the following choices");
        System.out.println("1: Player against player");
        System.out.println("2: Player against Ai");
        System.out.println("3: Watch how computer plays against himself");

        //asking what game type user want to play
        try
        {
            int gameChoice = scanner.nextInt();
            if (gameChoice==1)
            {
                System.out.println("Player 1 will play with whites and player 2 will play with blacks");
                Game checkers = new Game();
                System.out.println("Player 1 please enter your name: ");
                scanner.nextLine();
                String playerOne = scanner.nextLine();
                System.out.println(playerOne+ " you are playing with white pieces");
                System.out.println("Player 2 please enter your name: ");
//                scanner.nextLine();
                String playerTwo = scanner.nextLine();
                System.out.println(playerTwo+ " you are playing with black pieces");
                checkers.startVsPlayer(playerOne,playerTwo);

            }
            else if (gameChoice ==2)
            {
                Game checkers = new Game();
                System.out.println("Please enter your name: ");
                scanner.nextLine();
                String name = scanner.nextLine();
                System.out.println(name+ " you are playing with white pieces");
                checkers.startVsAi(name);
            }
            else
            {
                System.out.println("Sorry this option is not available yet");
                choseGameType();
            }
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong. Please enter number 1,2 or 3");
            e.printStackTrace();


        }

    }

    public String[] askForMove(String playersInput,Player player)
    {//asks for players input and returns string array with two elements
        // [0] is starting coordinates [1] is final coordinates

            if (playersInput.trim().isEmpty() || playersInput.indexOf(',')==-1 || playersInput.indexOf(',')!=2)
            {
                System.out.println("Not valid data was entered");
                System.out.println("Please enter your next move starting and ending coordinates separated by comma");
                askForMove(playersInput = scanner.nextLine(),player);
            }
            coordinates = playersInput.split(",");
            if (coordinates.length==0 || coordinates[0].indexOf(" ")!=-1 || coordinates[1].indexOf(" ")!=-1)
            {
                System.out.println("Not valid data was entered");
                System.out.println("Please enter your next move starting and ending coordinates separated by comma");
//                coordinates[0]="";
//                coordinates[1]="";
                askForMove(scanner.nextLine(),player);
            }
            if(!isCoordinatesValid(coordinates,player.getPieceColour()))
            {
                System.out.println("Please enter your next move starting and ending coordinates separated by comma");
                askForMove(scanner.nextLine(),player);
            }



        return coordinates;
    }

    public String[] askForCaptureMove(ArrayList<Integer> pieceCoordinates,Player player)
    {
        String [] moveCoordinates;
        if(pieceCoordinates.size()<3)//only one piece can capture
        {

            String []startingCoordinates=changeToLetters(pieceCoordinates);
            System.out.println(Arrays.toString(startingCoordinates));
            //getting string representation of point on board which has piece which needs to do capture move

            System.out.println(player.getName()+" you must capture opponents piece in "+startingCoordinates[0]+startingCoordinates[1]);
            System.out.println("Please enter your move's ending coordinate");
            String captureEndCoordinates = scanner.nextLine().toUpperCase();
            if (captureEndCoordinates.trim().isEmpty())
            {
                System.out.println("Not valid data was entered");
                System.out.println("Please enter your next move starting and ending coordinates separated by comma");
                askForCaptureMove(board.checkForCapture(player.getPieceColour()),player);
            }
            String startingcoord = startingCoordinates[0].concat(startingCoordinates[1]);
            System.out.println(startingcoord);
            System.out.println(captureEndCoordinates);
            moveCoordinates =  new String[]{startingcoord,captureEndCoordinates};
            System.out.println(Arrays.toString(moveCoordinates));
            if(!isCoordinatesValid(moveCoordinates,player.getPieceColour()))
            {
                System.out.println("Wrong coordinate!");
                askForCaptureMove(board.checkForCapture(player.getPieceColour()),player);
            }
            else if(pieceCoordinates.size()>3)
            {
                //int[]manyStartingCoordinatesInt= new int[2];
                String[]manyStartingCoordinates=changeToLetters(pieceCoordinates);
                System.out.println(player.getName()+" you must capture opponents piece by either ");
                for (int i=0;i<manyStartingCoordinates.length-1;i=i+2)
                {
                    System.out.print(manyStartingCoordinates[i]+manyStartingCoordinates[i+1]+" or ");
                }
                askForMove(playersInput,player);

                moveCoordinates = new String[]{startingcoord,captureEndCoordinates};
                //TODO: if there is more then one capturing situation
            }

            return moveCoordinates;

        }

        return null;

    }

    private int []changeToNumbers(String coordinates)
    {
        String toUpper = coordinates.toUpperCase();

        int []horizontalInNr={0,0};
        Character h = toUpper.charAt(0);
        switch(h) {
            case 'A' :
                horizontalInNr[0] = 1;
                break;
            case 'B' :
                horizontalInNr[0] = 2;
                break;
            case 'C' :
                horizontalInNr[0] = 3;
                break;
            case 'D' :
                horizontalInNr[0] = 4;
                break;
            case 'E' :
                horizontalInNr[0] = 5;
                break;
            case 'F' :
                horizontalInNr[0] = 6;
                break;
            case 'G' :
                horizontalInNr[0] = 7;
                break;
            case 'H' :
                horizontalInNr[0] = 8;
                break;
            default :
                System.out.println("Invalid coordinates");
                horizontalInNr[0]=0;
        }
        horizontalInNr[1] = Character.getNumericValue(coordinates.charAt(1));
        return horizontalInNr;
    }

    public String []changeToLetters(ArrayList<Integer> coordinates)
    {
        //String coordiantes = coordinates.toUpperCase();

        String []horizontalInLetter={"",""};
        for (int i=0;i<coordinates.size()-1;i=i+2)
        {
            switch(coordinates.get(i)) {
                case 1:
                    horizontalInLetter[i] = "A";
                    break;
                case 2 :
                    horizontalInLetter[i] = "B";
                    break;
                case 3 :
                    horizontalInLetter[i] = "C";
                    break;
                case 4 :
                    horizontalInLetter[i] = "D";
                    break;
                case 5 :
                    horizontalInLetter[i] = "E";
                    break;
                case 6 :
                    horizontalInLetter[i] = "F";
                    break;
                case 7 :
                    horizontalInLetter[i] = "G";
                    break;
                case 8 :
                    horizontalInLetter[i] = "H";
                    break;
                default :
                    System.out.println("Invalid coordinates");
                    horizontalInLetter[i]="0";
            }
            horizontalInLetter[i+1] = Integer.toString(coordinates.get(i+1));
        }
        return horizontalInLetter;
    }

    public boolean isCoordinatesValid(String []coordinates,String playerColour)
    {//this checks if coordinates are in range
        boolean isValid=true;
        int[] startingPos = changeToNumbers(coordinates[0]);
        int[] endPos = changeToNumbers(coordinates[1]);
        String opponentsColour="";
        if(playerColour=="w"){opponentsColour="b";}
        if(playerColour=="b"){opponentsColour="w";}
        if(startingPos[0]==0 ||(startingPos[1]>8 || startingPos[1]<1))
        {
            System.out.println("Starting position is invalid");
            isValid =false;
        }
        if(endPos[0]==0 ||(endPos[1]>8 || endPos[1]<1))
        {
            System.out.println("End position is invalid");
            isValid =false;
        }
        if(board.board[endPos[1]-1][endPos[0]-1]==" " ||
                board.board[endPos[1]-1][endPos[0]-1].equalsIgnoreCase(playerColour)||
                board.board[endPos[1]-1][endPos[0]-1].equalsIgnoreCase(opponentsColour))
        {
            System.out.println("You can't move there");
            isValid =false;
        }

        if(!board.board[startingPos[1]-1][startingPos[0]-1].equalsIgnoreCase(playerColour))
        {
            System.out.println("You don't have a piece there");
            isValid =false;
        }
        return isValid;
    }

}

package com.vkalns.model;

import com.vkalns.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Prompter
{
    String[] coordinates;
    Board board;

    Scanner scanner = new Scanner(System.in);

    public Prompter()
    {}

    public Prompter(Board board)
    {
        this.board = board;
    }

    public void choseGameType()//method to choose what game type to start
    {
        System.out.println("Please choose your game type by entering number of the following choices or leave application entering 4");
        System.out.println("1: Player against player");
        System.out.println("2: Player against Ai");
        System.out.println("3: Watch how computer plays against himself");
        System.out.println("4: Exit");

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
            else if(gameChoice==3)
            {
                Game checkers = new Game();
                checkers.startAiVsAi();
            }
            else if (gameChoice==4)
            {
                System.out.println("Thanks for playing. Goodbye!");
                System.exit(0);
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
            if(!isCoordinatesValid(changeToNumbers(coordinates[0]),changeToNumbers(coordinates[1]),player.getPieceColour()))
            {
                System.out.println("Please enter your next move starting and ending coordinates separated by comma");
                askForMove(scanner.nextLine(),player);
            }
        return coordinates;
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
        horizontalInNr[0]--;
        horizontalInNr[1] = Character.getNumericValue(coordinates.charAt(1))-1;
        return horizontalInNr;
    }



    public boolean isCoordinatesValid(int []startingCoordinates,int[]endCordinates,String playerColour)
    {//this checks if coordinates are in range
        boolean isValid=true;
        //int[] startingCoordinates = changeToNumbers(coordinates[0]);
        //int[] endPos = changeToNumbers(coordinates[1]);
        String opponentsColour="";
        if(playerColour=="w"){opponentsColour="b";}
        if(playerColour=="b"){opponentsColour="w";}
        if(startingCoordinates[0]==-1 ||(startingCoordinates[1]>7 || startingCoordinates[1]<0))
        {
            System.out.println("Starting position is invalid");
            isValid =false;
        }
        if(endCordinates[0]==-1 ||(endCordinates[1]>7 || endCordinates[1]<0))
        {
            System.out.println("End position is invalid");
            isValid =false;
        }
        if(board.board[endCordinates[1]][endCordinates[0]]==" " ||
                board.board[endCordinates[1]][endCordinates[0]].equalsIgnoreCase(playerColour)||
                board.board[endCordinates[1 ]][endCordinates[0]].equalsIgnoreCase(opponentsColour) ||
                endCordinates[0]==startingCoordinates[0] || endCordinates[1]==startingCoordinates[1])
        {
            System.out.println("You can't move there");
            isValid =false;
        }

        if(!board.board[startingCoordinates[1]][startingCoordinates[0]].equalsIgnoreCase(playerColour))
        {
            System.out.println("You don't have a piece there");
            isValid =false;
        }
        if((!board.checkRightCapture(startingCoordinates[1],startingCoordinates[0],playerColour)&&
                !board.checkLeftCapture(startingCoordinates[1],startingCoordinates[0],playerColour))&&
                ((startingCoordinates[1]>=endCordinates[1]+2 || startingCoordinates[1]<=endCordinates[1]-2)))
        {
            System.out.println("You can't jump unless you capture");
            isValid=false;
        }
        return isValid;
    }

}

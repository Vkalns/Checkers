package com.vkalns.model;

import com.vkalns.Game;

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
    String colour1 = "w";
    String colour2 = "b";
    Scanner scanner = new Scanner(System.in);

//    public Player getPlayer() {
//        return player;
//    }
//
//    public void setPlayer(String colour1) {
//        this.player = player;
//    }


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
                System.out.println("Player 1 will play with whites and player two will play with blacks");
                //TODO if I have time implement a nice Choose name for players and choose colour
                Game checkers = new Game();
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


        }

    }

    public String[] askForMove(String playersInput)
    {//asks for players input and returns string array with two elements
        // [0] is starting coordinates [1] is final coordinates

            if (playersInput.trim().isEmpty() || playersInput.indexOf(',')==-1 || playersInput.indexOf(',')!=2)
            {
                System.out.println("Not valid data was entered");
                System.out.println("Please enter your next move starting and ending coordinates separated by comma");
                askForMove(playersInput = scanner.nextLine());
            }
            coordinates = playersInput.split(",");
            if (coordinates.length==0 || coordinates[0].indexOf(" ")!=-1 || coordinates[1].indexOf(" ")!=-1)
            {
                System.out.println("Not valid data was entered");
                System.out.println("Please enter your next move starting and ending coordinates separated by comma");
//                coordinates[0]="";
//                coordinates[1]="";
                askForMove(scanner.nextLine());
            }
            if(!isCoordinatesValid(coordinates))
            {
                System.out.println("Please enter your next move starting and ending coordinates separated by comma");
                askForMove(scanner.nextLine());
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
        horizontalInNr[1] = Character.getNumericValue(coordinates.charAt(1));
        return horizontalInNr;
    }

    public boolean isCoordinatesValid(String []coordinates)
    {//this checks if coordinates are in range
        boolean isValid=true;
        int[] startingPos = changeToNumbers(coordinates[0]);
        int[] endPos = changeToNumbers(coordinates[1]);
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
                board.board[endPos[1]-1][endPos[0]-1].equalsIgnoreCase(colour1)||
                board.board[endPos[1]-1][endPos[0]-1].equalsIgnoreCase(colour2))
        {
            System.out.println("You can't move there");
            isValid =false;
        }

        if(!board.board[startingPos[1]-1][startingPos[0]-1].equalsIgnoreCase(colour1))
        {
            System.out.println("You don't have a piece there");
            isValid =false;
        }
        return isValid;
    }

}

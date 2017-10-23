package com.vkalns.model;

import com.vkalns.Game;

import java.util.Scanner;

public class Prompter
{
    String playersInput="";

    Scanner scanner = new Scanner(System.in);

    public Prompter() {}

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
                System.out.println("You are playing with white pieces");
                checkers.startVsAi();
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
            String[] coordinates = playersInput.split(",");
            if (coordinates.length==0 || coordinates[0].indexOf(" ")!=-1 || coordinates[1].indexOf(" ")!=-1)
            {
                System.out.println("Not  valid data was entered");
                System.out.println("Please enter your next move starting and ending coordinates separated by comma");
                askForMove(playersInput = scanner.nextLine());
            }
        return coordinates;
    }

}

package com.vkalns.model;

import com.vkalns.Game;

import java.util.Scanner;

public class Prompter
{
    String playersInput="";

    Scanner scanner = new Scanner(System.in);

    public Prompter() {}

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

    public boolean doUndo(String input)
    {//this will check if player want's to undo a move
        boolean doUndo=false;

        if(input.indexOf(',')!=-1)
        {
            String[] coordinates = input.split(",");
            if(coordinates[0].equals("*")&&coordinates[1].equals("*"))
            {
                doUndo=true;
                System.out.println("Undoing your latest move");
            }
        }
        return doUndo;
    }
}

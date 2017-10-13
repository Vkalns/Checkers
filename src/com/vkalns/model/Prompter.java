package com.vkalns.model;

import java.util.Scanner;

public class Prompter
{
    String playersInput="";

    Scanner scanner = new Scanner(System.in);

    public Prompter() {}

    public String[] askForMove()
    {//asks for players input and returns string array with two elements
        // [0] is starting coordinates [1] is final coordinates
        System.out.println("Please enter your next move starting and ending coordinates separated by comma");

        playersInput = scanner.nextLine();
        if (playersInput.trim().isEmpty() || playersInput.indexOf(',')==-1 || playersInput.indexOf(',')!=2)
        {
            System.out.println("Not valid data was entered");
            askForMove();
        }
        String[] coordinates = playersInput.split(",");
        if (coordinates.length==0)
        {
            System.out.println("Not  valid data was entered");
            askForMove();
        }
        return coordinates;
    }
}

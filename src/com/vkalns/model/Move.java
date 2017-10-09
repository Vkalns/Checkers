package com.vkalns.model;

import java.util.Stack;

public class Move

{
    Board board = new Board();
    String[] input;
    String startingPos = "";
    String targetPos = "";
    int [] startingPosNummeric;
    int [] targetPosNummeric;

    public Move(String [] input,Board board)
    {
        this.board = board;
        this.input = input;
        startingPos = input[0];
        targetPos = input[1];
        int startingPosNummeric[] = changeToNumbers(startingPos);
        this.startingPosNummeric = startingPosNummeric;
    }

    //getters

    public String getStartingPos()
    {
        return startingPos;
    }

    public String getTargetPos()
    {
        return targetPos;
    }

    public int[] getStartingPosNummeric() {
        return startingPosNummeric;
    }

    private int []changeToNumbers(String coordinates)
    {
        String toUpper = coordinates.toUpperCase();

        int []horizontalInNr={0,0};
        Character h = toUpper.charAt(0);
        System.out.println(h);

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


    public boolean hasFigureToMove(String startingPos)//temp? piece with starting coordinates?
    {


        return true;
    }

    public void undoMove(){}

}

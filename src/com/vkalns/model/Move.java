package com.vkalns.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Move

{
    Board board = new Board();
    String[] input;
    String startingPos = "";
    String targetPos = "";
    int [] startingPosNummeric;
    int[] capturePosition;
    int [] jumpPosition = new int[2];
    int [] targetPosNummeric;
    Player player;
    boolean isValid;
    ArrayList<Integer> capturedPiecesPositions = new ArrayList<Integer>();

    public Move(String [] input,Board board)
    {
        this.board = board;
        this.input = input;
        startingPos = input[0];
        targetPos = input[1];
        int startingPosNummeric[] = changeToNumbers(startingPos);
        this.startingPosNummeric = startingPosNummeric;
        int targetPosNummeric[] = changeToNumbers(targetPos);
        this.targetPosNummeric = targetPosNummeric;

    }

    //getters

    public String getStartingPos()
    {
        return startingPos.toUpperCase();
    }

    public String getTargetPos()
    {
        return targetPos.toUpperCase();
    }

    public int[] getStartingPosNummeric() {
        return startingPosNummeric;
    }

    public int[] getTargetPosNummeric() {
        return targetPosNummeric;
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


    public void advancedMove(int[] startPosition,int[]targetPosition,String colour)
    {//TODO create similar move but for king
        if(colour.equals("w")&&targetPosition[1]==startPosition[1]+2)//if whites go ahead by two on y axis
        {
            if(targetPosition[0]>startPosition[0])//target is on the left from player point(right from our display point)
            {
                jumpPosition[0]=startPosition[0]+1;
                jumpPosition[1]=startPosition[1]+1;
                if(hasPieceToCapture(jumpPosition,colour))
                {
                    //capturedPiecesPositions.add(jumpPosition[])
                    //board.board[jumpPosition[0]][jumpPosition[1]]="*";

                    capturedPiecesPositions.add(jumpPosition[0]);
                    capturedPiecesPositions.add(jumpPosition[1]);
                    //TODO this should be in update board  method
                    //capturing piece between starting and target position

                }

            }
            else if (targetPosition[0]<startPosition[0])//target is on the right from player point(left from our display point)
            {
                jumpPosition[0]=startPosition[0]-1;
                jumpPosition[1]=startPosition[1]+1;
                if(hasPieceToCapture(jumpPosition,colour))
                {
                    //capturedPiecesPositions.add(jumpPosition[])
                    capturedPiecesPositions.add(jumpPosition[0]);
                    capturedPiecesPositions.add(jumpPosition[1]);
                    //TODO this should be in update board  method
                    //capturing piece between starting and target position

                }
            }
        }


    }

    public boolean hasPieceToCapture(int[]coordinates,String colour)
    {//this checks if in given coordinate has opponent's piece
        boolean hasPiece = false;
        if (colour.equalsIgnoreCase("w") && board.board[coordinates[0]-1][coordinates[1]-1].equalsIgnoreCase("b"))
        {
            hasPiece = true;
        }
        if (colour.equalsIgnoreCase("b") && board.board[coordinates[0]-1][coordinates[1]-1].equalsIgnoreCase("w"))
        {
            hasPiece = true;
        }
        return hasPiece;
    }

    public void CapturePiece(String coordinates)
    {//this takes off the opponents piece from the board at provided coordinate
        int[] coordinate = changeToNumbers(coordinates);
        board.board[coordinate[1]-1][coordinate[0]-1]="*";
    }

}

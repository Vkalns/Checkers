package com.vkalns.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Move

{
    Board board = new Board();
    public String [] []startOfTurnBoard = new String[8][8];
    String[] input;
    int [] startingPosNummeric;
    int [] targetPosNummeric;
    int[] capturePosition;
    String startingPos = "";
    String targetPos = "";

    int [] jumpPosition = new int[2];


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
        this.startOfTurnBoard = copyBoard(board.board);
    }

    public Move(int[]startCoordinates,int[]endCoordinates,Board board)
    {
        startingPosNummeric= startCoordinates;
        targetPosNummeric = endCoordinates;
        this.board = board;
        this.startOfTurnBoard = copyBoard(board.board);

    }

    public Move(String[] input,ArrayList<Integer> capturedPiecesPositions,Board board)
    {
        this.board = board;
        this.input = input;
        this.capturedPiecesPositions = capturedPiecesPositions;
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

    public int []changeToNumbers(String coordinates)
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

    public String changeToLetters(int[] coordinates)
    {
        //String coordiantes = coordinates.toUpperCase();

        String horizontalInLetter="";

            switch(coordinates[0]+1) {
                case 1:
                    horizontalInLetter = "A";
                    break;
                case 2 :
                    horizontalInLetter = "B";
                    break;
                case 3 :
                    horizontalInLetter = "C";
                    break;
                case 4 :
                    horizontalInLetter = "D";
                    break;
                case 5 :
                    horizontalInLetter = "E";
                    break;
                case 6 :
                    horizontalInLetter = "F";
                    break;
                case 7 :
                    horizontalInLetter = "G";
                    break;
                case 8 :
                    horizontalInLetter = "H";
                    break;
                default :
                    System.out.println("Invalid coordinates");
                    horizontalInLetter ="-1";
            }
            horizontalInLetter = horizontalInLetter.concat(Integer.toString(coordinates[1]+1));

        return horizontalInLetter;
    }

    public String[][] copyBoard(String[][] board)
    {

        String [][] copy = new String[8][8];
        for(int i=0; i<board.length; i++)
        {
            for(int j=0; j<board[i].length; j++)
            {
                copy[i][j]=board[i][j];
            }
        }
        return copy;
    }

    public void setStartOfTurnBoard() {
        this.startOfTurnBoard=copyBoard(board.board);
    }

    public void addCapturedPiecePositions(int[] startPosition, int[]targetPosition, String colour)
    {
        if(targetPosition[1]==startPosition[1]+2)//if player go ahead by two on y axis
        {
            if(targetPosition[0]>startPosition[0])//target is on the left from player point(right from our display point)
            {
                jumpPosition[0]=startPosition[0]+1;
                jumpPosition[1]=startPosition[1]+1;
                if(hasPieceToCapture(jumpPosition,colour))
                {
                    capturedPiecesPositions.add(jumpPosition[0]);
                    capturedPiecesPositions.add(jumpPosition[1]);
                }
            }
            else if (targetPosition[0]<startPosition[0])//target is on the right from player point(left from our display point)
            {
                jumpPosition[0]=startPosition[0]-1;
                jumpPosition[1]=startPosition[1]+1;
                if(hasPieceToCapture(jumpPosition,colour))
                {
                    capturedPiecesPositions.add(jumpPosition[0]);
                    capturedPiecesPositions.add(jumpPosition[1]);
                }
            }
        }

        if(targetPosition[1]==startPosition[1]-2)//if player go back by two on y axis
        {
            if(targetPosition[0]>startPosition[0])//target is on the left from player point(right from our display point)
            {
                jumpPosition[0]=startPosition[0]+1;
                jumpPosition[1]=startPosition[1]-1;
                if(hasPieceToCapture(jumpPosition,colour))
                {
                    capturedPiecesPositions.add(jumpPosition[0]);
                    capturedPiecesPositions.add(jumpPosition[1]);
                }
            }
            else if (targetPosition[0]<startPosition[0])//target is on the right from player point(left from our display point)
            {
                jumpPosition[0]=startPosition[0]-1;
                jumpPosition[1]=startPosition[1]-1;
                if(hasPieceToCapture(jumpPosition,colour))
                {
                    capturedPiecesPositions.add(jumpPosition[0]);
                    capturedPiecesPositions.add(jumpPosition[1]);
                }
            }
        }
    }

    public boolean hasPieceToCapture(int[]coordinates,String colour)
    {//this checks if in given coordinate has opponent's piece
        boolean hasPiece = false;
        //System.out.println(board.board[coordinates[0]][coordinates[1]]);
        //System.out.println(board.board[coordinates[0]-1][coordinates[1]-1]);
        if (colour.equalsIgnoreCase("w") && (board.board[coordinates[1]][coordinates[0]].equalsIgnoreCase("b")))
        {
            hasPiece = true;
        }
        if (colour.equalsIgnoreCase("b") && board.board[coordinates[1]][coordinates[0]].equalsIgnoreCase("w"))
        {
            hasPiece = true;
        }
        return hasPiece;
    }
}

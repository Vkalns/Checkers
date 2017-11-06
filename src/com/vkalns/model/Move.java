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

    private int []changeToNumbers(String coordinates)
    {
        String toUpper = coordinates.toUpperCase();

        int []horizontalInNr={0,0};
        Character h = toUpper.charAt(0);
        switch(h) {
            case 'A' :
                horizontalInNr[1] = 1;
                break;
            case 'B' :
                horizontalInNr[1] = 2;
                break;
            case 'C' :
                horizontalInNr[1] = 3;
                break;
            case 'D' :
                horizontalInNr[1] = 4;
                break;
            case 'E' :
                horizontalInNr[1] = 5;
                break;
            case 'F' :
                horizontalInNr[1] = 6;
                break;
            case 'G' :
                horizontalInNr[1] = 7;
                break;
            case 'H' :
                horizontalInNr[1] = 8;
                break;
            default :
                System.out.println("Invalid coordinates");
                horizontalInNr[1]=0;
        }
        horizontalInNr[1]--;
        horizontalInNr[0] = Character.getNumericValue(coordinates.charAt(1))-1;
        return horizontalInNr;
    }


    public void advancedMove(int[] startPosition,int[]targetPosition,String colour)
    {//TODO create similar move but for king
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
        System.out.println(board.board[coordinates[0]][coordinates[1]]);
        System.out.println(board.board[coordinates[0]-1][coordinates[1]-1]);
        if (colour.equalsIgnoreCase("w") && (board.board[coordinates[0]][coordinates[1]].equalsIgnoreCase("b")))
        {
            hasPiece = true;
        }
        if (colour.equalsIgnoreCase("b") && board.board[coordinates[0]][coordinates[1]].equalsIgnoreCase("w"))
        {
            hasPiece = true;
        }
        return hasPiece;
    }

//    public void CapturePiece(String coordinates)
//    {//this takes off the opponents piece from the board at provided coordinate
//        int[] coordinate = changeToNumbers(coordinates);
//        board.board[coordinate[1]-1][coordinate[0]-1]="*";
//    }

}

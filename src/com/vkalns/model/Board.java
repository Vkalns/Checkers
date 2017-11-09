package com.vkalns.model;

import java.util.ArrayList;

public class Board
{

    public String [][] board  = {
            {"w"," ","w"," ","w"," ","w"," "},
            {" ","w"," ","w"," ","w"," ","w"},
            {"w"," ","w"," ","w"," ","w"," "},
            {" ","*"," ","*"," ","*"," ","*"},
            {"*"," ","*"," ","*"," ","*"," "},
            {" ","b"," ","b"," ","b"," ","b"},
            {"b"," ","b"," ","b"," ","b"," "},
            {" ","b"," ","b"," ","b"," ","b"}};

//    public String [][] board  = {
//            {"*"," ","*"," ","*"," ","*"," "},
//            {" ","b"," ","*"," ","w"," ","*"},
//            {"*"," ","*"," ","*"," ","*"," "},
//            {" ","*"," ","*"," ","*"," ","*"},
//            {"*"," ","*"," ","*"," ","*"," "},
//            {" ","*"," ","*"," ","*"," ","*"},
//            {"*"," ","w"," ","*"," ","b"," "},
//            {" ","*"," ","*"," ","W"," ","*"}};
    //public ArrayList<Integer>



    public Board  (){};



    public void drawBoard()
    {
        for(int y=1;y<9;y++ )
        {
            System.out.print(y+"|");
            for(int x=1;x<9;x++ )
            {
                System.out.print(board[y-1][x-1]+"|");
                if (x==8)
                {
                    System.out.println("");
                }
            }
            if (y==8)
            {
                System.out.println("  A B C D E F G H");
            }
        }
    }

    public void updateBoard(Move move, String colour,boolean undo, boolean redo)
            //String playerColour,int[]startingCoordinates,int[]endCoordinates,boolean undo
    {
        if(undo==false)
        {//if we moving players or AI piece we swap the array elements
            String piece = board[move.getStartingPosNummeric()[0]][move.getStartingPosNummeric()[1]];
            board[move.getStartingPosNummeric()[0]][move.getStartingPosNummeric()[1]]="*";

            if (move.getTargetPosNummeric()[0]==0 && colour.equalsIgnoreCase("b") ||
                    (move.getTargetPosNummeric()[0]==7 && colour.equalsIgnoreCase("w")))
            {//if move ends at the other end of board then capitalise the piece(make it KING)
                board[move.getTargetPosNummeric()[0]][move.getTargetPosNummeric()[1]]=piece.toUpperCase();
            }
            else
                {
                    board[move.getTargetPosNummeric()[0]][move.getTargetPosNummeric()[1]]=piece;
                }

            if(!move.capturedPiecesPositions.isEmpty())//if move contains captured pieces
            {
                //System.out.println(move.capturedPiecesPositions);
                for(int i=0;i<move.capturedPiecesPositions.size()-1;i=i+2)
                {
                    board[move.capturedPiecesPositions.get(i)][move.capturedPiecesPositions.get(i+1)]="*";
                }
            }
            drawBoard();
        }
        else //if we undo the move we swap the elements from w/b back to *
            {
                String piece = board[move.getTargetPosNummeric()[0]][move.getTargetPosNummeric()[1]];
                board[move.getStartingPosNummeric()[0]][move.getStartingPosNummeric()[1]]=piece;

                board[move.getTargetPosNummeric()[0]][move.targetPosNummeric[1]]="*";

                if(!move.capturedPiecesPositions.isEmpty())//if move contains captured pieces
                {
                    for(int i=0;i<move.capturedPiecesPositions.size()-1;i=i+2)
                    {
                        board[move.capturedPiecesPositions.get(i)][move.capturedPiecesPositions.get(i+1)]="*";
                    }
                }
                drawBoard();
            }
    }

    public int checkPieceCount(String colour)
    {//checks how many elements in Board array equals piece colour
        int count = 0;
        for(int y=0;y<8;y++ )
        {
            for(int x=0;x<8;x++ )
            {
                if (board[y][x].equalsIgnoreCase(colour))
                {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean checkRightUpwards(int y, int x)
    {
        boolean inRange=true;
        if(y-2<0 || x+2>7)
        {
            inRange=false;
        }
        return inRange;
    }
    public boolean checkLeftUpwards(int y, int x)
    {
        boolean inRange=true;
        if(y-2<0 || x-2<0)
        {
            inRange=false;
        }
        return inRange;
    }
    public boolean checkRightDownwards(int y, int x)
    {
        boolean inRange=true;
        if(y+2>7 || x+2>7)
        {
            inRange=false;
        }
        return inRange;
    }
    public boolean checkLeftDownwards(int y, int x)
    {
        boolean inRange=true;
        if(y+2>7 || x-2<0)
        {
            inRange=false;
        }
        return inRange;
    }

    public boolean checkRightCapture(int y, int x,String colour)
    {
        boolean hasCapture = false;
        if(checkRightDownwards(y,x))
        {
            if(colour.equalsIgnoreCase("w")&& board[y+1][x+1].equalsIgnoreCase("b"))
            {
                if(colour.equalsIgnoreCase("w")&& board[y+2][x+2].equalsIgnoreCase("*"))
                {
                    hasCapture = true;//has valid capture
                }
            }
        }

        if(checkRightUpwards(y,x))
        {
            if(colour.equalsIgnoreCase("b")&& board[y-1][x+1].equalsIgnoreCase("w"))
            {
                if(colour.equalsIgnoreCase("b")&& board[y-2][x+2].equalsIgnoreCase("*"))
                {
                    hasCapture=true;//has valid capture
                }
            }
        }

        return hasCapture;

    }

    public boolean checkLeftCapture(int y, int x,String colour)
    {
        boolean hasCapture =false;
        if(checkLeftDownwards(y,x))//while target coordinates are in range
        {
            if(colour.equals("w")&& board[y+1][x-1].equalsIgnoreCase("b"))//if there is piece to capture
            {
                if(colour.equals("w")&& board[y+2][x-2].equalsIgnoreCase("*"))//and there is space after it
                {
                    hasCapture=true;//has valid capture
                }
            }
        }

        if(checkLeftUpwards(y,x))//while target coordinates are in range
        {
            if(colour.equals("b")&& board[y-1][x-1].equalsIgnoreCase("w"))//if there is piece to capture
            {
                if(colour.equals("b")&& board[y-2][x-2].equalsIgnoreCase("*"))//and there is space after it
                {
                    hasCapture=true;//has valid capture
                }
            }
        }

        return hasCapture;

    }


    public ArrayList<Integer> checkForCapture(String colour)
    {
        ArrayList<Integer> piecesWhichCanCapture= new ArrayList<Integer>();
//        if (colour.equalsIgnoreCase("w"))//checking whites
//        {
            for(int y=0;y<8;y++ )
            //I check for pieces on board which capture range is not outside of board
            {
                for(int x=0;x<8;x++ )
                {
                    if (board[y][x].equalsIgnoreCase(colour))
                    {
                        if(checkRightCapture(y,x,colour)||checkLeftCapture(y,x,colour))
                        {
                            piecesWhichCanCapture.add(x+1);
                            piecesWhichCanCapture.add(y+1);
                        }
                    }
                }
            }
        return piecesWhichCanCapture;

    }

    public boolean isCaptureMove(int[]startingCoordinates,String colour)
    {
        boolean isCapture=false;
        if(board[startingCoordinates[0]][startingCoordinates[1]].equalsIgnoreCase(colour))
        {
            if(checkRightCapture(startingCoordinates[0],startingCoordinates[1],colour)||
                    checkLeftCapture(startingCoordinates[0],startingCoordinates[1],colour))
            {
                isCapture=true;
            }
        }
        return isCapture;
    }


}

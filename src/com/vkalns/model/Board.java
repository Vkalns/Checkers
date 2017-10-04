package com.vkalns.model;

import java.util.ArrayList;

public class Board
{
    private ArrayList<Piece> myPieces = new ArrayList<>();
    private ArrayList<Piece> aiPieces = new ArrayList<>();



    public Board  (){};

    public Board(ArrayList<Piece> myPieces, ArrayList<Piece> aiPieces)
    {
        this.myPieces = myPieces;
        this.aiPieces = aiPieces;
    }




}

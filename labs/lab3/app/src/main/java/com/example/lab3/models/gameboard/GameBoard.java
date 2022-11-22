package com.example.lab3.models.gameboard;

import android.graphics.Canvas;

import com.example.lab3.models.Position;

public class GameBoard {
    private int size;
    private int cellSize;
    private Cell[][] cells;
    public GameBoard(int size) {
        this.size = size;
    }
    public Cell getCell(Position position) {
        return cells[position.x][position.y];
    }

}

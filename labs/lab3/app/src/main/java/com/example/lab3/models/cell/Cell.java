package com.example.lab3.models.cell;

public class Cell {
    private CellColor color;
    private CellState state = CellState.IDLE;

    public Cell(CellColor color) {
        this.color = color;
    }
    public CellColor getColor() {
        return color;
    }

    public void setColor(CellColor color) {
        this.color = color;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }


}

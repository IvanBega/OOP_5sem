package com.example.lab3.models.checker;

public class Checker {
    private CheckerState state = CheckerState.REGULAR;
    private CheckerColor color;

    public Checker(CheckerColor color) {
        this.color = color;
    }
}

package me.bega.candies.exceptions;

public class NegativeSugarAmountException extends Exception{
    public NegativeSugarAmountException() {}
    public NegativeSugarAmountException(String message) {
        super(message);
    }
}

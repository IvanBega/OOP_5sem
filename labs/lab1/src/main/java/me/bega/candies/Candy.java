package me.bega.candies;

import me.bega.candies.exceptions.NegativeSugarAmountException;
import me.bega.candies.exceptions.NegativeWeightException;

public abstract class Candy {
    private String name;
    private double weight;
    private double sugarAmount;
    private CandyType type;

    protected Candy(String name, double weight, double sugarAmount) {
        this.name = name;
        this.weight = weight;
        this.sugarAmount = sugarAmount;
    }
    public void setWeight(double weight) throws NegativeWeightException {
        if (weight <= 0) {
            throw new NegativeWeightException("Tried to set negative weight.");
        }
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public double getSugarAmount() {
        return sugarAmount;
    }

    public void setSugarAmount(double sugarAmount) throws NegativeSugarAmountException {
        if (sugarAmount < 0) {
            throw new NegativeSugarAmountException("Tried to assigned negative sugar amount.");
        }
        this.sugarAmount = sugarAmount;
    }

    public CandyType getType() {
        return type;
    }

    public void setType(CandyType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object candy) {

        if (this == candy) return true;
        if (candy == null || getClass() != candy.getClass()) return false;
        Candy otherCandy = (Candy) candy;
        return (Double.compare(otherCandy.getWeight(), weight) == 0) &&
                (Double.compare(otherCandy.getSugarAmount(), sugarAmount) == 0) &&
                otherCandy.getName().equals(name) && otherCandy.getType() == type;
    }

    @Override
    public String toString() {
        return "Candy{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", sugarAmount=" + sugarAmount +
                ", type=" + type +
                '}';
    }
}

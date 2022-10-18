package me.bega.candies;

public class BlueGum extends Candy{
    public BlueGum(String name, double weight, double sugarAmount) {
        super(name, weight, sugarAmount);
        this.setType(CandyType.GUM);
    }
}

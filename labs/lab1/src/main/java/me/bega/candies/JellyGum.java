package me.bega.candies;

public class JellyGum extends Candy{
    public JellyGum(String name, double weight, double sugarAmount) {
        super(name, weight, sugarAmount);
        this.setType(CandyType.GUM);
    }
}

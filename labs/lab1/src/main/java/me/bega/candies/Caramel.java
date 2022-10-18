package me.bega.candies;

public class Caramel extends Candy{
    public Caramel(String name, double weight, double sugarAmount) {
        super(name, weight, sugarAmount);
        this.setType(CandyType.CARAMEL);
    }
}

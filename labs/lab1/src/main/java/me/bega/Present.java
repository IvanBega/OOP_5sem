package me.bega;

import me.bega.candies.Candy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Present implements Openable{
    private String name;

    public List<Candy> getCandies() {
        return candies;
    }

    public void setCandies(List<Candy> candies) {
        this.candies = candies;
    }
    public void addCandy(Candy candy) {
        candies.add(candy);
    }

    private List<Candy> candies;

    public Present(String name) {
        candies = new ArrayList<>();
    }
    public Present(String name, List<Candy> candies) {
        this.name = name;
        this.candies = candies;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void sortCandies(Comparator<Candy> comparator) {
        candies.sort(comparator);
    }
    public double getWeight() {
        double weight = 0;
        for (Candy candy : candies) {
            weight += candy.getWeight();
        }
        return weight;
    }
    public List<Candy> getCandiesWithSugarRange(double min, double max) {
        List<Candy> result = new ArrayList<>();
        for (Candy candy : candies) {
            if (candy.getSugarAmount() >= min && candy.getSugarAmount() <= max) {
                result.add(candy);
            }
        }
        return result;
    }

    @Override
    public void open() {
        System.out.println("Opened present with " + candies.size() + " candies!");
        candies.clear();
    }
}

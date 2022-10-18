package me.bega.candies;

import me.bega.candies.exceptions.NegativeSugarAmountException;
import me.bega.candies.exceptions.NegativeWeightException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CandyTest {

    @Test
    void testCaramelCandy() {
        Caramel candy = new Caramel("Caramel", 20,5);
        Assertions.assertEquals("Caramel", candy.getName());
        Assertions.assertEquals(20,candy.getWeight());
        Assertions.assertEquals(5,candy.getSugarAmount());
        Assertions.assertEquals(CandyType.CARAMEL, candy.getType());
    }

    @Test
    void testBlueGum() {
        BlueGum gum = new BlueGum("blue gum", 7, 2);
        Assertions.assertEquals("blue gum", gum.getName());
        Assertions.assertEquals(7, gum.getWeight());
        Assertions.assertEquals(2, gum.getSugarAmount());
        Assertions.assertEquals(CandyType.GUM, gum.getType());
    }

    @Test
    void testJellyGum() {
        JellyGum gum = new JellyGum("jelllly gum", 15, 4);
        Assertions.assertEquals("jelllly gum", gum.getName());
        Assertions.assertEquals(15, gum.getWeight());
        Assertions.assertEquals(4, gum.getSugarAmount());
        Assertions.assertEquals(CandyType.GUM, gum.getType());
    }

    @Test
    void testNegativeSugarAmountException() {
        Caramel caramel = new Caramel("Caramel", 16, 3);
        Throwable exception = Assertions.assertThrows(NegativeSugarAmountException.class, () -> caramel.setSugarAmount(-5));
        assertEquals("Tried to assigned negative sugar amount." ,exception.getMessage());
    }

    @Test
    void testNegativeWeightException() {
        Caramel caramel = new Caramel("Caramel", 16, 3);
        Throwable exception = Assertions.assertThrows(NegativeWeightException.class, () -> caramel.setWeight(-5));
        assertEquals("Tried to set negative weight." ,exception.getMessage());
    }
}
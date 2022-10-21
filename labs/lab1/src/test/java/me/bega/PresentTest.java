package me.bega;

import me.bega.candies.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PresentTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private Present present = null;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        present = new Present("2023 present");

        present.addCandy(new Caramel("Caramel", 15, 4));
        present.addCandy(new JellyGum("Jelly1", 5, 2));
        present.addCandy(new BlueGum("blue", 13, 2));
    }

    @Test
    void testOpen() {
        present.open();
        assertEquals("Opened present with 3 candies!\r\n", outContent.toString());
    }

    @Test
    void testGetWeight() {
        double expectedWeight = 33;
        double actualWeight = present.getWeight();
        assertEquals(expectedWeight, actualWeight, 0.001);
    }

    @Test
    void testSortCandies() {
        present.sortCandies(new CandiesComparator());
        List<Candy> candies = present.getCandies();

        for (int i = 0; i < candies.size() - 1; i ++) {
            assertTrue(candies.get(i).getSugarAmount() <= candies.get(i+1).getSugarAmount());
        }
    }
    @Test
    void testGetCandiesWithSugarRange() {
        List<Candy> candies = present.getCandiesWithSugarRange(3, 5);
        assertEquals(1, candies.size());
        for (Candy c : candies) {
            assertTrue(c.getSugarAmount() > 3 && c.getSugarAmount() < 5);
        }
    }
    @Test
    void testPresentName() {
        assertEquals("2023 present", present.getName());
    }
}
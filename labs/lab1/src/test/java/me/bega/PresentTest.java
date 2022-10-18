package me.bega;

import me.bega.candies.BlueGum;
import me.bega.candies.Caramel;
import me.bega.candies.JellyGum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PresentTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private Present present = null;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        present = new Present("Present");

        present.addCandy(new Caramel("Caramel", 15, 4));
        present.addCandy(new JellyGum("Jelly1", 5, 2));
        present.addCandy(new BlueGum("blue", 13, 2));
    }

    @Test
    void testOpen() {
        present.open();
        assertEquals("Opened present with 3 candies!\r\n", outContent.toString());
    }
}
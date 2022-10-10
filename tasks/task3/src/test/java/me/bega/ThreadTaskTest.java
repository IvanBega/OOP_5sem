package me.bega;

/*import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;*/
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class ThreadTaskTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testThreadTaskMethods(){
        ThreadGroup threadGroup1 = new ThreadGroup("FIRST GROUP");
        ThreadGroup threadGroup2 = new ThreadGroup(threadGroup1, "SECOND GROUP");
        ThreadGroup threadGroup3 = new ThreadGroup(threadGroup2, "THIRD GROUP");

        new Thread(threadGroup1, () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "firstThread").start();


        new Thread(threadGroup2, () -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "secondThread").start();

        new Thread(threadGroup2, () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thirdThread").start();

        new Thread(threadGroup3, () -> {
            try {
                Thread.sleep(11000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "fourthThread").start();

        ThreadTask threadTask = new ThreadTask();
        threadTask.printTreadsInfo(threadGroup1);
        long endTime = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < endTime);
        Assertions.assertEquals("FIRST GROUP\n" +
                "Threads at FIRST GROUP:\n" +
                "  firstThread\n" +
                "Thread groups in FIRST GROUP:\n" +
                "  SECOND GROUP\n" +
                "  Threads at SECOND GROUP:\n" +
                "    secondThread\n" +
                "    thirdThread\n" +
                "  Thread groups in SECOND GROUP:\n" +
                "    THIRD GROUP\n" +
                "    Threads at THIRD GROUP:\n" +
                "      fourthThread\n" +
                "FIRST GROUP\n" +
                "Thread groups in FIRST GROUP:\n" +
                "  SECOND GROUP\n" +
                "  Thread groups in SECOND GROUP:\n" +
                "    THIRD GROUP\n" +
                "    Threads at THIRD GROUP:\n" +
                "      fourthThread\n" +
                "FIRST GROUP\n" +
                "Thread groups in FIRST GROUP:\n" +
                "  SECOND GROUP\n" +
                "  Thread groups in SECOND GROUP:\n" +
                "    THIRD GROUP\n" +
                "    Threads at THIRD GROUP:\n" +
                "      fourthThread\n", outContent.toString());
    }
}
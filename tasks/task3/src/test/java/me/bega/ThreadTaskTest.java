package me.bega;

import org.junit.jupiter.api.*;

import java.io.*;


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
    public void testThreadTaskMethods() throws IOException {
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
        Assertions.assertEquals("FIRST GROUP\r\n" +
                "Threads at FIRST GROUP:\r\n" +
                "  firstThread\r\n" +
                "Thread groups in FIRST GROUP:\r\n" +
                "  SECOND GROUP\r\n" +
                "  Threads at SECOND GROUP:\r\n" +
                "    secondThread\r\n" +
                "    thirdThread\r\n" +
                "  Thread groups in SECOND GROUP:\r\n" +
                "    THIRD GROUP\r\n" +
                "    Threads at THIRD GROUP:\r\n" +
                "      fourthThread\r\n" +
                "FIRST GROUP\r\n" +
                "Thread groups in FIRST GROUP:\r\n" +
                "  SECOND GROUP\r\n" +
                "  Thread groups in SECOND GROUP:\r\n" +
                "    THIRD GROUP\r\n" +
                "    Threads at THIRD GROUP:\r\n" +
                "      fourthThread\r\n" +
                "FIRST GROUP\r\n" +
                "Thread groups in FIRST GROUP:\r\n" +
                "  SECOND GROUP\r\n" +
                "  Thread groups in SECOND GROUP:\r\n" +
                "    THIRD GROUP\r\n" +
                "    Threads at THIRD GROUP:\r\n" +
                "      fourthThread\r\n", outContent.toString());
    }
}
package me.bega.client;

import me.bega.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.channels.SocketChannel;

public class ClientTest {
    private Client clientTest;
    private final Student student = new Student("Ivan", 19, "KNU");
    private SocketChannel clientSocket = Mockito.mock(SocketChannel.class);

    @BeforeEach
    public void setUp(){
        clientTest = new Client(clientSocket);
    }

    @Test
    public void testSendObject() throws IOException, ClassNotFoundException {
        clientTest.sendStudent(student);
        clientTest.getBuffer().rewind();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(clientTest.getBuffer().array()));
        Assertions.assertEquals(student, ois.readObject());
    }
}
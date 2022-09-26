package me.bega.server;

import me.bega.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import static org.mockito.Mockito.*;

public class ServerTest {
    private Server testServer = new Server();
    private final ServerSocketChannel serverSocketChannel = Mockito.mock(ServerSocketChannel.class);
    private final Student student = new Student("Ivan", 19, "KNU");
    private final SelectionKey acceptKey = Mockito.mock(SelectionKey.class);
    private final SelectionKey readKey = Mockito.mock(SelectionKey.class);

    @BeforeEach
    public void setUp() throws IOException {
        SocketChannel client = Mockito.mock(SocketChannel.class);
        when(serverSocketChannel.accept()).thenReturn(client);

        when(acceptKey.readyOps()).thenReturn(SelectionKey.OP_ACCEPT);
        when(readKey.readyOps()).thenReturn(SelectionKey.OP_READ);
    }

    @Test
    public void testReadingObject() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
        oos.writeObject(student);
        oos.close();
        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        Serializable studentReceived = testServer.readObject(buffer);
        Assertions.assertEquals(studentReceived, student);
    }

    @Test
    public void testSuccessfulResponse() throws IOException {
        SocketChannel client = Mockito.mock(SocketChannel.class);
        testServer.respond(true, client);
        verify(client).write(ByteBuffer.wrap("Received student".getBytes()));
    }

    @Test
    public void testUnsuccessfullResponse() throws IOException {
        SocketChannel client = Mockito.mock(SocketChannel.class);
        testServer.respond(false, client);
        verify(client).write(ByteBuffer.wrap("Something went wrong".getBytes()));
    }
}
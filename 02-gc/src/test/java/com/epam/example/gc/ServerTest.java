package com.epam.example.gc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;

public class ServerTest {

    private Socket socket;
    private DataOutputStream os;

    @Before
    public void init() throws IOException {

        socket = new Socket("localhost", 7890);
        os = new DataOutputStream(socket.getOutputStream());
    }
    
    @Test
    public void testServer() throws IOException {
        for (int i = 0; i < 1000000000; i++) {
            writeMessage(i);
        }
    }

    private void writeMessage(int i) throws IOException {
        int count =  i % 10;
        os.writeInt(count);

        int length = 1000 + count;
        for (int j = 0; j < count; j++) {
            os.writeInt(length);
            os.writeBytes(makeString(length));
        }
    }

    private String makeString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(i % 5 == 0 ? ' ' : 'x');
        }
        sb.append(length);
        return sb.toString();
    }

}
package com.epam.example.gc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class Server {

    private static final Set<String> cache = new HashSet<>();

    private int port;
    private ServerSocket server;
    private DataOutputStream os;
    private DataInputStream is;
    private ReentrantLock lock = new ReentrantLock();

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
    	server = new ServerSocket(port);
    	createStreams(server.accept());
    	
        while (true) {
            readMessage();
        }
    }

	private void createStreams(Socket socket) throws IOException {
		os = new DataOutputStream(socket.getOutputStream());
        is = new DataInputStream(socket.getInputStream());
	}

    private void readMessage() throws IOException {
        for (int i = 0, length = is.readInt(); i < length; i++) {
        	byte[] bytes = new byte[is.readInt()];
            is.readFully(bytes);
            startNewThread(bytes, i);
        }
    }
    
    private void startNewThread(byte[] bytes, int index) {
    	new Thread(() -> calcSentence(bytes, index)).start();
    }

    private void calcSentence(byte[] bytes, int index) {
        String str = new String(bytes).intern();
        int big = 0;
        int small = 0;
        for (String word : str.split(" ")) {
            if (word.length() <= 4) {
                cache.add(word);
                small++;
            } else {
                big++;
            }
        }
        try {
            lock.lock();
            os.writeInt(index);
            os.writeInt(big);
            os.writeInt(small);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws IOException {
        new Server(7890).start();
    }
}

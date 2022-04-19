package org.yanosik.test.task.server;

import java.io.IOException;
import java.net.ServerSocket;

public class UserRequestListener {
    private boolean serverStatus = true;
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("User request listener started on port " + port);
        while (serverStatus) {
            new RequestHandler(serverSocket.accept()).start();
        }
    }

    public void stop() throws IOException {
        serverStatus = false;
        serverSocket.close();
    }

    public static void main(String[] args) {
        System.out.println("Server started");
        UserRequestListener listener = new UserRequestListener();
        try {
            listener.start(6666);
            listener.stop();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't start a user request listener", e);
        }
    }
}

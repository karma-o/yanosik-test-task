package org.yanosik.test.task.server;

import java.io.IOException;
import java.net.ServerSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.yanosik.test.task.server.config.AppConfig;

@Component
public class UserRequestListener {
    @Autowired
    private AnnotationConfigApplicationContext context;
    private ServerSocket serverSocket;
    private boolean serverStatus = true;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("User request listener started on port " + port);
        while (serverStatus) {
            context.getBean(RequestHandler.class).setClientSocket(serverSocket.accept()).start();
        }
    }

    public void stop() throws IOException {
        serverStatus = false;
        serverSocket.close();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserRequestListener listener = context.getBean(UserRequestListener.class);
        System.out.println("Server started");
        try {
            listener.start(6666);
            listener.stop();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't start a user request listener", e);
        }
    }
}

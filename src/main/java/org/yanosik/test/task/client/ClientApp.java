package org.yanosik.test.task.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.json.JSONObject;
import org.yanosik.test.task.common.model.dto.request.UserRequestDto;

public class ClientApp {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public JSONObject sendMessage(UserRequestDto userRequestDto) throws IOException {
        System.out.println("Sending data: " + userRequestDto.toString());
        out.println(new JSONObject(userRequestDto));
        System.out.println("Message sent successfully");
        JSONObject response = new JSONObject(in.readLine());
        System.out.println("Response received: " + response);
        return response;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        System.out.println("Connection closed");
    }
}

package org.yanosik.test.task.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.yanosik.test.task.common.model.User;
import org.yanosik.test.task.common.model.dto.request.UserRequestDto;
import org.yanosik.test.task.common.service.mapper.impl.InsuranceOfferDtoMapper;
import org.yanosik.test.task.common.service.mapper.impl.UserDtoMapper;
import org.yanosik.test.task.common.service.mapper.impl.VehicleDtoMapper;
import org.yanosik.test.task.server.dao.impl.InsuranceOfferDaoImpl;
import org.yanosik.test.task.server.dao.impl.UserDaoImpl;
import org.yanosik.test.task.server.dao.impl.VehicleDaoImpl;
import org.yanosik.test.task.server.service.AuthenticationService;
import org.yanosik.test.task.server.service.impl.AuthenticationServiceImpl;
import org.yanosik.test.task.server.service.impl.InsuranceOfferServiceImpl;
import org.yanosik.test.task.server.service.impl.UserServiceImpl;
import org.yanosik.test.task.server.service.impl.VehicleServiceImpl;

@Component()
@Scope("prototype")
public class RequestHandler extends Thread {
    @Autowired
    private UserDtoMapper userDtoMapper;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private VehicleDtoMapper vehicleDtoMapper;
    private  Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public RequestHandler() {
    }

    public RequestHandler(Socket socket) {
        clientSocket = socket;
    }

    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // this while loop allows us to keep the request handler running
            // until the client closes the connection
            String inputLine;
            while ((inputLine = in.readLine()) != null) {

                System.out.println("Request handler started...");
                UserRequestDto userRequestDto = userDtoMapper.toRequestDto(
                        new JSONObject(
                                inputLine));
                User user = authenticationService.login(
                        userRequestDto.getId(),
                        userRequestDto.getPassword()
                );

                System.out.println("Mapping users vehicles to response DTOs...");
                System.out.println("Mapping vehicle response DTOs to JSON...");
                List<JSONObject> jsonVehicles = user.getVehicles()
                        .stream()
                        .map(vehicleDtoMapper::toResponseDto)
                        .map(vehicleDtoMapper::toJson)
                        .collect(Collectors.toList());

                System.out.println("Composing a single JSONObject response...");
                String responseMessage = new JSONObject().put(
                        "vehicles", new JSONArray(jsonVehicles)).toString();

                System.out.println("Sending response to client...");
                out.println(responseMessage);
            }
            close();
        } catch (IOException e) {
            throw new RuntimeException("Error while reading/writing from/to client socket", e);
        }
    }

    public RequestHandler setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
        return this;
    }

    public void close() throws IOException {
        System.out.println("Closing request handler resources...");
        in.close();
        out.close();
        clientSocket.close();
    }
}

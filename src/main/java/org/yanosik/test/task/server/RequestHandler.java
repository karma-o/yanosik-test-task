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

public class RequestHandler extends Thread {
    private static final UserDtoMapper userDtoMapper;
    private static final AuthenticationService authenticationService;
    private static final VehicleDtoMapper vehicleDtoMapper;
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    // in the production we would use dependency injection for all of our services and DAOs
    // e.g. Spring Framework, Guice, etc.
    // but for simplicity we will use static initialization block.
    // although it will create a lot of unnecessary objects for each Thread.
    static {
        userDtoMapper = new UserDtoMapper();
        authenticationService = new AuthenticationServiceImpl(
                new UserServiceImpl(
                        new UserDaoImpl(
                                new VehicleServiceImpl(
                                        new VehicleDaoImpl(
                                                new InsuranceOfferServiceImpl(
                                                        new InsuranceOfferDaoImpl()
                                                )
                                        )
                                )
                        )
                )
        );
        vehicleDtoMapper = new VehicleDtoMapper(new InsuranceOfferDtoMapper());
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

    public void close() throws IOException {
        System.out.println("Closing request handler resources...");
        in.close();
        out.close();
        clientSocket.close();
    }
}

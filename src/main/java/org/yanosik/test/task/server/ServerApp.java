package org.yanosik.test.task.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
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

public class ServerApp {
    private final UserDtoMapper userDtoMapper;
    private final AuthenticationService authenticationService;
    private final VehicleDtoMapper vehicleDtoMapper;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ServerApp(
            UserDtoMapper userDtoMapper,
            AuthenticationService authenticationService, VehicleDtoMapper vehicleDtoMapper) {
        this.userDtoMapper = userDtoMapper;
        this.authenticationService = authenticationService;
        this.vehicleDtoMapper = vehicleDtoMapper;
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        System.out.println("Server started...");
        UserRequestDto userRequestDto = userDtoMapper.toRequestDto(
                new JSONObject(
                        in.readLine()));
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

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) {
        // in the production we would use dependency injection for all of our services and DAOs
        // e.g. Spring Framework, Guice, etc.
        UserDaoImpl userDao = new UserDaoImpl(
                new VehicleServiceImpl(
                        new VehicleDaoImpl(
                                new InsuranceOfferServiceImpl(
                                        new InsuranceOfferDaoImpl()
                                )
                        )
                )
        );
        UserServiceImpl userService = new UserServiceImpl(userDao);
        ServerApp server = new ServerApp(
                new UserDtoMapper(),
                new AuthenticationServiceImpl(userService),
                new VehicleDtoMapper(
                        new InsuranceOfferDtoMapper()
                )
        );
        try {
            server.start(6666);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't start a server socket", e);
        }
    }
}

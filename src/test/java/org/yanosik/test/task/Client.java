package org.yanosik.test.task;

import java.io.IOException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yanosik.test.task.client.ClientApp;
import org.yanosik.test.task.common.model.dto.request.UserRequestDto;
import org.yanosik.test.task.common.service.mapper.impl.InsuranceOfferDtoMapper;
import org.yanosik.test.task.common.service.mapper.impl.VehicleDtoMapper;

/**
 * This test class is acting like a separate client application.
 * I tried to keep a server and a client as a single application,
 * and at the same time separate them.
 *
 */
public class Client {
    public static final Long MOCK_ID = 1L;
    public static final String MOCK_PASSWORD = "1234";

    public static final String SERVER_IP = "127.0.0.1";
    public static final int SERVER_PORT = 6666;

    private static VehicleDtoMapper vehicleDtoMapper;
    private ClientApp client;

    @BeforeAll
    static void beforeAll() {
        vehicleDtoMapper = new VehicleDtoMapper(
                new InsuranceOfferDtoMapper());
    }

    @BeforeEach
    void beforeEach() throws IOException {
        client = new ClientApp();
        System.out.println("Client started");
        client.startConnection(SERVER_IP, SERVER_PORT);
        System.out.println("Established connection with: " + SERVER_IP + ":" + SERVER_PORT);
    }

    @Test
    public void sendMessageReceiveResponse() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setId(MOCK_ID);
        userRequestDto.setPassword(MOCK_PASSWORD);
        try {
            JSONObject response = client.sendMessage(userRequestDto);
            String readableResponse = vehicleDtoMapper.responseToReadableString(response);

            System.out.println("Displaying response...");
            System.out.println(readableResponse);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't connect to a server, ip = "
                    + SERVER_IP + ":" + SERVER_PORT, e);
        }
    }

    @AfterEach
    void tearDown() {
        try {
            client.stopConnection();
        } catch (IOException e) {
            throw new RuntimeException("Couldn't close a  client connection, ip = "
                    + SERVER_IP + ":" + SERVER_PORT, e);
        }
    }
}

package iti.jets.amira;

import org.junit.jupiter.api.Test;

import iti.jets.amira.models.UserModel;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {
    private String restUri = "http://localhost:9090/rest-jersey/webapi/v5/users";
    private Client client = ClientBuilder.newClient();
    public static final int HTTP_OK = 200;

    @Test
    public void TestGetAllUsers() {
        Response response = client.target(restUri)
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(HTTP_OK, response.getStatus());
    }

    @Test
    public void TestCreateUser() {
        UserModel user = new UserModel("amira", "123");
        Response response = client.target(restUri)
                .request()
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));
        assertEquals(HTTP_OK, response.getStatus());
        System.out.println(response.getEntity());
    }

    @Test
    public void TestGetUserById() {
        Response response = client.target(restUri)
                .path("{id}")
                .resolveTemplate("id", 100)
                .request(MediaType.APPLICATION_XML)
                .get();
        assertEquals(HTTP_OK, response.getStatus());
    }

}

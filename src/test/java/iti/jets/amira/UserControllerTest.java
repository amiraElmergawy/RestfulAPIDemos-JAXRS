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
    String url = "http://localhost:9090/rest-jersey/webapi/v5/users";

    @Test
    public void TestGetAllUsers() {
        Client client = ClientBuilder.newClient();
        Response response = client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .get();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void TestGetUserById() {
        Client client = ClientBuilder.newClient();
        Response response = client.target(url)
                .path("{id}")
                .resolveTemplate("id", 100)
                .request(MediaType.APPLICATION_XML)
                .get();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void TestCreateUser() {
        Client client = ClientBuilder.newClient();
        UserModel user = new UserModel("amira", "123");
        Response response = client.target(url)
                .request()
                .post(Entity.entity(user, MediaType.APPLICATION_JSON));
        assertEquals(200, response.getStatus());
        System.out.println(response.getEntity());
    }


}

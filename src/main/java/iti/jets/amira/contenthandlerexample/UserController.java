package iti.jets.amira.contenthandlerexample;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import iti.jets.amira.models.UserModel;

/**
 * Root usersResource (exposed at "v3/users" path)
 */
@Path("v3/users")
public class UserController {

    private static Map<Integer, UserModel> usersMap = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger(100); // users ids will begin with 100

    /**
     * Method handling HTTP GET requests. The returned array of objects will be sent
     * to the client as "JSON" media type.
     *
     * @return Map that will be returned as a JSON response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN) // uses custom body writer
    public Map<Integer, UserModel> getAllUsers() {
        return usersMap;
    }

    /**
     * Method handling HTTP GET request. The returned object will be sent according
     * to the entered id in the url
     * to the client as "XML" media type.
     *
     * @return Object that will be returned as XML response.
     */
    @GET
    @Path("{id://d+}")
    @Produces(MediaType.APPLICATION_XML)
    public UserModel getUserById(@PathParam("id") int userId) {
        return usersMap.get(userId);
    }

    /**
     * Method handling HTTP POST request. The object will be added according
     * to the entered username and password in the form to our map.
     *
     * @return success message
     */
    @POST
    @Produces(MediaType.TEXT_HTML)
    public String addUserByFormParams(@FormParam("username") String username,
            @FormParam("password") String password) {
        usersMap.put(idCounter.getAndIncrement(), new UserModel(username, password));
        return "added succefully";
    }


    /**
     * Method handling HTTP POST request. The users list will be added according
     * to the entered username and password in the request body to our map.
     *
     * @return success message
     */
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.TEXT_PLAIN) // uses custom body reader
    public String addUser(List<UserModel> users) {
        for (UserModel user : users) {
            usersMap.put(idCounter.getAndIncrement(), user);
        }
        return "User added successfully";
    }


    //we can add another post method but using different consumers and producers
    @POST
    @Produces(MediaType.TEXT_PLAIN) // uses custom body writer
    @Consumes(MediaType.APPLICATION_JSON)
    public Map<Integer,UserModel> anotherPostMethod(List<UserModel> users) {
        return usersMap;
    }
}

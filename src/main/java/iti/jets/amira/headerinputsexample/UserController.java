package iti.jets.amira.headerinputsexample;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Root usersResource (exposed at "v2/users" path)
 */
@Path("v2/users")
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
    @Produces(MediaType.APPLICATION_JSON) // by default produces JSON if you provide JSON in your project dependencies
    public Map<Integer, UserModel> getAllUsers(){
        return usersMap;
    }


    //PathParam test

    /**
     * Method handling HTTP GET request. The returned object will be sent according to the entered id in the url
     * to the client as "XML" media type.
     *
     * @return Object that will be returned as XML response.
     */
    @GET
    @Path("{id://d+}") // regex matches digits only
    @Produces(MediaType.APPLICATION_XML)
    public UserModel getUserById(@PathParam("id") int userId){
        return usersMap.get(userId);
    }


    /**
     * Method handling HTTP GET request. The returned object will be sent according to the entered username and password in the url
     * to the client as "JSON" media type.
     *
     * @return Object that will be returned as JSON response, or null is the entered data is not correct
     */
    @GET
    @Path("{username: [a-zA-Z0-9]+}-{password}") // username regex matches only letters & digits
    @Produces(MediaType.APPLICATION_JSON)
    public UserModel getUserByUsernameAndPassword(@PathParam("username") String username,
                                       @PathParam("password") String password){
        for (var user: usersMap.values()) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password))
                return user;
        }
        return null; // 204 no-content response
    }

    //FormParam Test
    /**
     * Method handling HTTP POST request. The returned object will be sent according to the entered username and password in the url
     * to the client as "JSON" media type.
     *
     * @return Object that will be returned as JSON response, or null is the entered data is not correct
     */
    @POST
    @Produces(MediaType.TEXT_HTML)
    public String addUser(@FormParam("username") String username,
                                       @FormParam("password") String password){
        usersMap.put(idCounter.getAndIncrement(), new UserModel(username,password));
        return "added succefully";
    }

}
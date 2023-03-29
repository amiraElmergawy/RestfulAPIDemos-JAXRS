package iti.jets.amira.contenthandlerexample;

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
    @Produces(MediaType.TEXT_PLAIN)
    public Map<Integer,UserModel> getAllUsers(){ 
        return usersMap;
    }


    /**
     * Method handling HTTP GET request. The returned object will be sent according to the entered id in the url
     * to the client as "XML" media type.
     *
     * @return Object that will be returned as XML response.
     */
    @GET
    @Path("{id://d+}")
    @Produces(MediaType.APPLICATION_XML)
    public UserModel getUserById(@PathParam("id") int userId){
        return usersMap.get(userId);
    }

    /**
     * Method handling HTTP POST request. The returned object will be sent according to the entered username and password in the url
     * to the client as simple text message media type.
     *
     * @return Object that will be returned as text response
     */
    @POST
    @Produces(MediaType.TEXT_HTML)
    public String addUser(@FormParam("username") String username, 
                                       @FormParam("password") String password){
        usersMap.put(idCounter.getAndIncrement(), new UserModel(username,password));
        return "added succefully";
    }

}

package iti.jets.amira.complexresponsesexample;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import iti.jets.amira.models.UserModel;

/**
 * Root usersResource (exposed at "v4/users" path)
 */
@Path("v4/users")
public class UserController {

    private static Map<Integer, UserModel> usersMap = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger(100); // users ids will begin with 100


    /**
     * Method handling HTTP GET requests. The returned map of objects will be sent
     * to the client as "JSON" media type.
     *
     * @return map that will be returned as a JSON response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<Integer, UserModel> getAllUsers(){
        return usersMap;
    }


    /**
     * Method handling HTTP GET request. The returned object will be sent according to the entered id in the url
     * to the client as "XML" media type.
     *
     * @return Object that will be returned as XML response.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public UserModel getUserById(@PathParam("id") int userId){
        return usersMap.get(userId);
    }


    /**
     * Method handling HTTP POST request. The added object obtained from request body
     * to the client as simple text message media type.
     *
     * @return success message as text response
     */
    @POST
    @Produces(MediaType.TEXT_HTML)
    public String addUser(UserModel user){
        usersMap.put(idCounter.getAndIncrement(), user);
        return "added succefully";
    }




    @PUT
    @Path("{id}")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_XML)
    public String editUser(@PathParam("id") int userId, UserModel user){
        if(usersMap.containsKey(userId)){
            usersMap.put(userId, user);
            return "updated succefully";
        }
        return "invalid user"; // not found // but status code is 200 );
    }

}

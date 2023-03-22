package iti.jets.amira.simpleexample;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("users")
public class MyResource {

    private Map<Integer, UserModel> usersMap = new ConcurrentHashMap<>();
    private AtomicInteger idCounter = new AtomicInteger(100); //users ids will begin with 100
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }


//    @GET
//    @Path("{users}")
//    @Produces(MediaType.APPLICATION_JSON) // by default produces JSON if you provide JSON in your project dependencies
////    @Produces(MediaType.APPLICATION_XML)
//    public Map<Integer, UserModel> getAllUsers(){
//        return usersMap;
//    }

    @GET
    @Path("{id}")
//    @Produces(MediaType.APPLICATION_JSON) // by default produces JSON if you provide JSON in your project dependencies
    @Produces(MediaType.APPLICATION_XML)
    public UserModel getUser(@PathParam("id") int UserId){
        return new UserModel();
    }

}

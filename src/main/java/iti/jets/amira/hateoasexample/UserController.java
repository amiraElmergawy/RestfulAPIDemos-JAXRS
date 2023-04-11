package iti.jets.amira.hateoasexample;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Response.ResponseBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import iti.jets.amira.exceptionhandlingexample.exceptions.UserNotFoundException;
import iti.jets.amira.models.UserModel;

/**
 * Exception Handling
 *
 * Root usersResource (exposed at "v6/users" path)
 */
@Path("v6/users")
public class UserController {

    private static Map<Integer, UserModel> usersMap = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger(100); // users ids will begin with 100

    /**
     * Method handling HTTP GET requests. The returned list of objects will be sent
     * to the client as "JSON" media type.
     *
     * @return list that will be returned as a JSON response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsersJSON() {
        ResponseBuilder responseBuilder = Response.ok(usersMap.values());
        return responseBuilder.build();
    }

    /**
     * Method handling HTTP GET requests. The returned list of objects will be sent
     * to the client as "XML" media type.
     *
     * @return list that will be returned as a XML response.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getAllUsers() {

        GenericEntity<Collection<UserModel>> genericEntity = new GenericEntity<>(usersMap.values()) {
        }; // generic entity works as type-erasure it replaces all type parameters in
           // generic types with their bounds or Object if the type parameters are
           // unbounded
        ResponseBuilder responseBuilder = Response.ok(genericEntity);
        return responseBuilder.build();

    }

    /**
     * Method handling HTTP GET request. The returned object will be sent according
     * to the entered id in the url
     * to the client as "XML" media type.
     *
     * @return Object that will be returned as XML response.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getUserById(@PathParam("id") int userId, @Context UriInfo uriInfo) {
        var user = usersMap.get(userId);
        System.out.println(user);
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder()).rel("self").build();
        user.setLinks(Arrays.asList(self));
        return Response.ok(user).build(); // links will appear in the response body as it is embedded in the object
    }

    /**
     * Method handling HTTP POST request. The added object obtained from request
     * body
     * 
     * @return response with status code 201 and object "Location" in the header
     */
    @POST
    @Produces(MediaType.TEXT_HTML)
    public Response addUser(UserModel user, @Context UriInfo uriInfo) {
        Integer userId = idCounter.getAndIncrement();
        usersMap.put(userId, user);
        // return Response.ok("added succefully").link(uriInfo.getBaseUri().toString().concat(userId.toString()), "created-at").build(); // link will appear in the response header with "link" as the header key
    
        Link link = Link.fromPath(uriInfo.getBaseUri().toString().concat(userId.toString())).build();
        return Response.created(link.getUri()).build(); // link will appear in the response header with "Location" as the header key
    
    }

    /**
     * Method handling HTTP PUT request. The searched object using entered id
     * will be replaced by the object entered in the message body
     * then send to the client simple text message media type.
     *
     * @return success message as text response
     */
    @PUT
    @Path("{id}")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_XML)
    public Response editUser(@PathParam("id") int userId, UserModel user) {

        ResponseBuilder responseBuilder = Response.ok("updated succefully");
        if (usersMap.containsKey(userId)) { // user exist
            usersMap.put(userId, user);
            return responseBuilder.build();
        }
        throw new UserNotFoundException();

    }

}

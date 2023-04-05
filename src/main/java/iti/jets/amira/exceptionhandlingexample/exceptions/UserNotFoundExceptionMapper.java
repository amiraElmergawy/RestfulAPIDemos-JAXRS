package iti.jets.amira.exceptionhandlingexample.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider // tells JAX-RS that their is an exception mapper can convert UserNotFoundException to Response
public class UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException>{

    @Override
    public Response toResponse(UserNotFoundException userNotFoundException) {
        return Response.status(Response.Status.NOT_FOUND).entity("User Not found").build();
    }


}

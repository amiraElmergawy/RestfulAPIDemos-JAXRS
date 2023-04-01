package iti.jets.amira.contenthandlerexample.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

import iti.jets.amira.contenthandlerexample.UserModel;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.TEXT_PLAIN) // tells JAX-RS which type this class provide
public class UserMessageBodyWriter implements MessageBodyWriter <Map<Integer, UserModel>>{


    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Map.class.isAssignableFrom(type);    
    }
    
    @Override
    public void writeTo(Map<Integer, UserModel> users, Class<?> type, Type genericType, Annotation[] annotations,
            MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream outputStream)
            throws IOException, WebApplicationException {
        //{100=UserModel(username=amira, password=1234), 101=UserModel(username=amira, password=1234)}
        outputStream.write(users.toString().getBytes());
    }

}

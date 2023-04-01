package iti.jets.amira.contenthandlerexample.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import iti.jets.amira.contenthandlerexample.UserModel;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.Provider;

@Provider
@Consumes(MediaType.TEXT_PLAIN)
public class UserMessageBodyReader implements MessageBodyReader<List<UserModel>> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return List.class.isAssignableFrom(type);
    }

    /*
     * [
    {
        "username":"amira",
        "password":"123"
    },
    {
        "username":"amira2",
        "password":"1234"
    }
    ]
     */
    @Override
    public List<UserModel> readFrom(Class<List<UserModel>> type, Type genericType,
            Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
            InputStream inputStream) throws IOException, WebApplicationException {
        List<UserModel> usersList = null;
        String listString = new String(inputStream.readAllBytes());
        listString = listString.replace("[", ""); // {"username":"amira", "password":"123"}, {"username":"amira", "password":"123"}]
        listString = listString.replace("]", ""); // {"username":"amira", "password":"123"}, {"username":"amira", "password":"123"}
        listString = listString.replaceAll(" |\"", ""); //{"username":"amira", "password":"123"},{"username":"amira", "password":"123"}
        String[] listValues = listString.split("},"); // ["{"username":"amira", "password":"123"}","{"username":"amira", "password":"123"}"]
        List.of(listValues).stream().forEach((e)-> System.out.println(e));
        for (var user : listValues) {
            user = user.replace("{", ""); // {"username":"amira", "password":"123"
            user = user.replace("}", ""); // "username":"amira", "password":"123"
            String[] userDetails = user.split(",");// ["username":"amira" ,
                                                        //"password":"123"]
            List.of(userDetails).stream().forEach((e)-> System.out.println(e));
            var username = userDetails[0].trim().replace("username:", ""); // amira
            var password = userDetails[1].trim().replace("password:", ""); // password
            if (usersList == null)
                usersList = new ArrayList<>();
            usersList.add(new UserModel(username, password));
        }
        return usersList;

    }

}

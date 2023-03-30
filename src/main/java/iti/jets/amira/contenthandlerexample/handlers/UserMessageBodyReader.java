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
        // [UserModel(username=amira, password=123), UserModel(username=amira, password=123)]
        String listString = new String(inputStream.readAllBytes());
        listString = listString.replace("[", ""); // UserModel(username=amira, password=123), UserModel(username=amira, password=123)]
        listString = listString.replace("]", ""); // UserModel(username=amira, password=123), UserModel(username=amira, password=123)
        System.out.println("before trim:"+listString);
        listString = listString.replaceAll(" ", "");
        System.out.println("after trim:"+listString);
        String[] listValues = listString.split("UserModel"); // ["(username=amira, password=123)","(username=amira, password=123)"]
        List.of(listValues).stream().forEach((e)-> System.out.println(e));
        ////////////////////////spaaaaaaaaaaaaaaaaaaaaaaaaaaaace
        for (var user : listValues) {
            if (user.equals(listValues[0])) continue;
            user = user.replace("(", ""); // "username=amira, password=123)"
            user = user.replace(")", ""); // "username=amira, password=123"
            String[] userDetails = user.split(",");// ["username=amira" , "password=123"]
            var username = userDetails[0].replace("username=", ""); // amira
            var password = userDetails[1].replace("password=", ""); // password
            if (usersList == null)
                usersList = new ArrayList<>();
            usersList.add(new UserModel(username, password));
        }
        return usersList;

    }

    // @Override
    // public Map<Integer, UserModel> readFrom(Class<Map<Integer, UserModel>> type,
    // Type genericType,
    // Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String>
    // httpHeaders,
    // InputStream inputStream) throws IOException, WebApplicationException {

    // UserModel userModel;
    // Map<Integer, UserModel> usersMap = null;
    // // {100=UserModel(username=amira, password=1234),
    // 101=UserModel(username=amira,
    // // password=1234)}
    // String mapString = new String(inputStream.readAllBytes());

    // mapString = mapString.trim();
    // mapString = mapString.replace("{", ""); // 100=UserModel(username=amira,
    // password=1234),
    // // 101=UserModel(username=amira, password=1234)}
    // mapString = mapString.replace("}", ""); // 100=UserModel(username=amira,
    // password=1234),
    // // 101=UserModel(username=amira, password=1234)
    // mapString = mapString.trim(); // 100=UserModel(username=amira,
    // password=1234), 101=UserModel(username=amira,
    // // password=1234)

    // String[] MapValues = mapString.split("), "); //
    // {"100=UserModel(username=amira, password=1234" ,
    // // "101=UserModel(username=amira, password=1234)"}

    // for (String users : MapValues) {

    // users = users.replace("UserModel(", ""); // "100=username=amira,
    // password=1234)"
    // users = users.replace(")", ""); // "100=username=amira, password=1234"

    // String[] usersValues = users.split("="); // {"100", "username" , "amira,
    // password" , "1234"} //4

    // int mapKey = Integer.parseInt(usersValues[0]); //100
    // String username = usersValues[2].replace(", password", ""); //"amira"
    // String password = usersValues[3]; //"1234"
    // userModel = new UserModel(username, password);
    // if(usersMap == null) usersMap = new ConcurrentHashMap<>();
    // usersMap.put(mapKey, userModel);

    // }

    // return usersMap; // need to be list of users(:

    // }

}

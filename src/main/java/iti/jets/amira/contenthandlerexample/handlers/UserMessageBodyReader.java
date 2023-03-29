package iti.jets.amira.contenthandlerexample.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import iti.jets.amira.contenthandlerexample.UserModel;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.Provider;

@Provider
@Consumes(MediaType.TEXT_PLAIN)
public class UserMessageBodyReader implements MessageBodyReader<Map<Integer, UserModel>> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Map.class.isAssignableFrom(type);
    }

    @Override
    public Map<Integer, UserModel> readFrom(Class<Map<Integer, UserModel>> type, Type genericType,
            Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
            InputStream entityStream) throws IOException, WebApplicationException {

        UserModel userModel;
        Map<Integer, UserModel> usersMap = null;
        // {100=UserModel(username=amira, password=1234), 101=UserModel(username=amira,
        // password=1234)}
        String MapString = new String(entityStream.readAllBytes());

        MapString = MapString.trim();
        MapString = MapString.replace("{", ""); // 100=UserModel(username=amira, password=1234),
                                                // 101=UserModel(username=amira, password=1234)}
        MapString = MapString.replace("}", ""); // 100=UserModel(username=amira, password=1234),
                                                // 101=UserModel(username=amira, password=1234)
        MapString = MapString.trim(); // 100=UserModel(username=amira, password=1234), 101=UserModel(username=amira,
                                      // password=1234)

        String[] MapValues = MapString.split("), "); // {"100=UserModel(username=amira, password=1234" ,
                                                     // "101=UserModel(username=amira, password=1234)"}

        for (String users : MapValues) {

            users = users.replace("UserModel(", ""); // "100=username=amira, password=1234)"
            users = users.replace(")", ""); // "100=username=amira, password=1234"

            String[] usersValues = users.split("="); // {"100", "username" , "amira, password" , "1234"} //4

            int mapKey = Integer.parseInt(usersValues[0]); //100
            String username = usersValues[2].replace(", password", ""); //"amira"
            String password = usersValues[3]; //"1234"
            userModel = new UserModel(username, password);
            if(usersMap == null) usersMap = new ConcurrentHashMap<>(); 
            usersMap.put(mapKey, userModel);

        }

        return usersMap; // need to be list of users(:

    }

}

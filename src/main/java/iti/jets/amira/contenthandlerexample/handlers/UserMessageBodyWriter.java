package iti.jets.amira.contenthandlerexample.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import iti.jets.amira.contenthandlerexample.UserModel;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlRootElement;

@Provider // tells JAX-RS to use it as Marshaller
@Produces(MediaType.APPLICATION_XML) // tells JAX-RS which type this class provide
public class UserMessageBodyWriter implements MessageBodyWriter<UserModel> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        // check if the class can be represented as xml or not
        // return type.getClass().isAnnotationPresent(XmlRootElement.class);
        return true;
    }

    @Override
    public void writeTo(UserModel t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {

                try {
                    JAXBContext context = JAXBContext.newInstance(type);
                    context.createMarshaller().marshal(t, entityStream);
                } catch (JAXBException e) {
                    e.printStackTrace();
                }

    }

}

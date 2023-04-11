package iti.jets.amira.models;

import java.util.List;

import jakarta.ws.rs.core.Link;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement // XML binding
@XmlAccessorType(XmlAccessType.FIELD) // HATEOAS
public class UserModel {
    private String username;
    private String password;

    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)  // HATEOAS
    private List<Link> links;

    public UserModel(String username, String password){
        this.username = username;
        this.password = password;
    }
}

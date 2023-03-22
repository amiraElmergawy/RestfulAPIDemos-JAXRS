package iti.jets.amira.simpleexample;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class UserModel {
    private String username;
    private String password;
}

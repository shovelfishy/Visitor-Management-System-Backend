package dev.edward.projectx.model;

import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "residents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginData {

    @Id
    private String id;

    private HashMap <String, ?> user_info;
    private String username;
    private String password;

}


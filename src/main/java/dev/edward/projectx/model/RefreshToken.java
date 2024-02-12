package dev.edward.projectx.model;

import java.time.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Document(collection = "refreshTokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    
    @Id
    private String Id;

    private String token;

    private LocalDateTime expiration;

    private String userId;

    private boolean revoked;

}

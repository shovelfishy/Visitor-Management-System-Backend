package dev.edward.projectx;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.edward.projectx.model.LoginData;
import dev.edward.projectx.model.VisitorData;
import dev.edward.projectx.services.LoginDataService;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.*;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.security.*;


@RestController
@RequestMapping(value="/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {

    @Autowired
    private LoginDataService loginDataService;

    @GetMapping
    public List<LoginData> getAll() {
        return loginDataService.getAllData();
    }

    private final String SECRET_KEY = "my-super-duper-secret-key-for-my-project"; 

    @GetMapping("/refresh")
    public String refreshToken() {
        return null;
    }

    private Key getSignKey(){
        byte[] secretKey = Base64.getEncoder().encode(SECRET_KEY.getBytes());
        return Keys.hmacShaKeyFor(secretKey);
    }
    
    @GetMapping("/create")
    public String createToken() {

        Date exp = new Date(System.currentTimeMillis()+1000*60);
        Map<String, String> claims = new HashMap<>();
        claims.put("user","hello");
        claims.put("age","pe");

        String jwt = Jwts.builder()
            .header()
                .add("typ","jwt").and()
            .claims(claims)
            .expiration(exp)
            .signWith(getSignKey()).compact();
        return jwt;
    }

    @PostMapping("/login")
    public String authUser(@RequestBody Map<String, String> details) {
        return loginDataService.authUser(details.get("username"), details.get("password"));
    }

    @GetMapping("/test")
    public String test() {
        return "hello world";
    }

}
package dev.edward.projectx.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import dev.edward.projectx.model.LoginData;

public interface LoginRepository extends MongoRepository<LoginData, String>{

    // @Query(value="{name: '?0'}", fields="{'name' : 1, 'email' : 1}")
    @Query("{name: '?0'}")
    List<LoginData> findByField(String field);

    @Query(value="{'username': ?0, 'password': ?1}", fields="{'username': 1, 'password': 1}")
    LoginData findUserLogin(String username, String password);

}

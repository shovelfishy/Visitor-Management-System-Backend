package dev.edward.projectx.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;
import com.mongodb.internal.bulk.UpdateRequest;

import dev.edward.projectx.model.LoginData;
import dev.edward.projectx.model.VisitorData;
import dev.edward.projectx.repositories.LoginRepository;

import java.util.List;

@Service
public class LoginDataService {
    
    @Autowired
    private LoginRepository LoginRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<LoginData> getAllData(){
        return LoginRepository.findAll();
    }

    public Long editVisitor(VisitorData visitor){
        Query query = new Query().addCriteria(Criteria.where("id").is(visitor.getId()));
        Update update = new Update();
        update.set("name", visitor.getName());
        update.set("car_plate", visitor.getCar_plate());
        update.set("date", visitor.getDate());
        update.set("time", visitor.getTime());
           
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, VisitorData.class);

        return updateResult.getModifiedCount();
    }

    public String authUser(String username, String password){

        try {
            LoginData user = LoginRepository.findUserLogin(username, password);
            if(!user.getUsername().equals(username) || !user.getPassword().equals(password)){
                return "ERROR";
            } else {
                return "SUCCESS";
            }
        } catch (Exception e) {
            return "ERROR";
        }
    }
}

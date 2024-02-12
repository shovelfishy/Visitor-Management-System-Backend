package dev.edward.projectx.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import dev.edward.projectx.model.VisitorData;

public interface VisitorRepository extends MongoRepository<VisitorData, String>{

    // @Query()
    // List<VisitorData> checkVisitors() 
    @DeleteQuery("{name: '?0'}")
    List<VisitorData> deleteByName(String name); 
    
    @Query("{id: '?0'}")
    VisitorData getVisitorByID(String id); 

}

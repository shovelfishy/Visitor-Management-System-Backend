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
import dev.edward.projectx.repositories.VisitorRepository;

import java.util.List;

@Service
public class VisitorDataService {
    

    @Autowired
    private VisitorRepository VisitorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public VisitorData insertVisitor(VisitorData visitor){
        return VisitorRepository.insert(visitor);
    }

    public VisitorData saveVisitor(VisitorData visitor){
        return VisitorRepository.save(visitor);
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

    public Boolean checkDuplicateVisitor(VisitorData visitor){
        
        List<VisitorData> visitors = VisitorRepository.findAll();

        for (VisitorData visitorData : visitors) {

            if (visitor.equals(visitorData))
                return true;
        }
        return false;
    }

    public List<VisitorData> deleteByName(String name){
        return VisitorRepository.deleteByName(name);
    }

    public List<VisitorData> getAllVisitor(){
        return VisitorRepository.findAll();
    }

    public VisitorData getVisitorByID(String id){
        return VisitorRepository.getVisitorByID(id);
    }
}

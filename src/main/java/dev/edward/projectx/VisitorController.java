package dev.edward.projectx;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
import dev.edward.projectx.services.VisitorDataService;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.*;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.security.*;


@RestController
@RequestMapping(value="/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class VisitorController {

    @Autowired
    private VisitorDataService visitorDataService;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    private VisitorData createVisitorData(Map<String, Object> visitorDetails){

        String[] dateStr = visitorDetails.get("date").toString().split("-");
        String[] timeStr = visitorDetails.get("time").toString().split(":");
        int[] dateInt = new int[dateStr.length];
        int[] timeInt = new int[timeStr.length];

        for (int i=0; i<dateStr.length; i++) {
            dateInt[i] = Integer.parseInt(dateStr[i]);
        }

        for (int i=0; i<timeStr.length; i++) {
            timeInt[i] = Integer.parseInt(timeStr[i]);
        }

        LocalDate ld = LocalDate.of(dateInt[0], dateInt[1], dateInt[2]);
        LocalTime lt = LocalTime.of(timeInt[0], timeInt[1]);
        LocalDateTime ldt = LocalDateTime.of(ld, lt);
        
        VisitorData visitor = new VisitorData((String)visitorDetails.get("name"), (String)visitorDetails.get("car_plate"), ld, lt);

        return visitor;
    }

    @GetMapping("/{residentID}")
    public List<VisitorData> getAllVisitor(@PathVariable String residentID) {
        Query query = new Query().with(Sort.by("date").descending().and(Sort.by("time").descending()));
        query.addCriteria(Criteria.where("residentID").is(residentID));
        return mongoTemplate.find(query, VisitorData.class);
    }

    @GetMapping("/visitor")
    public VisitorData getVisitor(@RequestHeader(value="Id") String visitorID){
        return visitorDataService.getVisitorByID(visitorID);
    }

    @PostMapping("/users/checkin")
    public VisitorData checkIn(@RequestBody Map<String, Object> visitorDetails) {

        VisitorData visitor = createVisitorData(visitorDetails);

        if(!visitorDataService.checkDuplicateVisitor(visitor)){
            visitorDataService.insertVisitor(visitor);
            return visitor;
        }

        return new VisitorData("Duplicate Entry Error");
    }
    
    @PutMapping("/users/edit")
    public Long editVisitor(@RequestBody Map<String, Object> visitorDetails){
        VisitorData visitor = createVisitorData(visitorDetails);
        visitor.setId(visitorDetails.get("id").toString());
        return visitorDataService.editVisitor(visitor);
    }
    
    @DeleteMapping("/{name}")
    public List<VisitorData> editVisitor(@PathVariable String name){
        return visitorDataService.deleteByName(name);  
    }

    // @PostMapping("/test")
    //  public VisitorData test(@RequestBody String id) {
    //     return visitorDataService.getAllVisitor().get(1);
    // }

}
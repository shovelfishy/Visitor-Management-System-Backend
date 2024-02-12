package dev.edward.projectx.model;

import java.time.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Document(collection = "visitors")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VisitorData {
    
    @Id
    private String id;

    @NonNull @EqualsAndHashCode.Include private String name;
    @NonNull @EqualsAndHashCode.Include private String car_plate;
    @NonNull @EqualsAndHashCode.Include private LocalDate date;
    @NonNull @EqualsAndHashCode.Include private LocalTime time;
    private LocalDateTime datetime;
    private String error;
    private String residentID;

    public VisitorData(String error){
        this.error = error;
    }

}

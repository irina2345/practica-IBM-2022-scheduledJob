package com.practica.ibm.scheduledjob.models;

import com.practica.ibm.scheduledjob.util.AlertingMethods;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("settings")
public class Settings {

    private @Getter @Setter String adminEmail;

    private @Getter @Setter int daysBeforeAlerting;

    private @Getter @Setter AlertingMethods alertingMethod;

}

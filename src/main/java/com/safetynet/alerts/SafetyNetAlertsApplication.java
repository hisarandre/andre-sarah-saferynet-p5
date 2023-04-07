package com.safetynet.alerts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.repository.DataRepository;



@SpringBootApplication
public class SafetyNetAlertsApplication implements CommandLineRunner {
	
	@Autowired
	DataRepository dataRepository;
	
	@Autowired
	Data data;
	
	private static final String FILEPATH = "src/main/resources/data.json";
	
	public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}
	
    @Override
    public void run(String... args) throws Exception {
    	dataRepository.read(FILEPATH);
    }


}

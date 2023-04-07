package com.safetynet.alerts.repository;
import com.safetynet.alerts.model.Data;

import java.io.File;

import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class DataRepository {
	
	private Data data;
	
	public Data read(String FILEPATH){
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			//JSON URL to Java object
			data = mapper.readValue(new File(FILEPATH), Data.class);	
	
		 } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	     }
		
		return data;
			
	}
	
}

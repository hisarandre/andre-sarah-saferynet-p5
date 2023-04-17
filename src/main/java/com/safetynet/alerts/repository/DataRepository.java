package com.safetynet.alerts.repository;
import com.safetynet.alerts.model.Data;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class DataRepository {
	
	private Data data;
	
	private static final Logger LOGGER = LogManager.getLogger(DataRepository.class);

	/** 
	* Read a json file and map values in Data model class
	* @param FILEPATH url of the json file
	* @throws Exception 
	*/ 
	public Data read(String FILEPATH) throws Exception{
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			//JSON URL to Java object
			data = mapper.readValue(new File(FILEPATH), Data.class);
	
		 } catch (Exception e) {
	            LOGGER.error("Fail to read the json file", e);
	            throw e;
	     }
		
    	LOGGER.info("Read and mapped data:", data);
		return data;
			
	}
	
}

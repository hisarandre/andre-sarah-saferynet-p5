package com.safetynet.alerts.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.alerts.model.Data;

@SpringBootTest
class DataRepositoryTest {
	
	@Autowired
	private DataRepository dataRepository;
		
	@Test
	void testReadJsonFile() throws Exception {
		String FILEPATH = "src/main/resources/data.json";
		
		Data result = dataRepository.read(FILEPATH);
		
		assertNotNull(result);
		assertEquals(result.getPersons().size(), 23);
	}

}

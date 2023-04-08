package com.safetynet.alerts.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {

	@Autowired
	public MockMvc mvc;
	
    @Test
    public void testGetMedicalRecord() throws Exception{
		mvc.perform(get("/medicalrecord")).andExpect(status().isOk());
    }

	@Test
    public void testPostMedicalRecord() throws Exception{

        String newMedicalRecord = "{\"firstName\":\"jon\",\"lastName\":\"snow\",\"birthdate\":\"30/03/2000\",\"medications\":[\"ibupurin:200mg\",\"hydrapermazol:400mg\"],\"allergies\":[\"nillacilan\"]}";
        
		mvc.perform(post("/medicalrecord")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newMedicalRecord))
        .andExpect(status().isOk()); 
    }
	
	@Test
    public void testPostMedicalRecordIfNoContentSent() throws Exception{
        String empty = "";
        
		mvc.perform(post("/medicalrecord")
        .contentType(MediaType.APPLICATION_JSON)
        .content(empty))
        .andExpect(status().isBadRequest());
    }
	
	@Test
    public void testDeleteMedicalRecord() throws Exception{
		mvc.perform(delete("/medicalrecord?firstName=jon&lastName=snow")).andExpect(status().isOk());
    }
	
	@Test
    public void testDeleteMedicalRecordIfMissingName() throws Exception{
		mvc.perform(delete("/medicalrecord")).andExpect(status().isBadRequest());
    }
	
	@Test
    public void testPutPerson() throws Exception{
        String newMedicalRecord = "{\"firstName\":\"jon\",\"lastName\":\"snow\",\"birthdate\":\"30/03/2000\",\"medications\":[\"ibupurin:200mg\",\"hydrapermazol:400mg\"],\"allergies\":[\"nillacilan\"]}";

		mvc.perform(put("/medicalrecord?firstName=jon&lastName=snow")
	    .contentType(MediaType.APPLICATION_JSON)
	    .content(newMedicalRecord))
	    .andExpect(status().isOk()); 
    }
	
	@Test
    public void testPutPersonIfMissingName() throws Exception{
	    String newMedicalRecord = "{\"firstName\":\"jon\",\"lastName\":\"snow\",\"address\":\"une rue\",\"city\":\"une ville\",\"zip\":\"00000\",\"phone\":\"000-111-2222\",\"email\":\"js.stark@mail.com\"}";

		mvc.perform(put("/medicalrecord")
	    .contentType(MediaType.APPLICATION_JSON)
	    .content(newMedicalRecord))
        .andExpect(status().isBadRequest());
    }
}


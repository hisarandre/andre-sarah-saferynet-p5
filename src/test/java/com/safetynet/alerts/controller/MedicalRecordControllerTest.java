package com.safetynet.alerts.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.DTO.EmptyJsonDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {

	@Autowired
	public MockMvc mvc;
	
    @InjectMocks
    private MedicalRecordController medicalRecordController;
    
    @Mock
    private MedicalRecordService medicalRecordService;
	
    @Test
    public void testGetMedicalRecord() throws Exception{
		mvc.perform(get("/medicalrecord")).andExpect(status().isOk());
    }
    
    @Test
    public void testGetAllMedicalRecordWhenNotFound() {
        List<MedicalRecord> medicalRecordList = new ArrayList<>();
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(medicalRecordList);

        ResponseEntity<Object> response = medicalRecordController.getAllMedicalRecord();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
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
	public void testDeleteMedicalRecordIsNotFound() {
	    String firstName = "John";
	    String lastName = "Doe";
	    
	    when(medicalRecordService.deleteMedicalRecord(firstName, lastName)).thenReturn(new ArrayList<>());
	    
	    ResponseEntity<Object> response = medicalRecordController.deleteMedicalRecord(firstName, lastName);
	    
	    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	

    @Test
    public void testUpdateMedicalRecord() throws Exception{
    	MedicalRecord udaptedMedicalRecord = new MedicalRecord();
    	List<String> allergies = new ArrayList<>();
        List<String> medications = new ArrayList<>();
        allergies.add("cats");
        allergies.add("peanuts");
        medications.add("tetracyclaz:650mg");
        medications.add("dodoxadin:30mg");
    	udaptedMedicalRecord.setFirstName("John");
    	udaptedMedicalRecord.setLastName("Doe");
    	udaptedMedicalRecord.setBirthdate("08/05/1992");
    	udaptedMedicalRecord.setAllergies(allergies);
    	udaptedMedicalRecord.setMedications(medications);
        

        String newMedicalRecord = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthdate\":\"30/03/2000\",\"medications\":[\"ibupurin:200mg\",\"hydrapermazol:400mg\"],\"allergies\":[\"nillacilan\"]}";

        
        when(medicalRecordService.udapteMedicalRecord(any(MedicalRecord.class), anyString(), anyString())).thenReturn(udaptedMedicalRecord);
         
        mvc.perform(put("/medicalrecord")
        		.param("firstName", "John")
        		.param("lastName", "Doe")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(newMedicalRecord))
        		.andExpect(status().isOk());
        
        verify(medicalRecordService, times(1)).udapteMedicalRecord(any(MedicalRecord.class), anyString(), anyString());
    }
    
    @Test
    public void testUpdateMedicalRecord_3() throws Exception {
    	//MedicalRecord mr = new MedicalRecord();
        //when(medicalRecordService.getAllMedicalRecords(anyLong())).thenReturn(mr);
        when(medicalRecordService.udapteMedicalRecord(any(MedicalRecord.class), anyString(),anyString())).thenReturn(new MedicalRecord());
        mvc.perform(put("/medicalrecords/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }
    
    @Test
    public void testUpdateMedicalRecord_2() {
    	MedicalRecord udaptedMedicalRecord = new MedicalRecord();
    	List<String> allergies = new ArrayList<>();
        List<String> medications = new ArrayList<>();
        allergies.add("cats");
        allergies.add("peanuts");
        medications.add("tetracyclaz:650mg");
        medications.add("dodoxadin:30mg");
    	udaptedMedicalRecord.setFirstName("John");
    	udaptedMedicalRecord.setLastName("Doe");
    	udaptedMedicalRecord.setBirthdate("08/05/1992");
    	udaptedMedicalRecord.setAllergies(allergies);
    	udaptedMedicalRecord.setMedications(medications);
        
        when(medicalRecordService.udapteMedicalRecord(any(MedicalRecord.class), anyString(), anyString())).thenReturn(udaptedMedicalRecord);
        
        ResponseEntity<Object> response = medicalRecordController.getAllMedicalRecord();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}


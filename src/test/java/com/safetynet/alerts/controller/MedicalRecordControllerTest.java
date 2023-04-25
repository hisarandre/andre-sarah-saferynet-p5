package com.safetynet.alerts.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.model.MedicalRecord;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {

	@Autowired
	public MockMvc mvc;
	
    @InjectMocks
    private MedicalRecordController medicalRecordController;
    
    @MockBean
    private MedicalRecordService medicalRecordService;
	
    @Test
    public void testGetMedicalRecord() throws Exception{
    	List<MedicalRecord> medicalRecordList = new ArrayList<>();
    	MedicalRecord medicalRecord = new MedicalRecord();
    	List<String> allergies = new ArrayList<>();
        List<String> medications = new ArrayList<>();
        allergies.add("cats");
        allergies.add("peanuts");
        medications.add("tetracyclaz:650mg");
        medications.add("dodoxadin:30mg");
        medicalRecord.setFirstName("Jane");
        medicalRecord.setLastName("Doe");
        medicalRecord.setBirthdate("08/05/1992");
        medicalRecord.setAllergies(allergies);
        medicalRecord.setMedications(medications);	
        medicalRecordList.add(medicalRecord);
        
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(medicalRecordList);

        mvc.perform(get("/medicalrecord")).andExpect(status().isOk()); 
    }
    
    @Test
    public void testGetAllMedicalRecordIsNotFound() throws Exception {
        List<MedicalRecord> medicalRecordList = new ArrayList<>();
        
        when(medicalRecordService.getAllMedicalRecords()).thenReturn(medicalRecordList);

        mvc.perform(get("/medicalrecord")).andExpect(status().isNotFound()); 
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
		List<MedicalRecord> medicalRecordDeleted = new ArrayList<>();
		
		MedicalRecord medicalRecord = new MedicalRecord();
    	List<String> allergies = new ArrayList<>();
        List<String> medications = new ArrayList<>();
        allergies.add("cats");
        allergies.add("peanuts");
        medications.add("tetracyclaz:650mg");
        medications.add("dodoxadin:30mg");
        medicalRecord.setFirstName("Jane");
        medicalRecord.setLastName("Doe");
        medicalRecord.setBirthdate("08/05/1992");
        medicalRecord.setAllergies(allergies);
        medicalRecord.setMedications(medications);	
        medicalRecordDeleted.add(medicalRecord);
        
		when(medicalRecordService.deleteMedicalRecord(anyString(), anyString()))
        .thenReturn(medicalRecordDeleted);
		
		mvc.perform(delete("/medicalrecord")
			.param("firstName", "John")
			.param("lastName", "Doe"))
			.andExpect(status().isOk());
    }
	
	@Test
	public void testDeleteMedicalRecordIsNotFound() throws Exception {
		List<MedicalRecord> medicalRecordDeleted = new ArrayList<>();
        
		when(medicalRecordService.deleteMedicalRecord(anyString(), anyString()))
        .thenReturn(medicalRecordDeleted);
		
		mvc.perform(delete("/medicalrecord")
			.param("firstName", "John")
			.param("lastName", "Doe"))
			.andExpect(status().isNotFound());
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
        

        when(medicalRecordService.updateMedicalRecord(any(MedicalRecord.class), anyString(), anyString()))
        .thenReturn(udaptedMedicalRecord);
 
        String newMedicalRecord = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"birthdate\":\"30/09/2000\",\"medications\":[\"ibupurin:200mg\",\"hydrapermazol:400mg\"],\"allergies\":[\"nillacilan\"]}";

        mvc.perform(put("/medicalrecord")
        		.param("firstName", "John")
        		.param("lastName", "Doe")
        		.content(newMedicalRecord)
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk());
        
        verify(medicalRecordService, times(1)).updateMedicalRecord(any(MedicalRecord.class), anyString(), anyString());
    }
    
    @Test
    public void testUpdateMedicalRecordIsNotFound() throws Exception{
        when(medicalRecordService.updateMedicalRecord(any(MedicalRecord.class), anyString(), anyString()))
        .thenReturn(null);
 
        String newMedicalRecord = "{}";

        mvc.perform(put("/medicalrecord")
        		.param("firstName", "John")
        		.param("lastName", "Doe")
        		.content(newMedicalRecord)
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isNotFound());
            }
}


package com.safetynet.alerts.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.alerts.MockData;
import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.MedicalRecord;

@SpringBootTest
public class MedicalRecordRepositoryTest {
	
    @Autowired
    private MockData mockData;
    
    @Autowired
    private Data data;
    
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    
    @Mock
    private DataRepository dataRepository;
    
    @BeforeEach()
    public void setUp() throws Exception{
    	data = mockData.getAllMockData();
        Mockito.when(dataRepository.read(anyString())).thenReturn(data);
    }
    
    @Test
    public void testGetAllMedicalRecords() throws Exception{
    	List<MedicalRecord> listMedicalRecords= medicalRecordRepository.getAllMedicalRecords();
        assertEquals(3, listMedicalRecords.size());
    }
    
    @Test
    public void testCreateMedicalRecord() throws Exception{ 
        List<String> allergies = new ArrayList<>();
        List<String> medications = new ArrayList<>();
    	allergies.add("cats");
    	allergies.add("peanuts");
        medications.add("tetracyclaz:650mg");
        medications.add("dodoxadin:30mg");
         
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Lily");
        medicalRecord.setLastName("Evans");
        medicalRecord.setBirthdate("30/03/2000");
        medicalRecord.setAllergies(allergies);
        medicalRecord.setMedications(medications);
        
    	medicalRecordRepository.createMedicalRecord(medicalRecord);
    	List<MedicalRecord> listMedicalRecords= data.getMedicalrecords();
        assertEquals(4, listMedicalRecords.size());
    }
    
    @Test
    public void testDeleteMedicalRecord() throws Exception{
    	List<MedicalRecord> listMedicalRecords= data.getMedicalrecords();
        assertEquals(listMedicalRecords.size(), 3);
        
        medicalRecordRepository.deleteMedicalRecord("Alix", "Han");
    	listMedicalRecords= data.getMedicalrecords();
        assertEquals(listMedicalRecords.size(), 2);
    }
    
    @Test
    public void testUdapteMedicalRecord() throws Exception{
        List<String> allergies = new ArrayList<>();
        List<String> medications = new ArrayList<>();
    	allergies.add("cats");
    	allergies.add("peanuts");
        medications.add("tetracyclaz:650mg");
        medications.add("dodoxadin:30mg");
         
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Alix");
        medicalRecord.setLastName("Han");
        medicalRecord.setBirthdate("30/03/2000");
        medicalRecord.setAllergies(allergies);
        medicalRecord.setMedications(medications);
    	
        medicalRecordRepository.udapteMedicalRecord(medicalRecord,"Alix","Han");
    	
    	List<MedicalRecord> listMedicalRecords = data.getMedicalrecords();
    	MedicalRecord Alix= listMedicalRecords.get(0);
        assertEquals("30/03/2000", Alix.getBirthdate());
    }
}

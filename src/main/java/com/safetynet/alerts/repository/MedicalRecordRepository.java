package com.safetynet.alerts.repository;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.MedicalRecord;

@Repository
public class MedicalRecordRepository {

    @Autowired
    private Data data;

    public List<MedicalRecord> getAllMedicalRecords() {
        return data.getMedicalrecords();
    }
    
    public void createMedicalRecord(MedicalRecord medicalRecord){
    	data.getMedicalrecords().add(medicalRecord);
    }
    
    public void deleteMedicalRecord(String firstName, String lastName){
    	List<MedicalRecord> medicalRecord = findByName(firstName,lastName);
    	data.getMedicalrecords().removeAll(medicalRecord);
    }
    
    public void udapteMedicalRecord(MedicalRecord medicalRecord, String firstName, String lastName) {
    	List<MedicalRecord> medicalrecordsList = data.getMedicalrecords();
    	
    	for(MedicalRecord mr : medicalrecordsList) {
    		if((mr.getFirstName().equals(firstName)) && (mr.getLastName()).equals(lastName)) {
    			mr.setBirthdate(medicalRecord.getBirthdate());
    			mr.setMedications(medicalRecord.getMedications());
    			mr.setAllergies(medicalRecord.getAllergies());
            }
    	}
    }
    
    public List<MedicalRecord> findByName(String firstName, String lastName) {
    	List<MedicalRecord> medicalRecordsList = data.getMedicalrecords();
    	List<MedicalRecord> found = new ArrayList<>();
    	
    	for(MedicalRecord mr : medicalRecordsList) {
    		if((mr.getFirstName().equals(firstName)) && (mr.getLastName()).equals(lastName)) {
    			found.add(mr);
            }
    	}
		return found;
    }
    
    public int calculateAge(String date) {
    	LocalDate today = LocalDate.now();
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
    	LocalDate birthday = LocalDate.parse(date, formatter); 
    	    	
    	int age = Period.between(birthday,today).getYears();
    	
		return age;
    }
}

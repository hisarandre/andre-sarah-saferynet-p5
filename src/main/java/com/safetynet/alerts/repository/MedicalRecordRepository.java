package com.safetynet.alerts.repository;

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
    	MedicalRecord medicalRecord = findByName(firstName,lastName);
    	data.getMedicalrecords().remove(medicalRecord);
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
    
    public MedicalRecord findByName(String firstName, String lastName) {
    	List<MedicalRecord> medicalRecordsList = data.getMedicalrecords();
    	
    	for(MedicalRecord mr : medicalRecordsList) {
    		if((mr.getFirstName().equals(firstName)) && (mr.getLastName()).equals(lastName)) {
    			return mr;
            }
    	}
		return null;
    }
}

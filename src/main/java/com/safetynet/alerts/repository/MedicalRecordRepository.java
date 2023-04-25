package com.safetynet.alerts.repository;

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

    /**
     * Rretrieves all the medical records
     * 
     * @return a list of all the medical records
     */
    public List<MedicalRecord> getAllMedicalRecords() {
        return data.getMedicalrecords();
    }

    /**
     * Creates a new medical record.
     * 
     * @param medicalRecord the new medical record to be created
     */
    public void createMedicalRecord(MedicalRecord medicalRecord){
    	data.getMedicalrecords().add(medicalRecord);
    }

    /**
     * Deletes an existing medical record by firstname and lastname.
     * 
     * @param firstName the first name of the person
     * @param lastName the last name of the person
     * @return MedicalRecord the medical record deleted
     */
    public List<MedicalRecord> deleteMedicalRecord(String firstName, String lastName){
    	List<MedicalRecord> medicalRecord = findByName(firstName,lastName);
    	data.getMedicalrecords().removeAll(medicalRecord);
    	return medicalRecord;
    }

    /**
     * Updates an existing medical record by first name and last name.
     * 
     * @param medicalRecord the updated medical record
     * @param firstName the firstname of the person
     * @param lastName the lastname of the person
     * @return MedicalRecord the medical record updated
     */
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord, String firstName, String lastName) {
    	List<MedicalRecord> medicalrecordsList = data.getMedicalrecords();
    	
    	String newBirthdate= medicalRecord.getBirthdate();
    	List<String> newAllergies = medicalRecord.getAllergies();
    	List<String> newMedications = medicalRecord.getMedications();
    			
    	for(MedicalRecord mr : medicalrecordsList) {
    		if((mr.getFirstName().equals(firstName)) && (mr.getLastName()).equals(lastName)) {
    			
    			if(newBirthdate != null) mr.setBirthdate(newBirthdate);
    	    	if(newAllergies != null) mr.setAllergies(newAllergies);
    	    	if(newMedications != null) mr.setMedications(newMedications);
    	    	
    	    	return mr;
            }
    	}
    	
    	return null;
    }

    /**
     * Search for a medical record by firstname and lastname.
     * 
     * @param firstName the firstname of the person
     * @param lastName the lastname of the person
     * @return a list of medical records
     */
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
    
}

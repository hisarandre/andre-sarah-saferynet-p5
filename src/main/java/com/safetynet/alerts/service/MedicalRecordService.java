package com.safetynet.alerts.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }
    
    public List<MedicalRecord> getAllMedicalRecords(){
        return medicalRecordRepository.getAllMedicalRecords(); 
    }
    
    public void addMedicalRecord(MedicalRecord medicalRecord){
        medicalRecordRepository.createMedicalRecord(medicalRecord); 
    }
    
    public List<MedicalRecord> deleteMedicalRecord(String firstName, String lastName){
        return medicalRecordRepository.deleteMedicalRecord(firstName, lastName); 
    }
    
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord, String firstName, String lastName){
    	return medicalRecordRepository.updateMedicalRecord(medicalRecord, firstName, lastName);
    }
    
    public int calculateAge(String date) {
    	LocalDate today = LocalDate.now();
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
    	LocalDate birthday = LocalDate.parse(date, formatter); 
    	    	
    	int age = Period.between(birthday,today).getYears();
    	
		return age;
    }
    
}

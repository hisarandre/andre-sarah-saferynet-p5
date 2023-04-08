package com.safetynet.alerts.service;

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
    
    public void deleteMedicalRecord(String firstName, String lastName){
        medicalRecordRepository.deleteMedicalRecord(firstName, lastName); 
    }
    
    public void udapteMedicalRecord(MedicalRecord medicalRecord, String firstName, String lastName){
    	medicalRecordRepository.udapteMedicalRecord(medicalRecord, firstName, lastName);
    }
    
}

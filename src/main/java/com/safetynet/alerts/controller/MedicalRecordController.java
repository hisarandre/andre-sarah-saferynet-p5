package com.safetynet.alerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;

@RestController
public class MedicalRecordController {

	@Autowired
    private MedicalRecordService medicalRecordService;
	//private static final Logger logger = LogManager.getLogger(PersonController.class);

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/medicalrecord")
    public List<MedicalRecord> getAllMedicalRecord(){
        return medicalRecordService.getAllMedicalRecords();
    }
    
    @PostMapping("/medicalrecord")
    public void createMedicalRecord(@RequestBody MedicalRecord medicalRecord){
    	medicalRecordService.addMedicalRecord(medicalRecord);
    }
    
    @DeleteMapping("/medicalrecord")
    public void deleteMedicalRecord(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {       
    	medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }
    
    @PutMapping("/medicalrecord")
    public void udapteMedicalRecord(@RequestBody MedicalRecord medicalrecord, @RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {       
    	medicalRecordService.udapteMedicalRecord(medicalrecord, firstName, lastName);
    }
}

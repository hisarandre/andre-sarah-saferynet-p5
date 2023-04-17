package com.safetynet.alerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.DTO.EmptyJsonDTO;
import com.safetynet.alerts.service.MedicalRecordService;

@RestController
public class MedicalRecordController {

	@Autowired
    private MedicalRecordService medicalRecordService;
	
	private static final Logger LOGGER = LogManager.getLogger(MedicalRecordController.class);

	/** 
	* Class constructor.
	* @param medicalRecordService
	*/
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }
    
    /** 
    * REQUEST GET : the list of each medicalrecord.
    * 
    * @return ResponseEntity containing the list of medicalrecord  and an HTTP status code
    */
    @GetMapping("/medicalrecord")
    public ResponseEntity<Object> getAllMedicalRecord(){
    	List<MedicalRecord> medicalRecordList = medicalRecordService.getAllMedicalRecords();
        
    	if(!medicalRecordList.isEmpty()){
        	LOGGER.info("Got all medical records");
    		return new ResponseEntity<>(medicalRecordList, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed get data");
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }
    
    /**
     * Request POST : create a new medicalrecord.
     * 
     * @param medicalRecord the medicalRecord to create
     * @return ResponseEntity containing the new medical record and an HTTP status code
     */
    @PostMapping("/medicalrecord")
    public ResponseEntity<Object> createMedicalRecord(@RequestBody MedicalRecord medicalRecord){
    	LOGGER.debug("Request POST: Creating a new medicalRecord");
    	medicalRecordService.addMedicalRecord(medicalRecord);
    	
    	if(medicalRecord != null){
        	LOGGER.info("Medical record is added");
    		return new ResponseEntity<>(medicalRecord, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed to add :" + medicalRecord);
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }
   
    /**
     * Request DELETE : delete a medicalrecord by firstName and lastName.
     * 
     * @param firstName the person's firstname
     * @param lastName the person's lastname
     * @return ResponseEntity containing the deleted medical record and an HTTP status code
     */
    @DeleteMapping("/medicalrecord")
    public ResponseEntity<Object> deleteMedicalRecord(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {       
    	LOGGER.debug("Request DELETE: Deleting " + firstName + lastName + "'s medicalrecord");
    	List<MedicalRecord> medicalRecordDeleted = medicalRecordService.deleteMedicalRecord(firstName, lastName);
    	
    	if(!medicalRecordDeleted.isEmpty()){
        	LOGGER.info("Medical record is deleted");
    		return new ResponseEntity<>(medicalRecordDeleted, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed to delete :" + firstName);
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }
    
    /**
     * Request PUT : udapte a medicalrecord by firstName and lastName.
     * 
     * @param medicalrecord the udapted infos of medicalrecord 
     * @param firstName the person's firstname
     * @param lastName the person's lastname
     * @return ResponseEntity containing the updated medical record and an HTTP status code
     */
    @PutMapping("/medicalrecord")
    public ResponseEntity<Object> udapteMedicalRecord(@RequestBody MedicalRecord medicalrecord, @RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {       
    	LOGGER.debug("Request PUT: Udapting " + firstName + lastName + "'s medicalrecord");
    	MedicalRecord medicalRecordUdapted = medicalRecordService.udapteMedicalRecord(medicalrecord, firstName, lastName);
    	
    	if(medicalRecordUdapted != null){
        	LOGGER.info("Medical record is updated");
    		return new ResponseEntity<>(medicalRecordUdapted, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed to update :" + medicalrecord);
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }
}

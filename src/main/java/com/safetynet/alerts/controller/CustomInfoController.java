package com.safetynet.alerts.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.DTO.AdultAndChildDTO;
import com.safetynet.alerts.model.DTO.EmptyJsonDTO;
import com.safetynet.alerts.model.DTO.FamiliesAndStationDTO;
import com.safetynet.alerts.model.DTO.FamilyAndStationDTO;
import com.safetynet.alerts.model.DTO.PersonAndMedicalRecordDTO;
import com.safetynet.alerts.model.DTO.PersonWithNbOfAdultsAndChildrenDTO;
import com.safetynet.alerts.service.CustomInfoService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class CustomInfoController {
	
	@Autowired
    private CustomInfoService customInfoService;
	 
	private static final Logger LOGGER = LogManager.getLogger(CustomInfoController.class);

	/** 
	* Class constructor.
	* @param customInfoService
	*/
    public CustomInfoController(CustomInfoService customInfoService) {
        this.customInfoService = customInfoService;
    }
    
    /**
     * Get a list of person, nb of adults and nb of children by station number.
     * 
     * @param station the station number
     * @return ResponseEntity containing a list of person, nb of adults and nb of children
     */
    @GetMapping("/firestation")
    public ResponseEntity<Object> getPersonsByStation(@RequestParam(name = "stationNumber") String station) throws Exception {           	
    	PersonWithNbOfAdultsAndChildrenDTO personWithNbOfAdultsAndChildren = new PersonWithNbOfAdultsAndChildrenDTO();
    	personWithNbOfAdultsAndChildren = customInfoService.getPersonsByStation(station);
    	
    	if(personWithNbOfAdultsAndChildren != null){
        	LOGGER.info("Succesfully got the nb of adult:" + personWithNbOfAdultsAndChildren.getNbOfAdults() + " the nb of children: " + personWithNbOfAdultsAndChildren.getNbOfChildren() + "and the list of person" + personWithNbOfAdultsAndChildren.getPersonInfo());
    		return new ResponseEntity<>(personWithNbOfAdultsAndChildren, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed to get persons by station" + station);
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }
    
    /**
     * Get a list of children and list of adults by address.
     * 
     * @param address the address of station/person
     * @return ResponseEntity containing a list of children and list of adults
     */
    @GetMapping("/childAlert")
    public ResponseEntity<Object> getChildrenByAdress(@RequestParam(name = "address") String address) {      
    	AdultAndChildDTO aduldAndChild = new AdultAndChildDTO();
    	aduldAndChild = customInfoService.getChildrenByAddress(address);
    	
    	if(aduldAndChild != null){
        	LOGGER.info("Succesfully got list of children and adults:" + aduldAndChild);
    		return new ResponseEntity<>(aduldAndChild, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed to get persons by " + address);
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }
    
    /**
     * Get a list of phone numbers by station number.
     * 
     * @param firestation the station number
     * @return ResponseEntity containing a list of phone numbers
     */
    @GetMapping("/phoneAlert")
    public ResponseEntity<Object> getPhoneNumbersByStation(@RequestParam(name = "firestation") String firestation) {
    	HashSet<String> listOfPhoneNb = customInfoService.getPhoneNumbersByStation(firestation);
    	
    	if(!listOfPhoneNb.isEmpty()){
        	LOGGER.info("Succesfully got list of phone numbers:" + listOfPhoneNb);
    		return new ResponseEntity<>(listOfPhoneNb, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed to get phone numbers for station" + firestation);
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }
    
    /**
     * Get a list of person with their medical records and station number by address.
     * 
     * @param address the station address
     * @return ResponseEntity containing a list of person with medical record and station number
     */ 
    @GetMapping("/fire")
    public ResponseEntity<Object> getPersonsMedicalRecordsAndStationByAddress(@RequestParam(name = "address") String address) {       
    	FamilyAndStationDTO familyAndStation = new FamilyAndStationDTO();
    	familyAndStation = customInfoService.getPersonsMedicalRecordsAndStationByAddress(address);
    	
    	if(familyAndStation != null){
        	LOGGER.info("Succesfully got list of person with medical record:" + familyAndStation);
    		return new ResponseEntity<>(familyAndStation, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed to get the person of this station:" + address);
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }
    
    /**
     * Get a list of families by a list of station number.
     * 
     * @param listOfStation list of station number
     * @return ResponseEntity containing a list of families
     */ 
    @GetMapping("/flood/station")
    public ResponseEntity<Object> getFamilyByStation(@RequestParam(name = "stations") List<String> listOfStation) {
    	List<FamiliesAndStationDTO> familiesAndStation = customInfoService.getFamilyByStation(listOfStation);
    	
    	if(!familiesAndStation.isEmpty()){
        	LOGGER.info("Succesfully got list of families:" + familiesAndStation);
    		return new ResponseEntity<>(familiesAndStation, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed to get the families of those stations:" + listOfStation);
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }

    /**
     * Get a list of person with medical record by name.
     * 
     * @param firstName the person's firstname
     * @param lastName the person's lastname
     * @return ResponseEntity containing a list of person with medical record
     */ 
    @GetMapping("/personInfo")
    public ResponseEntity<Object> getPersonAndMedicalRecord(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {       
    	List<PersonAndMedicalRecordDTO> personAndMedicalRecord = customInfoService.getPersonAndMedicalRecord(firstName, lastName);
    	
    	if(!personAndMedicalRecord.isEmpty()){
        	LOGGER.info("Succesfully got person with medical record:" + personAndMedicalRecord);
    		return new ResponseEntity<>(personAndMedicalRecord, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed to get the medical record for :" + firstName);
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }
    
    /**
     * Get a list of emails by city.
     * 
     * @param city city of person
     * @return ResponseEntity containing a list of emails
     */ 
    @GetMapping("/communityEmail")
    public ResponseEntity<Object> getAllEmailsByCity(@RequestParam(name = "city") String city) {
    	HashSet<String> emails = customInfoService.getAllEmailsByCity(city);
    	
    	if(!emails.isEmpty()){
        	LOGGER.info("Succesfully got list of emails :" + emails);
    		return new ResponseEntity<>(emails, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed to get the emails in " + city);
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }
}

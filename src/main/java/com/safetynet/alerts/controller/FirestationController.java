package com.safetynet.alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.DTO.EmptyJsonDTO;
import com.safetynet.alerts.service.FirestationService;

@RestController
public class FirestationController {
	
	@Autowired
    private FirestationService firestationService;
	private static final Logger LOGGER = LogManager.getLogger(FirestationController.class);

	/**
	* Class constructor.
	* @param firestationService
	*/
    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    /**
     * Request POST : create a new firestation.
     * 
     * @param firestation the firestation to create
     * @return ResponseEntity containing new firestation and an HTTP status code
     */
    @PostMapping("/firestation")
    public ResponseEntity<Object> createFirestation(@RequestBody Firestation firestation){
    	LOGGER.debug("Request POST: Creating a new firestation");
    	firestationService.addFirestation(firestation);
    	
    	if(firestation != null){
        	LOGGER.info("Firestation is added :" + firestation);
    		return new ResponseEntity<>(firestation, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed add the firestation :"  + firestation);
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }

    /**
     * Request DELETE : delete a firestation by station and/or adress.
     * 
     * @param station the station number
     * @param address the station address
     * @return ResponseEntity containing firestation deleted and an HTTP status code
     */
    @DeleteMapping("/firestation")
    public ResponseEntity<Object> deleteFirestationFromNumber(@RequestParam(name = "station", required = false) String station, @RequestParam(name = "address", required = false) String address) {    
    	LOGGER.debug("Request DELETE: Deleting a firestation");
    	List<Firestation> deletedFirestation= firestationService.deleteFirestation(address, station);
    	
    	if(!deletedFirestation.isEmpty()){
        	LOGGER.info("Firestation deleted :" + deletedFirestation);
    		return new ResponseEntity<>(deletedFirestation, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed to delete the firestation");
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }
    
    /**
     * Request PUT : udapte a firestation by address.
     * 
     * @param firestation the udapted firestation 
     * @param address the station address
     * @return ResponseEntity containing firestation udapted and an HTTP status code
     */
    @PutMapping("/firestation")
    public ResponseEntity<Object> udapteFirestation(@RequestBody Firestation firestation, @RequestParam(name = "address") String address) {  
    	LOGGER.debug("Request PUT: Udapting " + firestation.getStation());
    	Firestation firestationUdapted = firestationService.udapteFirestation(firestation, address);
    	
    	if(firestationUdapted != null){
        	LOGGER.info("Firestation is updated :" + firestation);
    		return new ResponseEntity<>(firestation, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed to update the firestation :"  + firestation);
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }

}
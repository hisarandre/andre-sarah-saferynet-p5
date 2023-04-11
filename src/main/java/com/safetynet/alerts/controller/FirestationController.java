package com.safetynet.alerts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.FirestationService;

@RestController
public class FirestationController {
	
	@Autowired
    private FirestationService firestationService;
	//private static final Logger logger = LogManager.getLogger(PersonController.class);

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    /*@GetMapping("/firestation")
    public List<Firestation> getAllFirestations(){
        return firestationService.getAllFirestations();
    }*/
    
    @PostMapping("/firestation")
    public void createFirestation(@RequestBody Firestation firestation){
    	firestationService.addFirestation(firestation);
    }

    @DeleteMapping("/firestation")
    public void deleteFirestationFromNumber(@RequestParam(name = "station", required = false) String station, @RequestParam(name = "address", required = false) String address) {    
    	firestationService.deleteFirestation(address, station);
    }
    
    
    @PutMapping("/firestation")
    public void udapteFirestation(@RequestBody Firestation firestation, @RequestParam(name = "address") String address) {  
    	firestationService.udapteFirestation(firestation, address);
    }

}
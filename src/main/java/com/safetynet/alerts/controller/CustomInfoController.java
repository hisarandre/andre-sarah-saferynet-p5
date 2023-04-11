package com.safetynet.alerts.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.DTO.AdultAndChildDTO;
import com.safetynet.alerts.model.DTO.FamilyAndStationDTO;
import com.safetynet.alerts.model.DTO.FamilyDTO;
import com.safetynet.alerts.model.DTO.PersonAndMedicalRecordDTO;
import com.safetynet.alerts.model.DTO.PersonWithNbOfAdultsAndChildrenDTO;
import com.safetynet.alerts.service.CustomInfoService;

@RestController
public class CustomInfoController {
	
	@Autowired
    private CustomInfoService customInfoService;
	//private static final Logger logger = LogManager.getLogger(PersonController.class);

    public CustomInfoController(CustomInfoService customInfoService) {
        this.customInfoService = customInfoService;
    }
    
    @GetMapping("/firestation")
    public PersonWithNbOfAdultsAndChildrenDTO getPersonsByStation(@RequestParam(name = "stationNumber") String station) {       
    	return customInfoService.getPersonsByStation(station);
    }
    
    @GetMapping("/childAlert")
    public AdultAndChildDTO getChildrenByAdress(@RequestParam(name = "address") String address) {       
    	return customInfoService.getChildrenByAddress(address);
    }
    
    @GetMapping("/phoneAlert")
    public HashSet<String> getPhoneNumbersByStation(@RequestParam(name = "firestation") String firestation) {       
    	return customInfoService.getPhoneNumbersByStation(firestation);
    }
    
    @GetMapping("/fire")
    public FamilyAndStationDTO getPersonsMedicalRecordsAndStationByAddress(@RequestParam(name = "address") String address) {       
    	return customInfoService.getPersonsMedicalRecordsAndStationByAddress(address);
    }
    
    @GetMapping("/flood/station")
    public List<FamilyDTO> getFamilyByStation(@RequestParam(name = "stations") String listOfStation) {       
    	return customInfoService.getFamilyByStation(listOfStation);
    }


    @GetMapping("/personInfo")
    public List<PersonAndMedicalRecordDTO> getPersonAndMedicalRecord(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {       
        return customInfoService.getPersonAndMedicalRecord(firstName, lastName);
    }
}

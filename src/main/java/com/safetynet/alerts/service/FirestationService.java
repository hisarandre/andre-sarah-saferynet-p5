package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.FirestationRepository;

@Service
public class FirestationService {

    @Autowired
    FirestationRepository firestationRepository;

    public FirestationService(FirestationRepository firestationRepository) {
        this.firestationRepository = firestationRepository;
    }

    public List<Firestation> getAllFirestations(){
        return firestationRepository.getAllFirestations(); 
    }
    
    public void addFirestation(Firestation firestation){
        firestationRepository.createFirestation(firestation);
    }
    
    public List<Firestation> deleteFirestation(String address, String station){
        return firestationRepository.deleteFirestation(address, station);
    }
    
    public Firestation udapteFirestation(Firestation firestation, String address) {
    	return firestationRepository.udapteFirestation(firestation, address);
    }
    
    public List<String> findAdressesByStation(String station) {
    	List<String> addressesByStation = new ArrayList<>();

    	List<Firestation> firestationsList = firestationRepository.getAllFirestations();
    		    	
	    for(Firestation f : firestationsList) {
			if((f.getStation().equals(station))) {
				addressesByStation.add(f.getAddress());
			}
		}
    	
	    return addressesByStation;
    }
    
    public String findStationByAddress(String address) {
    	List<Firestation> firestationsList = firestationRepository.getAllFirestations();
    	
    	for(Firestation f : firestationsList) {
			if((f.getAddress().equals(address))) {
				return f.getStation();
			}
		} 
    	return null;
    }
}

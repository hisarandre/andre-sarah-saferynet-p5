package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.Firestation;


@Repository
public class FirestationRepository {

    @Autowired
    private Data data;

    public List<Firestation> getAllFirestations() {
        return data.getFirestations();
    }
    
    public void createFirestation(Firestation firestation) {
        data.getFirestations().add(firestation); 
    }
    
    public void deleteFirestation(String address, String station) {
    	List<Firestation> firestationsList = data.getFirestations();
    	List<Firestation> found = new ArrayList<Firestation>();
      	
    	// delete only the address with the linked station
    	if(address != null) {
    		for(Firestation f : firestationsList) {
    			if((f.getAddress().equals(address))) {
    				data.getFirestations().remove(f);
    				break;
    			}
    		}
    	// delete all address linked to the station
    	}else if(station != null && address == null){
    		for(Firestation f : firestationsList) {
    			if((f.getStation().equals(station))) {
    				found.add(f);
    			}
    		} 
    		data.getFirestations().removeAll(found);
    	}
    
    }
    
    public void udapteFirestation(Firestation firestation, String address) {
    	List<Firestation> firestationsList = data.getFirestations();

    	for(Firestation f : firestationsList){
    		if(f.getStation().equals(firestation.getStation())) {
        		f.setAddress(address);
        		break;
    		}
    	}
        
    }
}

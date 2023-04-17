package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.Firestation;


@Repository
public class FirestationRepository {

    @Autowired
    private Data data;

	private static final Logger LOGGER = LogManager.getLogger(FirestationRepository.class);

	
    /**
     * Retrieves all firestations.
     *
     * @return the list of all firestations
     */
    public List<Firestation> getAllFirestations(){ 
    	List<Firestation> firestations = new ArrayList<>();

        firestations = data.getFirestations();

        return firestations;
    }

    /**
     * Creates a new firestation.
     *
     * @param firestation the firestation to create
     */
    public void createFirestation(Firestation firestation) {
    	data.getFirestations().add(firestation);
    }

    /**
     * Deletes a firestation by address or station.
     *
     * @param address the address to delete (if provided)
     * @param station the station to delete all addresses for (if provided)
     */
    public List<Firestation> deleteFirestation(String address, String station) {
    	LOGGER.debug("Deleting firestation with address or station", address, station);
	    List<Firestation> firestationsList = data.getFirestations();
	    List<Firestation> found = new ArrayList<Firestation>();
    	
	    // delete only the address with the linked station
	    if(address != null) {
	    	for(Firestation f : firestationsList) {
	    		if((f.getAddress().equals(address))) {
	    			found.add(f);
	    		}
	    	}
	    // delete all addresses linked to the station
	    } else if(station != null && address == null){
	    	for(Firestation f : firestationsList) {
	    		if((f.getStation().equals(station))) {
	    			found.add(f);
	    		}
	    	} 
	    }
	    
    	data.getFirestations().removeAll(found);
    	return found;
    }

    /** 
     * Updates the address for a firestation with the specified station.
     *
     * @param firestation the firestation with the new address
     * @param address the current address of the firestation
     */
    public Firestation udapteFirestation(Firestation firestation, String address) {    	
	    List<Firestation> firestationsList = data.getFirestations();
	
	    for(Firestation f : firestationsList){
	    	if(f.getStation().equals(firestation.getStation())) {
	        	f.setAddress(address);
	        	return f;
	    	}
	    }
		return firestation;
    }
}
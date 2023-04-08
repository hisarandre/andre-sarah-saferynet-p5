package com.safetynet.alerts.service;

import java.util.List;
import java.util.Optional;

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
    
    public void deleteFirestation(String address, String station){
        firestationRepository.deleteFirestation(address, station);
    }
    
    public void udapteFirestation(Firestation firestation, String address) {
    	firestationRepository.udapteFirestation(firestation, address);
    }
    
}

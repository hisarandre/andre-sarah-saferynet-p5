package com.safetynet.alerts.model;

import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class Data {

    public static List<Person> persons;
    public static List<Firestation> firestations;
    public static List <MedicalRecord> medicalrecords;
 
    
    public List<Person> getPersons() {
        return persons; 
    }

    public void setPersons(List<Person> persons) {
        Data.persons = persons; 
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }

    public void setFirestations(List<Firestation> firestations) {
        Data.firestations = firestations;
    }

    public List<MedicalRecord> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
        Data.medicalrecords = medicalrecords;
    }

    @Override
    public String toString() {
        return "data{" +
                "persons=" + persons +
                ", firestations=" + firestations +
                ", medicalrecords=" + medicalrecords +
                '}';
    }
	
}

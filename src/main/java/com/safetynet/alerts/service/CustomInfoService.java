package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.DTO.AdultAndChildDTO;
import com.safetynet.alerts.model.DTO.FamilyAndStationDTO;
import com.safetynet.alerts.model.DTO.FamilyDTO;
import com.safetynet.alerts.model.DTO.PersonAdressPhoneDTO;
import com.safetynet.alerts.model.DTO.PersonAgeDTO;
import com.safetynet.alerts.model.DTO.PersonAndMedicalRecordDTO;
import com.safetynet.alerts.model.DTO.PersonNameDTO;
import com.safetynet.alerts.model.DTO.PersonPhoneAgeMedicalRecordDTO;
import com.safetynet.alerts.model.DTO.PersonWithNbOfAdultsAndChildrenDTO;
import com.safetynet.alerts.repository.FirestationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;

@Service
public class CustomInfoService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private FirestationRepository firestationRepository;
    
    public PersonWithNbOfAdultsAndChildrenDTO getPersonsByStation(String station){
    	List<PersonAdressPhoneDTO> personsInfoList = new ArrayList<>();
    	List<Person> personsList = personRepository.getAllPersons();
    	List<MedicalRecord> medicalRecordsList = medicalRecordRepository.getAllMedicalRecords();
    	
    	int nbOfAdults = 0;
    	int nbOfChildren = 0;
    	
        List<String> addressesByStation = firestationRepository.findAdressesByStation(station);

    	
        for (String address : addressesByStation) {
            for (Person person : personsList) {
                if (address.equals(person.getAddress())) {

                	PersonAdressPhoneDTO newPerson = new PersonAdressPhoneDTO();
                	newPerson.setFirstName(person.getFirstName());
                	newPerson.setLastName(person.getLastName());
                	newPerson.setAddress(person.getAddress());
                	newPerson.setPhone(person.getPhone());
                	personsInfoList.add(newPerson);
                    
                    for (MedicalRecord medicalRecord : medicalRecordsList) {
                    	if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
                    		
                    		String birthdate = medicalRecord.getBirthdate();
                    		int age = medicalRecordRepository.calculateAge(birthdate);
                    		
                    		if (age < 18) {
                    			nbOfChildren++;
                            } else {
                            	nbOfAdults++;
                            }
                        }
                    }
                }
        	}
        }
        
        PersonWithNbOfAdultsAndChildrenDTO result = new PersonWithNbOfAdultsAndChildrenDTO();
        result.setPersonInfo(personsInfoList);
        result.setNbOfAdults(nbOfAdults);
        result.setNbOfChildren(nbOfChildren);
        
    	return result;
    }
    
    public AdultAndChildDTO getChildrenByAddress(String address){
    	List<Person> personsList = personRepository.findByAddress(address);
    	List<MedicalRecord> medicalRecordsList = medicalRecordRepository.getAllMedicalRecords();
    	
    	List<PersonAgeDTO> children = new ArrayList<>();
    	List<PersonNameDTO> adults = new ArrayList<>();
    	
        for (Person person : personsList) {
        	for (MedicalRecord medicalRecord : medicalRecordsList) {
            	if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
            		
            		String birthdate = medicalRecord.getBirthdate();
            		int age = medicalRecordRepository.calculateAge(birthdate);
            		
            		if (age <= 18) {
            			PersonAgeDTO child = new PersonAgeDTO();
            			child.setFirstName(person.getFirstName());
            			child.setLastName(person.getLastName());
            			child.setAge(age);
            			children.add(child);
                    } else {
                    	PersonNameDTO adult = new PersonNameDTO();
                    	adult.setFirstName(person.getFirstName());
                    	adult.setLastName(person.getLastName());
            			adults.add(adult);
                    }
                }
            }
        }
        
        AdultAndChildDTO result = new AdultAndChildDTO();
    	result.setChildren(children);
    	result.setAdults(adults);
    	return result;
    }
    
    public HashSet<String> getPhoneNumbersByStation(String firestation){
    	HashSet<String> phoneNumbers = new HashSet<String>();
        List<String> addressesByStation = firestationRepository.findAdressesByStation(firestation);
    	List<Person> personsList = personRepository.getAllPersons();

        for (String address : addressesByStation) {
            for (Person person : personsList) {
                if (address.equals(person.getAddress())) {
    				phoneNumbers.add(person.getPhone());
                }
			}
		}
    	return phoneNumbers;    	
    }
    
    public FamilyAndStationDTO getPersonsMedicalRecordsAndStationByAddress(String address){	
    	String station = firestationRepository.findStationByAddress(address);
    	List<Person> personsList = personRepository.findByAddress(address);
    	List<MedicalRecord> medicalRecordsList = medicalRecordRepository.getAllMedicalRecords();
    	
    	
    	List<PersonPhoneAgeMedicalRecordDTO> membersOfFamily = new ArrayList<>();
    	
        for (Person person : personsList) {
        	for (MedicalRecord medicalRecord : medicalRecordsList) {
            	if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
            		
            		String birthdate = medicalRecord.getBirthdate();
            		int age = medicalRecordRepository.calculateAge(birthdate);
            		
            		PersonPhoneAgeMedicalRecordDTO newMember = new PersonPhoneAgeMedicalRecordDTO();
            		newMember.setFirstName(person.getFirstName());
            		newMember.setLastName(person.getLastName());
            		newMember.setAge(age);
            		newMember.setMedications(medicalRecord.getMedications());
            		newMember.setAllergies(medicalRecord.getAllergies());
            		
            		membersOfFamily.add(newMember);
                }
            }
        }
    	
    	FamilyAndStationDTO result = new FamilyAndStationDTO();
    	result.setStation(station);
    	result.setFamily(membersOfFamily);
    	return result;
    }
    
    public List<FamilyDTO> getFamilyByStation(String listOfStation){
    
	List<MedicalRecord> medicalRecordsList = medicalRecordRepository.getAllMedicalRecords();
    List<String> addressesByStation = firestationRepository.findAdressesByStation(listOfStation);
    List<FamilyDTO> allFamilies = new ArrayList<>();
	
    for (String address : addressesByStation) {
    	List<Person> personsListByAddress = personRepository.findByAddress(address);
    	FamilyDTO newFamily = new FamilyDTO();
    	List<PersonPhoneAgeMedicalRecordDTO> allFamilyMembers = new ArrayList<>();
    	
        for (Person person : personsListByAddress) {
        	PersonPhoneAgeMedicalRecordDTO newMember = new PersonPhoneAgeMedicalRecordDTO();
        	newMember.setFirstName(person.getFirstName());
        	newMember.setLastName(person.getLastName());
        	
        	for (MedicalRecord medicalRecord : medicalRecordsList) {
            	if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
            		String birthdate = medicalRecord.getBirthdate();
            		int age = medicalRecordRepository.calculateAge(birthdate);
            		
            		newMember.setAge(age);
            		newMember.setMedications(medicalRecord.getMedications());
            		newMember.setAllergies(medicalRecord.getAllergies());
                }
            }
        	
            allFamilyMembers.add(newMember);
        }
        newFamily.setFamily(allFamilyMembers);
        allFamilies.add(newFamily);
    }    
	return allFamilies;
    
    }
    
    
    public List<PersonAndMedicalRecordDTO> getPersonAndMedicalRecord(String firstName, String lastName){
       
    	List<Person> personFound = personRepository.findByName(firstName, lastName);
    	List<MedicalRecord> medicalRecordFound = medicalRecordRepository.findByName(firstName, lastName);
    	
    	List<PersonAndMedicalRecordDTO> result = new ArrayList<>();

    	for (int i = 0; i < personFound.size(); ++i){
  
    		PersonAndMedicalRecordDTO newPerson = new PersonAndMedicalRecordDTO();

    		newPerson.setLastName(personFound.get(i).getLastName());
    		newPerson.setFirstName(personFound.get(i).getFirstName());
    		newPerson.setAddress(personFound.get(i).getAddress());
    		newPerson.setEmail(personFound.get(i).getEmail());
    		
    		String birthdate = medicalRecordFound.get(i).getBirthdate();
    		int age = medicalRecordRepository.calculateAge(birthdate);
    		newPerson.setAge(age);
    		
    		newPerson.setMedications(medicalRecordFound.get(i).getMedications());
	    	newPerson.setAllergies(medicalRecordFound.get(i).getAllergies());
	    		
    		result.add(newPerson);
    	}
    	
    	return result;
    }
}

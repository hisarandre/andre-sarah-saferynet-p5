package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.DTO.AdultAndChildDTO;
import com.safetynet.alerts.model.DTO.FamiliesAndStationDTO;
import com.safetynet.alerts.model.DTO.FamilyAndStationDTO;
import com.safetynet.alerts.model.DTO.FamilyDTO;
import com.safetynet.alerts.model.DTO.PersonAdressPhoneDTO;
import com.safetynet.alerts.model.DTO.PersonAgeDTO;
import com.safetynet.alerts.model.DTO.PersonAndMedicalRecordDTO;
import com.safetynet.alerts.model.DTO.PersonNameDTO;
import com.safetynet.alerts.model.DTO.PersonPhoneAgeMedicalRecordDTO;
import com.safetynet.alerts.model.DTO.PersonWithNbOfAdultsAndChildrenDTO;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;

@Service
public class CustomInfoService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private MedicalRecordService medicalRecordService;
    
    @Autowired
    private FirestationService firestationService;
    
    
    /**
     * Retrieves persons and the number of adults and children living at their address for a given fire station.
     * 
     * @param station the station number to retrieve information for
     * @return a PersonWithNbOfAdultsAndChildrenDTO object containing the list of persons and the number of adults and children
     */
    public PersonWithNbOfAdultsAndChildrenDTO getPersonsByStation(String station){
    	List<PersonAdressPhoneDTO> personsInfoList = new ArrayList<>();
    	List<Person> personsList = personRepository.getAllPersons();
    	List<MedicalRecord> medicalRecordsList = medicalRecordRepository.getAllMedicalRecords();
    	
    	int nbOfAdults = 0;
    	int nbOfChildren = 0;
    	
        List<String> addressesByStation = firestationService.findAdressesByStation(station);

    	
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
                    		int age = medicalRecordService.calculateAge(birthdate);
                    		
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
    
    /**
     * Retrieves a list of children and adults living at the specified address.
     * 
     * @param address the address to search for
     * @return an AdultAndChildDTO object containing a list of PersonAgeDTO objects representing children
     * and a list of PersonNameDTO objects representing adults
    */
    public AdultAndChildDTO getChildrenByAddress(String address){
    	List<Person> personsList = personRepository.findByAddress(address);
    	List<MedicalRecord> medicalRecordsList = medicalRecordRepository.getAllMedicalRecords();
    	
    	List<PersonAgeDTO> children = new ArrayList<>();
    	List<PersonNameDTO> adults = new ArrayList<>();
    	
        for (Person person : personsList) {
        	for (MedicalRecord medicalRecord : medicalRecordsList) {
            	if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
            		
            		String birthdate = medicalRecord.getBirthdate();
            		int age = medicalRecordService.calculateAge(birthdate);
            		
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
    
    /**
     * Returns a set of phone numbers of all the persons linked to a given fire station.
     * 
     *  @param firestation The number of the fire station.
     *  @return A HashSet containing the phone numbers of all the persons linked to the given fire station.
    */
    public HashSet<String> getPhoneNumbersByStation(String firestation){
    	HashSet<String> phoneNumbers = new HashSet<String>();
        List<String> addressesByStation = firestationService.findAdressesByStation(firestation);
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
    
    /**
     *  Retrieves the list of persons living at the specified address, along with their medical records and the station
     *  serving the address.
     *  
     *  @param address the address to retrieve information for
     *  @return a list of object containing the list of family members and the fire station serving the address
    */
    public FamilyAndStationDTO getPersonsMedicalRecordsAndStationByAddress(String address){	
    	String station = firestationService.findStationByAddress(address);
    	List<Person> personsList = personRepository.findByAddress(address);
    	List<MedicalRecord> medicalRecordsList = medicalRecordRepository.getAllMedicalRecords();
    	
    	
    	List<PersonPhoneAgeMedicalRecordDTO> membersOfFamily = new ArrayList<>();
    	
        for (Person person : personsList) {
        	for (MedicalRecord medicalRecord : medicalRecordsList) {
            	if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
            		
            		String birthdate = medicalRecord.getBirthdate();
            		int age = medicalRecordService.calculateAge(birthdate);
            		
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
    

    public List<FamiliesAndStationDTO> getFamilyByStation(List<String> listOfStation){
    
		List<MedicalRecord> medicalRecordsList = medicalRecordRepository.getAllMedicalRecords();
		List<FamiliesAndStationDTO> result = new ArrayList<>();
		
	    for (String station : listOfStation) {
	    	List<String> addressesByStation = firestationService.findAdressesByStation(station);
	    	FamiliesAndStationDTO newFamiliesAndStation = new FamiliesAndStationDTO();
	    	List<FamilyDTO> allFamilies = new ArrayList<>();
	    	newFamiliesAndStation.setStation(station);
	    	
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
		            		int age = medicalRecordService.calculateAge(birthdate);
		            		
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
		    newFamiliesAndStation.setFamilies(allFamilies);
		    result.add(newFamiliesAndStation);
	    }
		return result;
    }
   
    /**
     * Retrieves all families living in the specified list of stations, along with their members' medical records
     * @param listOfStation A list of station numbers for which to retrieve the families.
     * @return A list of FamiliesAndStationDTO objects, each containing the station number and a list of families living in that station.
	*/
    
    public List<PersonAndMedicalRecordDTO> getPersonAndMedicalRecord(String firstName, String lastName){
       
    	List<Person> personFound = personRepository.findByName(firstName, lastName);
    	List<MedicalRecord> medicalRecordFound = medicalRecordRepository.findByName(firstName, lastName);
    	
    	List<PersonAndMedicalRecordDTO> result = new ArrayList<>();

        for (Person person : personFound) {
        	for (MedicalRecord medicalRecord : medicalRecordFound) {
            	if (medicalRecord.getFirstName().equals(person.getFirstName()) && medicalRecord.getLastName().equals(person.getLastName())) {
            		
		    		PersonAndMedicalRecordDTO newPerson = new PersonAndMedicalRecordDTO();
		
		    		newPerson.setLastName(person.getLastName());
		    		newPerson.setFirstName(person.getFirstName());
		    		newPerson.setAddress(person.getAddress());
		    		newPerson.setEmail(person.getEmail());
		    		
		    		String birthdate = medicalRecord.getBirthdate();
		    		int age = medicalRecordService.calculateAge(birthdate);
		    		newPerson.setAge(age);
		    		
		    		newPerson.setMedications(medicalRecord.getMedications());
			    	newPerson.setAllergies(medicalRecord.getAllergies());
			    		
		    		result.add(newPerson);
            	}
        	}
        }
    	return result;
    }

    /**
     * Returns a set of unique email addresses for all persons living in the given city.
     * 
     * @param city the name of the city to search for
     * @return a HashSet of email addresses for all persons living in the given city, or an empty set if no matches are found
    */
    public HashSet<String> getAllEmailsByCity(String city) {
    	HashSet<String> emails = new HashSet<String>();
    	List<Person> personsList = personRepository.getAllPersons();
    	
    	for(Person p : personsList) {
			if((p.getCity().equals(city))) {
				emails.add(p.getEmail());
			}
		}
    	return emails;
    }
}

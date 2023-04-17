package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.Data;

@Repository
public class PersonRepository {

    @Autowired
    private Data data;

    /**
     * Retrieves a list of all persons.
     * 
     * @return A List of Person objects representing all the persons stored in the data model.
     */
    public List<Person> getAllPersons() {
        return data.getPersons();
    }
    
    /**
     * Adds a new person to the list of persons.
     * 
     * @param person The Person object to be added to the list.
     */
    public void createPerson(Person person) {
        data.getPersons().add(person); 
    }
    
    /**
     * Deletes all persons with the specified first name and last name from the list of persons.
     * @param firstName The first name of the persons to be deleted.
     * @param lastName The last name of the persons to be deleted.
     * @return list of person found matching firstname and lastname and deleted.
     */
    public List<Person> deletePerson(String firstName, String lastName) {
    	List<Person> person = findByName(firstName, lastName);    	
        data.getPersons().removeAll(person);
        
        return person;
    }
    
    /**
     * Updates the details of the person(s) with the specified first name and last name.
     * 
     * @param person The updated Person object.
     * @param firstName The first name of the person(s) to be updated.
     * @param lastName The last name of the person(s) to be updated.
     * @return person udapted.
     */
    public Person udaptePerson(Person person, String firstName, String lastName) {
    	List<Person> personsList = data.getPersons();
    	
    	String newAddress = person.getAddress();
    	String newCity = person.getCity();
    	String newEmail = person.getEmail();
    	String newZip = person.getZip();
    	String newPhone = person.getPhone();

    	for(Person p : personsList) {			
    		if((p.getFirstName().equals(firstName)) && (p.getLastName()).equals(lastName)) {
     	    	
    	    	if(newAddress != null) p.setAddress(newAddress);
    	    	if(newCity != null) p.setCity(newCity);
    	    	if(newEmail != null) p.setEmail(newEmail);
    	    	if(newZip != null) p.setZip(newZip);
    	    	if(newPhone != null) p.setPhone(newPhone);
    	    	
    	    	return p;
            }
    	}
		return null;
    }
    
    /**
     * Finds all persons with the specified first name and last name.
     * 
     * @param firstName The first name of the persons to be found.
     * @param lastName The last name of the persons to be found.
     * @return A List of Person objects representing all the persons with the specified first name and last name.
     */
    public List<Person> findByName(String firstName, String lastName) {
    	List<Person> personsList = data.getPersons();
    	List<Person> found = new ArrayList<>();
    	
    	for(Person p : personsList) {
    		if((p.getFirstName().equals(firstName)) && (p.getLastName()).equals(lastName)) {
    			found.add(p);
            }
    	}
		return found; 
    }
    
    /**
     * Finds all persons with the specified address.
     * 
     * @param address The address of the persons to be found.
     * @return A List of Person objects representing all the persons with the specified address.
     */
    public List<Person> findByAddress(String address) {
    	List<Person> personsList = data.getPersons();
    	List<Person> found = new ArrayList<>();
    	
    	for(Person p : personsList) {
    		if((p.getAddress().equals(address))) {
    			found.add(p);
            }
    	}
		return found;
    }
    
}

package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.Data;

@Repository
public class PersonRepository {

    @Autowired
    private Data data;

    public List<Person> getAllPersons() {
        return data.getPersons();
    }
    
    public void createPerson(Person person) {
        data.getPersons().add(person); 
    }
    
    public void deletePerson(String firstName, String lastName) {
    	 List<Person> person = findByName(firstName, lastName);    	
        data.getPersons().removeAll(person);
    }
    
    public void udaptePerson(Person person, String firstName, String lastName) {
    	List<Person> personsList = data.getPersons();
    	
    	for(Person p : personsList) {
    		if((p.getFirstName().equals(firstName)) && (p.getLastName()).equals(lastName)) {
                p.setAddress(person.getAddress());
                p.setCity(person.getCity());
                p.setZip(person.getZip());
                p.setPhone(person.getPhone());
                p.setEmail(person.getEmail());
            }
    	}
    }
    
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
    
    public HashSet<String> getAllEmailsByCity(String city) {
    	HashSet<String> emails = new HashSet<String>();
    	List<Person> personsList = data.getPersons();
    	
    	for(Person p : personsList) {
			if((p.getCity().equals(city))) {
				emails.add(p.getEmail());
			}
		}
    	return emails;
    }
    
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

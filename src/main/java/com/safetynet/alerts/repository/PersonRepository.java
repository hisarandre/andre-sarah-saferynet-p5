package com.safetynet.alerts.repository;

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
    	Person person = findByName(firstName, lastName);    	
        data.getPersons().remove(person);
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
    
    public Person findByName(String firstName, String lastName) {
    	List<Person> personsList = data.getPersons();
    	
    	for(Person p : personsList) {
    		if((p.getFirstName().equals(firstName)) && (p.getLastName()).equals(lastName)) {
    			return p;
            }
    	}
		return null;
    }
    
}

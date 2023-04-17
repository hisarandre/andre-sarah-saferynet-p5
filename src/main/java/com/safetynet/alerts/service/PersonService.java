package com.safetynet.alerts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons(){
        return personRepository.getAllPersons(); 
    }
    
    public void addPerson(Person person){
        personRepository.createPerson(person);
    }
    
    public List<Person> deletePerson(String firstName, String lastName){
        return personRepository.deletePerson(firstName, lastName);
    }
    
    public Person udaptePerson(Person person, String firstName, String lastName){
        return personRepository.udaptePerson(person, firstName, lastName);
    	
    }
    
    
}
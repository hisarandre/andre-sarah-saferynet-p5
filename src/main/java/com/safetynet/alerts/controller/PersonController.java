package com.safetynet.alerts.controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

@RestController
public class PersonController {
	
	@Autowired
    private PersonService personService;
	//private static final Logger logger = LogManager.getLogger(PersonController.class);

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/person")
    public List<Person> getAllPersons(){
        return personService.getAllPersons();
    }
    
    @PostMapping("/person")
    public void createPerson(@RequestBody Person person){
    	personService.addPerson(person);
    }
    
    @DeleteMapping("/person")
    public void deletePerson(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {       
    	personService.deletePerson(firstName, lastName);
    }
    
    @PutMapping("/person")
    public void udaptePerson(@RequestBody Person person, @RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {       
    	personService.udaptePerson(person, firstName, lastName);
    }
    
    @GetMapping("/communityEmail")
    public HashSet<String> getAllEmailsByCity(@RequestParam(name = "city") String city) {       
    	return personService.getAllEmailsByCity(city);
    }
    
}
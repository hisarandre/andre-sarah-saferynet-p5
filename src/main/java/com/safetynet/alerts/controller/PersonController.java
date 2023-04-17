package com.safetynet.alerts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.safetynet.alerts.model.DTO.EmptyJsonDTO;
import com.safetynet.alerts.service.PersonService;

@RestController
public class PersonController {
	
	@Autowired
    private PersonService personService;
	
	private static final Logger LOGGER = LogManager.getLogger(PersonController.class);

	/**
	* Class constructor.
	* @param personService
	*/
    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    
    /** 
    * REQUEST GET : the list of each person.
    * 
    * @return ResponseEntity containing the list of person and an HTTP status code
    */
    @GetMapping("/person")
    public ResponseEntity<Object> getAllPersons(){
    	List<Person> personList = personService.getAllPersons();
        
    	if(!personList.isEmpty()){
        	LOGGER.info("Got all person");
    		return new ResponseEntity<>(personList, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed to get data");
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }
    
    /**
     * Request POST : create a new person.
     * 
     * @param person the person to create
     * @return ResponseEntity containing the added person and an HTTP status code
     */
    @PostMapping("/person")
    public ResponseEntity<Object> createPerson(@RequestBody Person person){
    	LOGGER.debug("Request POST: Creating a new person");
    	personService.addPerson(person);
    	
    	if(person != null){
        	LOGGER.info("Person is added");
    		return new ResponseEntity<>(person, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed to add :" + person);
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }
    
    /**
     * Request DELETE : delete a person by firstName and lastName.
     * 
     * @param firstName the person's firstname
     * @param lastName the person's lastname
     * @return ResponseEntity containing the deleted Person object and an HTTP status code
     */
    @DeleteMapping("/person")
    public ResponseEntity<Object> deletePerson(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {       
    	LOGGER.debug("Request DELETE: Deleting " + firstName + lastName);
    	List<Person> personDeleted = personService.deletePerson(firstName, lastName);
    	
    	if(!personDeleted.isEmpty()){
        	LOGGER.info("Person is deleted");
    		return new ResponseEntity<>(personDeleted, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed to delete :" + firstName);
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }
    
    /**
     * Request PUT : udapte a person by firstName and lastName.
     * 
     * @param person the udapted person's infos
     * @param firstName the person's firstname
     * @param lastName the person's lastname
     * @return ResponseEntity containing the updated Person object and an HTTP status code
     */
    @PutMapping("/person")
    public ResponseEntity<Object> udaptePerson(@RequestBody Person person, @RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {       
    	LOGGER.debug("Request PUT: Udapting " + firstName + lastName + "infos");
    	Person personUdapted = personService.udaptePerson(person, firstName, lastName);
    	
    	if(personUdapted != null){
        	LOGGER.info("Person is udapted");
    		return new ResponseEntity<>(personUdapted, HttpStatus.OK);
	    } else {
	        LOGGER.error("Failed to update :" + person);
	        return new ResponseEntity<>(new EmptyJsonDTO(),HttpStatus.NOT_FOUND);
	    }
    }
    
}
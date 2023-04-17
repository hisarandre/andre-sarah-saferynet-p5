package com.safetynet.alerts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

	@Autowired
	public MockMvc mvc;
	
	@MockBean
	private PersonService personService;
	
    @Test
    public void testGetPerson() throws Exception{
        List<Person> mockResponse = Arrays.asList(new Person(), new Person());		
		when(personService.getAllPersons()).thenReturn(mockResponse);		
		mvc.perform(get("/person")).andExpect(status().isOk());
    }
    
    @Test
    public void testGetAllPersonsWhenNoPersonFound() throws Exception {
        when(personService.getAllPersons()).thenReturn(Collections.emptyList());
        mvc.perform(get("/person"))
            .andExpect(status().isNotFound());
    }

	@Test
    public void testPostPerson() throws Exception{

        String newPerson = "{\"firstName\":\"jon\",\"lastName\":\"snow\",\"address\":\"une rue\",\"city\":\"une ville\",\"zip\":\"00000\",\"phone\":\"000-111-2222\",\"email\":\"js.stark@mail.com\"}";
        
		mvc.perform(post("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newPerson))
        .andExpect(status().isOk()); 
    }
	
	@Test
    public void testPostPersonIfNoContentSent() throws Exception{
        String empty = "";
        
		mvc.perform(post("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(empty))
        .andExpect(status().isBadRequest());
    }
	
	@Test
    public void testDeletePerson() throws Exception{
        List<Person> mockResponse = Arrays.asList(new Person(), new Person());

        String firstName = "John";
        String lastName = "Doe";
		
		when(personService.deletePerson(firstName,lastName)).thenReturn(mockResponse);
		
        mvc.perform(delete("/person").param("firstName", firstName).param("lastName", lastName)
        		.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk()); 
    }
	
	@Test
	public void testDeletePersonNotFound() throws Exception {
	    String firstName = "John";
	    String lastName = "Doe";
	    
	    when(personService.deletePerson(firstName, lastName)).thenReturn(new ArrayList<>());
	    
	    mvc.perform(delete("/person").param("firstName", firstName).param("lastName", lastName))
	       .andExpect(status().isNotFound());
	}
	
	@Test
	public void testPutPerson() throws Exception {
	    Person mockResponse = new Person();
	    mockResponse.setFirstName("John");
	    mockResponse.setLastName("Doe");
	    mockResponse.setAddress("une rue");
	    mockResponse.setCity("une ville");
	    mockResponse.setZip("00000");
	    mockResponse.setPhone("000-111-2222");
	    mockResponse.setEmail("js.stark@mail.com");

	    when(personService.udaptePerson(any(Person.class), eq("John"), eq("Doe"))).thenReturn(mockResponse);

	    String updatedPerson = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"address\":\"une rue\",\"city\":\"une ville\",\"zip\":\"00000\",\"phone\":\"000-111-2222\",\"email\":\"js.stark@mail.com\"}";

	    mvc.perform(put("/person")
	            .queryParam("firstName", "John")
	            .queryParam("lastName", "Doe")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(updatedPerson))
	            .andExpect(status().isOk());
	}
		
	@Test
	public void testUdaptePersonNotFound() throws Exception {
	    String firstName = "John";
	    String lastName = "Doe";
	    String requestBody = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"address\":\"9 rue grande\",\"city\":\"New York\",\"zip\":\"12345\",\"phone\":\"9876543210\",\"email\":\"johndoe@example.com\"}";

	    when(personService.udaptePerson(any(Person.class), eq(firstName), eq(lastName))).thenReturn(null);

	    mvc.perform(put("/person")
	            .param("firstName", firstName)
	            .param("lastName", lastName)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isNotFound());
	    }
}

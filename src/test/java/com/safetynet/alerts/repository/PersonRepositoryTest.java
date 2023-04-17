package com.safetynet.alerts.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.alerts.MockData;
import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.Person;

@SpringBootTest
public class PersonRepositoryTest {
	
    @Autowired
    private MockData mockData;
    
    @Autowired
    private Data data;
    
    @Autowired
    private PersonRepository personRepository;
    
    @Mock
    private DataRepository dataRepository;
    
    @BeforeEach()
    public void setUp() throws Exception{
    	data = mockData.getAllMockData();
        Mockito.when(dataRepository.read(anyString())).thenReturn(data);
    }
    
    @Test
    public void testGetAllPersons() throws Exception{
    	List<Person> listPersons= personRepository.getAllPersons();
        assertEquals(3, listPersons.size());
    }
    
    @Test
    public void testCreatePerson() throws Exception{
    	Person person = new Person();
    	person.setAddress("4 rue petite");
    	person.setCity("Lyon");
    	person.setEmail("Robin.i@gmail.com");
    	person.setPhone("076818811");
    	person.setZip("69000");
    	person.setFirstName("Robin");
    	person.setLastName("Im");
        
    	personRepository.createPerson(person);
    	List<Person> listPersons= data.getPersons();
        assertEquals(4, listPersons.size());
    }
    
    @Test
    public void testDeletePerson() throws Exception{
    	List<Person> listPersons= data.getPersons();
        assertEquals(listPersons.size(), 3);
        
    	personRepository.deletePerson("Jay", "Dean");
    	listPersons= data.getPersons();
        assertEquals(listPersons.size(), 2);
    }
    
    @Test
    public void testFindPersonByName() throws Exception{
    	List<Person> person = personRepository.findByName("Jay", "Dean");
        assertEquals("Jay", person.get(0).getFirstName());
    }
    
    @Test
    public void testFindPersonByNameIfPersonNotExist() throws Exception{
    	List<Person> person = personRepository.findByName("Adam", "Lannister");
    	assertEquals(person.size(), 0);
    }
    
    @Test
    public void testFindPersonByNameIfMissingLastname() throws Exception{
    	List<Person> person = personRepository.findByName("Jay", "");
    	assertEquals(person.size(), 0);
    }
    
    @Test
    public void testUdaptePerson() throws Exception{
    	Person person= new Person();
    	person.setAddress("4 rue petite");
    	person.setCity("Lyon");
    	person.setEmail("Robin.i@gmail.com");
    	person.setPhone("076818811");
    	person.setZip("69000");
    	person.setFirstName("Jay");
    	person.setLastName("Dean");
    	
    	personRepository.udaptePerson(person,"Jay","Dean");
    	
    	List<Person> listPersons = data.getPersons();
    	Person Dean= listPersons.get(0);
        assertEquals("4 rue petite", Dean.getAddress());
    }
    
    @Test
    public void testFindByAddress() throws Exception{
    	List<Person> person = personRepository.findByAddress("1 rue petite");
    	assertEquals("1 rue petite", person.get(0).getAddress());

    }
    
}

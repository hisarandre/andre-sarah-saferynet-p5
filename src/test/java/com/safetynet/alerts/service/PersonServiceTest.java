package com.safetynet.alerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;


@SpringBootTest
@AutoConfigureMockMvc
public class PersonServiceTest {

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonService personService;


    @Test
    public void testGetAllPersons() {
        List<Person> expectedList = new ArrayList<>();
        Person p = new Person();
        p.setAddress("1 rue petite");
        p.setCity("Lyon");
        p.setEmail("jay.d@gmail.com");
        p.setPhone("076818811");
        p.setZip("69000");
        p.setFirstName("Jay");
        p.setLastName("Dean");
        
        expectedList.add(p);

        when(personRepository.getAllPersons()).thenReturn(expectedList);
        personService.getAllPersons();
        
        assertEquals(expectedList.get(0).getFirstName(), "Jay");
        assertEquals(expectedList.get(0).getLastName(), "Dean");
        assertEquals(expectedList.get(0).getAddress(), "1 rue petite");
        assertEquals(expectedList.get(0).getCity(), "Lyon");
        assertEquals(expectedList.get(0).getZip(), "69000");
        assertEquals(expectedList.get(0).getPhone(), "076818811");
        assertEquals(expectedList.get(0).getEmail(), "jay.d@gmail.com");
    }

    @Test
    public void testAddPerson() {
        Person person = new Person();
        person.setAddress("1 rue petite");
        person.setCity("Lyon");
        person.setEmail("jay.d@gmail.com");
        person.setPhone("076818811");
        person.setZip("69000");
        person.setFirstName("Jay");
        person.setLastName("Dean");
        
        personService.addPerson(person);

        verify(personRepository, times(1)).createPerson(person);
    }

    @Test
    public void testDeletePerson() {
    	List<Person> expectedList = new ArrayList<>();
        Person p = new Person();
        p.setAddress("1 rue petite");
        p.setCity("Lyon");
        p.setEmail("jay.d@gmail.com");
        p.setPhone("076818811");
        p.setZip("69000");
        p.setFirstName("Jay");
        p.setLastName("Dean");
        
        expectedList.add(p);
        
        when(personRepository.deletePerson("Jay", "Dean")).thenReturn(expectedList);

        List<Person> actualList = personService.deletePerson("Jay", "Dean");

        assertEquals(expectedList.size(), actualList.size());
        assertEquals(expectedList.get(0).getFirstName(), actualList.get(0).getFirstName());
        assertEquals(expectedList.get(0).getLastName(), actualList.get(0).getLastName());
    }
    
    @Test
    public void testUpdatePerson() {
        Person updatedPerson = new Person();
        updatedPerson.setAddress("99 rue petite");
        updatedPerson.setCity("Lyon");
        updatedPerson.setEmail("jay.d@gmail.com");
        updatedPerson.setPhone("076818811");
        updatedPerson.setZip("69000");
        updatedPerson.setFirstName("Jay");
        updatedPerson.setLastName("Dean");
        
        when(personRepository.updatePerson(updatedPerson, "Jay", "Dean")).thenReturn(updatedPerson);

        Person actualPerson = personService.updatePerson(updatedPerson, "Jay", "Dean");

        assertEquals(updatedPerson.getFirstName(), actualPerson.getFirstName());
        assertEquals(updatedPerson.getLastName(), actualPerson.getLastName());
    }
}
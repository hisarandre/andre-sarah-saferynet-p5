package com.safetynet.alerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.alerts.MockData;
import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.DTO.AdultAndChildDTO;
import com.safetynet.alerts.model.DTO.FamiliesAndStationDTO;
import com.safetynet.alerts.model.DTO.FamilyAndStationDTO;
import com.safetynet.alerts.model.DTO.PersonWithNbOfAdultsAndChildrenDTO;
import com.safetynet.alerts.repository.DataRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomInfoServiceTest {
	
    @Autowired
    private MockData mockData;
    
    @Autowired
    private Data data;
    
    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private PersonRepository personRepository;
    
    @Mock
    private MedicalRecordService medicalRecordService;
    
    @Mock
    private FirestationService firestationService;
    
    @Autowired
    private CustomInfoService customInfoService;

    @Mock
    private DataRepository dataRepository;
    
    @BeforeEach()
    public void setUp() throws Exception{
    	data = mockData.getAllMockData();
        Mockito.when(dataRepository.read(anyString())).thenReturn(data);
    }
    
    @Test
    public void testGetPersonsByStation() {
        List<String> addressesByStation = new ArrayList<>();
        addressesByStation.add("3 rue grande");
        List<Person> personsList = data.getPersons();
        List<MedicalRecord> medicalRecordsList =  data.getMedicalrecords();
        
        when(personRepository.getAllPersons()).thenReturn(personsList);
        when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecordsList);
        when(firestationService.findAdressesByStation("3")).thenReturn(addressesByStation);

        PersonWithNbOfAdultsAndChildrenDTO result = customInfoService.getPersonsByStation("3");

        assertEquals(1, result.getNbOfAdults());
        assertEquals(1, result.getNbOfChildren());
    }

    @Test
    public void testGetChildrenByAddress() {
    	List<Person> personsList = data.getPersons();
        List<MedicalRecord> medicalRecordsList = data.getMedicalrecords();
        
    	List<Person> personsListByAddress = new ArrayList<>();
        Person p1 = new Person();
        p1.setAddress("3 rue grande");
        p1.setCity("Lyon");
        p1.setEmail("nathan@gmail.com");
        p1.setPhone("08799009");
        p1.setZip("69000");
        p1.setFirstName("Nathan");
        p1.setLastName("Han");

        Person p2 = new Person();
        p2.setAddress("3 rue grande");
        p2.setCity("Lyon");
        p2.setEmail("alix@gmail.com");
        p2.setPhone("0711121314");
        p2.setZip("69000");
        p2.setFirstName("Alix");
        p2.setLastName("Han");
       
        personsListByAddress.add(p1);
        personsListByAddress.add(p2);
        
        when(personRepository.getAllPersons()).thenReturn(personsList);
        when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecordsList);
        when(personRepository.findByAddress("3 rue grande")).thenReturn(personsList);
       
        AdultAndChildDTO result = customInfoService.getChildrenByAddress("3 rue grande");
        
        assertEquals(1, result.getAdults().size());    	
    }
    
    @Test
    public void testGetPhoneNumbersByStation() {
        List<String> addressesByStation = new ArrayList<>();
        addressesByStation.add("3 rue grande");      
        List<Person> personsList = data.getPersons();
        
        when(personRepository.getAllPersons()).thenReturn(personsList);
        when(firestationService.findAdressesByStation("3")).thenReturn(addressesByStation);

        HashSet<String> phoneNumbers = customInfoService.getPhoneNumbersByStation("3");
        
        assertEquals(2, phoneNumbers.size());
    }
    
    @Test
    public void testGetPersonsMedicalRecordsAndStationByAddress() {
        List<Person> personsListByAddress = new ArrayList<>();
        Person p1 = new Person();
        p1.setAddress("3 rue grande");
        p1.setCity("Lyon");
        p1.setEmail("nathan@gmail.com");
        p1.setPhone("08799009");
        p1.setZip("69000");
        p1.setFirstName("Nathan");
        p1.setLastName("Han");

        Person p2 = new Person();
        p2.setAddress("3 rue grande");
        p2.setCity("Lyon");
        p2.setEmail("alix@gmail.com");
        p2.setPhone("0711121314");
        p2.setZip("69000");
        p2.setFirstName("Alix");
        p2.setLastName("Han");
        
        when(personRepository.findByAddress("3 rue grande")).thenReturn(personsListByAddress);
        when(firestationService.findStationByAddress("3 rue grande")).thenReturn("3");

        FamilyAndStationDTO result = customInfoService.getPersonsMedicalRecordsAndStationByAddress("3 rue grande");
        
        assertEquals(2, result.getFamily().size());
        assertEquals("3", result.getStation());
    }
    
    @Test
    public void testGetFamilyByStation() {
        List<MedicalRecord> medicalRecordsList = data.getMedicalrecords();
        List<String> listOfStation = new ArrayList<String>(Arrays.asList("2", "3"));
        List<String> addressesByStation1 = new ArrayList<String>(Arrays.asList("3 rue grande"));
        List<String> addressesByStation2 = new ArrayList<String>(Arrays.asList("1 rue petite"));
        
        List<Person> personsListByAddress1 = new ArrayList<>();
        List<Person> personsListByAddress2 =  new ArrayList<>();;
        
        Person p1 = new Person();
        p1.setAddress("3 rue grande");
        p1.setCity("Lyon");
        p1.setEmail("nathan@gmail.com");
        p1.setPhone("08799009");
        p1.setZip("69000");
        p1.setFirstName("Nathan");
        p1.setLastName("Han");
        personsListByAddress1.add(p1);
        
        Person p2 = new Person();
        p2.setAddress("1 rue petite");
        p2.setCity("Lyon");
        p2.setEmail("jay.d@gmail.com");
        p2.setPhone("076818811");
        p2.setZip("69000");
        p2.setFirstName("Jay");
        p2.setLastName("Dean");
        personsListByAddress2.add(p2);
        
        when(firestationService.findAdressesByStation("3")).thenReturn(addressesByStation1);
        when(firestationService.findAdressesByStation("2")).thenReturn(addressesByStation2);
        when(personRepository.findByAddress("3 rue grande")).thenReturn(personsListByAddress1);
        when(personRepository.findByAddress("1 rue petite")).thenReturn(personsListByAddress2);
        when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecordsList);

        List<FamiliesAndStationDTO> result = customInfoService.getFamilyByStation(listOfStation);

        assertEquals(2, result.size());
    }

    @Test
	public void testGetAllEmailsByCity() {
    	List<Person> personsList = data.getPersons();

		when(personRepository.getAllPersons()).thenReturn(personsList);
		HashSet<String> result = customInfoService.getAllEmailsByCity("Lyon");
		
		assertTrue(result.contains("alix@gmail.com"));
	}
}
       

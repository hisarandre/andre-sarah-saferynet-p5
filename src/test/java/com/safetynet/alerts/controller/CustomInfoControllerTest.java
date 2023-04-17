package com.safetynet.alerts.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.safetynet.alerts.model.DTO.AdultAndChildDTO;
import com.safetynet.alerts.model.DTO.FamiliesAndStationDTO;
import com.safetynet.alerts.model.DTO.FamilyAndStationDTO;
import com.safetynet.alerts.model.DTO.PersonAndMedicalRecordDTO;
import com.safetynet.alerts.model.DTO.PersonWithNbOfAdultsAndChildrenDTO;
import com.safetynet.alerts.service.CustomInfoService;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomInfoControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CustomInfoService customInfoService;
	
	private static final String FIRESTATION_ENDPOINT = "/firestation";
	private static final String CHILDALERT_ENDPOINT = "/childAlert";
	private static final String PHONEALERT_ENDPOINT = "/phoneAlert";
	private static final String FIRE_ENDPOINT = "/fire";
	private static final String FLOOD_STATION_ENDPOINT = "/flood/station";
	private static final String PERSON_INFO_ENDPOINT = "/personInfo";
	private static final String COMMUNITY_EMAIL_ENDPOINT = "/communityEmail";

	    
	@Test
	public void testGetPersonsByStation() throws Exception {
		PersonWithNbOfAdultsAndChildrenDTO mockResponse = new PersonWithNbOfAdultsAndChildrenDTO();
		String station = "1";
		
		when(customInfoService.getPersonsByStation(station)).thenReturn(mockResponse);
		
        mvc.perform(get(FIRESTATION_ENDPOINT).param("stationNumber", station)
        		.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk());
	} 
	
	@Test
	public void testGetPersonsByStationIsNotFound() throws Exception {
		String station = "1";
		
		when(customInfoService.getPersonsByStation(station)).thenReturn(null);
		
        mvc.perform(get(FIRESTATION_ENDPOINT).param("stationNumber", station)
        		.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isNotFound());
	} 
	
	@Test
	public void testGetChildrenByAdress() throws Exception {
		AdultAndChildDTO mockResponse = new AdultAndChildDTO();
		String address="123 Rue Test";
				
		when(customInfoService.getChildrenByAddress(address)).thenReturn(mockResponse);
		
        mvc.perform(get(CHILDALERT_ENDPOINT).param("address", address)
        		.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk()); 
	}
	
	@Test
	public void testGetChildrenByAdressIsNotFound() throws Exception {
		String address="123 Rue Test";
				
		when(customInfoService.getChildrenByAddress(address)).thenReturn(null);
		
        mvc.perform(get(CHILDALERT_ENDPOINT).param("address", address)
        		.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isNotFound()); 
	}
	
	@Test
	public void testGetPhoneNumbersByStation() throws Exception {
		String station="1";
		when(customInfoService.getPhoneNumbersByStation(station)).thenReturn(new HashSet<>(Arrays.asList("123-456-7890", "098-765-4321")));
		
		mvc.perform(get(PHONEALERT_ENDPOINT).param("firestation", station)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testGetPhoneNumbersByStationIsNotFound() throws Exception {
		String station="1";
		when(customInfoService.getPhoneNumbersByStation(station)).thenReturn(new HashSet<>());
		
		mvc.perform(get(PHONEALERT_ENDPOINT).param("firestation", station)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	
    @Test
    public void testGetPersonsMedicalRecordsAndStationByAddress() throws Exception {
        String address = "123 Main St";
        FamilyAndStationDTO mockResponse = new FamilyAndStationDTO();
        
        when(customInfoService.getPersonsMedicalRecordsAndStationByAddress(address)).thenReturn(mockResponse);
        
        mvc.perform(get(FIRE_ENDPOINT).param("address", address)
        		.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk());
    }
    
    @Test
    public void testGetPersonsMedicalRecordsAndStationByAddressIsNotFound() throws Exception {
        String address = "123 Main St";
        
        when(customInfoService.getPersonsMedicalRecordsAndStationByAddress(address)).thenReturn(null);
        
        mvc.perform(get(FIRE_ENDPOINT).param("address", address)
        		.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isNotFound());
    }

    @Test
    public void testGetFamilyByStation() throws Exception {
        List<String> stations = Arrays.asList("1", "2", "3");
        List<FamiliesAndStationDTO> mockResponse = Arrays.asList(new FamiliesAndStationDTO(), new FamiliesAndStationDTO());
        
        when(customInfoService.getFamilyByStation(stations)).thenReturn(mockResponse);
        
        mvc.perform(get(FLOOD_STATION_ENDPOINT).param("stations", String.join(",", stations))
        		.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk());      		
    }
    
    @Test
    public void testGetFamilyByStationIsNotFound() throws Exception {
        List<String> stations = Arrays.asList("1", "2", "3");
        List<FamiliesAndStationDTO> emptyList = new ArrayList<>();
        
        when(customInfoService.getFamilyByStation(stations)).thenReturn(emptyList);
        
        mvc.perform(get(FLOOD_STATION_ENDPOINT).param("stations", String.join(",", stations))
        		.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isNotFound());      		
    }

    @Test
    public void testGetPersonAndMedicalRecord() throws Exception {
        String firstName = "John";
        String lastName = "Doe";
        List<PersonAndMedicalRecordDTO> mockResponse = Arrays.asList(new PersonAndMedicalRecordDTO(), new PersonAndMedicalRecordDTO());
        
        when(customInfoService.getPersonAndMedicalRecord(firstName, lastName)).thenReturn(mockResponse);
        
        mvc.perform(get(PERSON_INFO_ENDPOINT).param("firstName", firstName).param("lastName", lastName)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
    }
    
    @Test
    public void testGetPersonAndMedicalRecordIsNotFound() throws Exception {
        String firstName = "John";
        String lastName = "Doe";
        List<PersonAndMedicalRecordDTO> emptyList = new ArrayList<>();
        
        when(customInfoService.getPersonAndMedicalRecord(firstName, lastName)).thenReturn(emptyList);
        
        mvc.perform(get(PERSON_INFO_ENDPOINT).param("firstName", firstName).param("lastName", lastName)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
    }
    
    @Test
    public void testGetAllEmailsByCity() throws Exception {
        String city = "Springfield";
        HashSet<String> mockResponse = new HashSet<>(Arrays.asList("john.doe@example.com", "jane.doe@example.com"));
        
        when(customInfoService.getAllEmailsByCity(city)).thenReturn(mockResponse);
        
        mvc.perform(get(COMMUNITY_EMAIL_ENDPOINT).param("city", city)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
    }
	
    @Test
    public void testGetAllEmailsByCityIsNotFound() throws Exception {
        String city = "Springfield";
        HashSet<String> emptyList = new HashSet<>();
        
        when(customInfoService.getAllEmailsByCity(city)).thenReturn(emptyList);
        
        mvc.perform(get(COMMUNITY_EMAIL_ENDPOINT).param("city", city)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
    }
	
}
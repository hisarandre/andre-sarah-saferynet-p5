package com.safetynet.alerts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.FirestationRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationServiceTest {

	@InjectMocks
	private FirestationService firestationService;

	@Mock
	private FirestationRepository firestationRepository;

	@Test
	public void testGetAllFirestations() {
		// Arrange
		Firestation firestation1 = new Firestation();
		firestation1.setAddress("address1");
		firestation1.setStation("1");
		
		Firestation firestation2 = new Firestation();
		firestation2.setAddress("address2");
		firestation2.setStation("2");
		
		List<Firestation> firestations = new ArrayList<>();
		firestations.add(firestation1);
		firestations.add(firestation2);
		when(firestationRepository.getAllFirestations()).thenReturn(firestations);

		// Act
		List<Firestation> result = firestationService.getAllFirestations();

		// Assert
		assertNotNull(result);
		assertEquals(firestations.size(), result.size());
		assertEquals(firestations.get(0).getAddress(), result.get(0).getAddress());
		assertEquals(firestations.get(0).getStation(), result.get(0).getStation());
		assertEquals(firestations.get(1).getAddress(), result.get(1).getAddress());
		assertEquals(firestations.get(1).getStation(), result.get(1).getStation());
	}

	@Test
	public void testAddFirestation() {
		// Arrange
		Firestation firestation = new Firestation();
		firestation.setAddress("address1");
		firestation.setStation("1");

		// Act
		firestationService.addFirestation(firestation);

		// Assert
		verify(firestationRepository, times(1)).createFirestation(firestation);
	}

	@Test
	public void testDeleteFirestation() {
		// Arrange
		Firestation firestation = new Firestation();
		firestation.setAddress("address1");
		firestation.setStation("1");
		
		String address = "address1";
		String station = "1";
		List<Firestation> firestations = new ArrayList<>();
		firestations.add(firestation);
		when(firestationRepository.deleteFirestation(address, station)).thenReturn(firestations);

		// Act
		List<Firestation> result = firestationService.deleteFirestation(address, station);

		// Assert
		assertNotNull(result);
		assertEquals(firestations.size(), result.size());
		assertEquals(firestations.get(0).getAddress(), result.get(0).getAddress());
		assertEquals(firestations.get(0).getStation(), result.get(0).getStation());
		verify(firestationRepository, times(1)).deleteFirestation(address, station);
	}
	
	@Test
	public void testFindStationByAddress() {
	    // Arrange
	    String address = "1234 Main St";
	    String station = "1";
	    
		Firestation firestation1 = new Firestation();
		firestation1.setAddress("1234 Main St");
		firestation1.setStation("1");
		
		Firestation firestation2 = new Firestation();
		firestation2.setAddress("address2");
		firestation2.setStation("2");
		
		
	    List<Firestation> firestations = Arrays.asList(firestation1,firestation2);
	    when(firestationRepository.getAllFirestations()).thenReturn(firestations);

	    // Act
	    String result = firestationService.findStationByAddress(address);

	    // Assert
	    assertEquals(station, result);
	}

	@Test
	public void testFindAddressesByStation() {
	    // Arrange
	    String station = "1";
	    
		Firestation firestation1 = new Firestation();
		firestation1.setAddress("1234 Main St");
		firestation1.setStation("1");
		
		Firestation firestation2 = new Firestation();
		firestation2.setAddress("5678 2nd St");
		firestation2.setStation("2");
		
		Firestation firestation3 = new Firestation();
		firestation3.setAddress("91011 3rd St");
		firestation3.setStation("1");

	    List<Firestation> firestations = Arrays.asList(firestation1,firestation2, firestation3);
	    when(firestationRepository.getAllFirestations()).thenReturn(firestations);

	    // Act
	    List<String> result = firestationService.findAdressesByStation(station);

	    // Assert
	    assertEquals(2, result.size());
	    assertTrue(result.contains("1234 Main St"));
	    assertTrue(result.contains("91011 3rd St"));
	}

	@Test
	public void testUpdateFirestation() {
	    // Arrange
		Firestation firestation = new Firestation();
		firestation.setAddress("1234 Main St");
		firestation.setStation("1");
		
	    String address = "1234 Main St";
	    when(firestationRepository.udapteFirestation(any(Firestation.class), any(String.class))).thenReturn(firestation);

	    // Act
	    Firestation result = firestationService.udapteFirestation(firestation, address);

	    // Assert
	    assertNotNull(result);
	    assertEquals(firestation.getAddress(), result.getAddress());
	    assertEquals(firestation.getStation(), result.getStation());
	    verify(firestationRepository, times(1)).udapteFirestation(firestation, address);
	}
}
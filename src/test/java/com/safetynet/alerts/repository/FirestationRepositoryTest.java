package com.safetynet.alerts.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.alerts.MockData;
import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;

@SpringBootTest
class FirestationRepositoryTest {

    @Autowired
    private MockData mockData;
    
    @Autowired
    private Data data;
    
    @Autowired
    private FirestationRepository firestationRepository;
    
    @Mock
    private DataRepository dataRepository;
    
    @BeforeEach()
    public void setUp(){
    	data = mockData.getAllMockData();
        Mockito.when(dataRepository.read(anyString())).thenReturn(data);
    }
    
    @Test
    public void testGetAllFirestations() throws Exception{
    	List<Firestation> listFirestation= firestationRepository.getAllFirestations();
        assertEquals(3, listFirestation.size());
    }
    
    @Test
    public void testCreateFirestation() throws Exception{
    	Firestation firestation = new Firestation();
        firestation.setAddress("100 rue grande");
        firestation.setStation("1");
        
        firestationRepository.createFirestation(firestation);
    	List<Firestation> listFirestation= data.getFirestations();
        assertEquals(4, listFirestation.size());
    }
    
    @Test
    public void testDeleteOnlyAddressWithLinkedStation() throws Exception{
    	List<Firestation> listFirestation= data.getFirestations();
        assertEquals(listFirestation.size(), 3);
        
        firestationRepository.deleteFirestation("9 rue grande", "1");
        listFirestation= data.getFirestations();
        assertEquals(listFirestation.size(), 2);
    }

    @Test
    public void testDeleteStationAndAllAddressLinked() throws Exception{
    	List<Firestation> listFirestation= data.getFirestations();
        assertEquals(listFirestation.size(), 3);
        
        firestationRepository.deleteFirestation(null, "1");
        listFirestation= data.getFirestations();
        assertEquals(listFirestation.size(), 2);
    }
    
    @Test
    public void testUdapteFirestation() throws Exception{
    	Firestation firestation = new Firestation();
        firestation.setAddress("9 rue grande");
        firestation.setStation("1");
    	
        firestationRepository.udapteFirestation(firestation,"10 rue grande");
    	
    	List<Firestation> listFirestation= data.getFirestations();

    	Firestation f= listFirestation.get(0);
        assertEquals("10 rue grande", f.getAddress());
    }
}

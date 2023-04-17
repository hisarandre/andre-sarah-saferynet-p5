package com.safetynet.alerts.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.FirestationService;


@SpringBootTest
@AutoConfigureMockMvc
class FirestationControllerTest {

	@Autowired
	public MockMvc mvc;
	
	@MockBean
	private FirestationService firestationService;
	
    @InjectMocks
    private FirestationController firestationController;

	@Test
    public void testPostFirestation() throws Exception{
        String newFirestation = "{\"address\":\"100 rue de lyon\",\"station\":\"99\"}";
        
		mvc.perform(post("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newFirestation))
        .andExpect(status().isOk()); 
    }
	
	@Test
    public void testPostFirestationIfNoContentSent() throws Exception{
        String empty = "";
        
		mvc.perform(post("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(empty))
        .andExpect(status().isBadRequest());
    }
	
	@Test
    public void testDeleteFirestation() throws Exception{
        List<Firestation> mockResponse = Arrays.asList(new Firestation(), new Firestation());
        String address = "une adresse";
        String station = "3";

		when(firestationService.deleteFirestation(address,station)).thenReturn(mockResponse);
		
        mvc.perform(delete("/firestation").param("address", address).param("station", station)
        		.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk()); 
    }
	
	@Test
	public void testPutFirestation() throws Exception {
	    Firestation mockResponse = new Firestation();
	    mockResponse.setStation("1");
	    mockResponse.setAddress("une rue");

	    when(firestationService.udapteFirestation(any(Firestation.class), eq("une rue")))
	            .thenReturn(mockResponse);

	    String updatedFirestation = "{\"address\":\"9 rue grande\",\"station\":\"1\"}";

	    mvc.perform(put("/firestation")
	            .param("address", "une rue")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(updatedFirestation))
	            .andExpect(status().isOk());
	}
	
	@Test
    public void testPutFirestationIfMissingParam() throws Exception{
        String newFirestation = "{\"address\":\"100 rue de lyon\",\"station\":\"99\"}";

		mvc.perform(put("/firestation")
	    .contentType(MediaType.APPLICATION_JSON)
	    .content(newFirestation))
        .andExpect(status().isBadRequest());
    }

	@Test
	public void TestdeleteFirestationFromNumberNotFound() throws Exception {
	    mvc.perform(delete("/firestation")
	            .param("station", "1")
	            .param("address", "test"))
	            .andExpect(status().isNotFound());
	}

	@Test
	public void TestudapteFirestationNotFound() throws Exception {
	    Firestation firestation = new Firestation();
	    firestation.setStation("1");
        String newFirestation = "{\"address\":\"100 rue de lyon\",\"station\":\"99\"}";


	    mvc.perform(put("/firestation")
	            .contentType(MediaType.APPLICATION_JSON)
	            .param("address", "test")
	            .content(newFirestation))
	            .andExpect(status().isNotFound());

	}
}

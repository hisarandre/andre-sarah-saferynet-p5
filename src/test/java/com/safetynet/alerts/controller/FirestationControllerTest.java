package com.safetynet.alerts.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class FirestationControllerTest {

	@Autowired
	public MockMvc mvc;
	
    @Test
    public void testGetFirestation() throws Exception{
		mvc.perform(get("/firestation")).andExpect(status().isOk());
    }

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
		mvc.perform(delete("/firestation?address=9+rue+grande&station=1")).andExpect(status().isOk());
    }
	
	@Test
    public void testPutFirestation() throws Exception{
        String firestation = "{\"address\":\"9 rue grande\",\"station\":\"1\"}";

		mvc.perform(put("/firestation?address=100+rue+grande")
	    .contentType(MediaType.APPLICATION_JSON)
	    .content(firestation))
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

}

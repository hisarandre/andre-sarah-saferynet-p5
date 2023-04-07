package com.safetynet.alerts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

	@Autowired
	public MockMvc mvc;
	
    @Test
    public void testGetPerson() throws Exception{
		mvc.perform(get("/person")).andExpect(status().isOk());
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
		mvc.perform(delete("/person?firstName=jon&lastName=snow")).andExpect(status().isOk());
    }
	
	@Test
    public void testDeletePersonIfMissingName() throws Exception{
		mvc.perform(delete("/person")).andExpect(status().isBadRequest());
    }
	
	@Test
    public void testPutPerson() throws Exception{
	    String newPerson = "{\"firstName\":\"jon\",\"lastName\":\"snow\",\"address\":\"une rue\",\"city\":\"une ville\",\"zip\":\"00000\",\"phone\":\"000-111-2222\",\"email\":\"js.stark@mail.com\"}";

		mvc.perform(put("/person?firstName=jon&lastName=snow")
	    .contentType(MediaType.APPLICATION_JSON)
	    .content(newPerson))
	    .andExpect(status().isOk()); 
    }
	
	@Test
    public void testPutPersonIfMissingName() throws Exception{
	    String newPerson = "{\"firstName\":\"jon\",\"lastName\":\"snow\",\"address\":\"une rue\",\"city\":\"une ville\",\"zip\":\"00000\",\"phone\":\"000-111-2222\",\"email\":\"js.stark@mail.com\"}";

		mvc.perform(put("/person")
	    .contentType(MediaType.APPLICATION_JSON)
	    .content(newPerson))
        .andExpect(status().isBadRequest());
    }
}

package com.safetynet.alerts.model.DTO;

import java.util.List;

public class PersonAndMedicalRecordDTO {
	public String firstName;
    public String lastName;
    public String address;
    public int age;
    public String email;
    public List<String> medications;
    public List<String> allergies;
    
    public String getFirstName() {
		return firstName;
	}
    
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<String> getMedications() {
		return medications;
	}
	
	public void setMedications(List<String> medications) {
		this.medications = medications;
	}
	
	public List<String> getAllergies() {
		return allergies;
	}
	
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
}
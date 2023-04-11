package com.safetynet.alerts.model.DTO;

import java.util.List;

public class PersonWithNbOfAdultsAndChildrenDTO {
	
	public List<PersonAdressPhoneDTO> personInfo;
    public int nbOfAdults;
    public int nbOfChildren;
    
	public List<PersonAdressPhoneDTO> getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(List<PersonAdressPhoneDTO> personInfo) {
		this.personInfo = personInfo;
	}
	public int getNbOfAdults() {
		return nbOfAdults;
	}
	public void setNbOfAdults(int nbOfAdults) {
		this.nbOfAdults = nbOfAdults;
	}
	public int getNbOfChildren() {
		return nbOfChildren;
	}
	public void setNbOfChildren(int nbOfChildren) {
		this.nbOfChildren = nbOfChildren;
	}
}

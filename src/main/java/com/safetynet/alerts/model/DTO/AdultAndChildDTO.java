package com.safetynet.alerts.model.DTO;

import java.util.List;

public class AdultAndChildDTO {
	public List<PersonAgeDTO> children;
	public List<PersonNameDTO> adults;
	
	public List<PersonAgeDTO> getChildren() {
		return children;
	}
	public void setChildren(List<PersonAgeDTO> children) {
		this.children = children;
	}
	public List<PersonNameDTO> getAdults() {
		return adults;
	}
	public void setAdults(List<PersonNameDTO> adults) {
		this.adults = adults;
	}
}

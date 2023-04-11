package com.safetynet.alerts.model.DTO;

import java.util.List;

public class FamilyAndStationDTO {
	public String station;
	public List<PersonPhoneAgeMedicalRecordDTO> family;
	
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public List<PersonPhoneAgeMedicalRecordDTO> getFamily() {
		return family;
	}
	public void setFamily(List<PersonPhoneAgeMedicalRecordDTO> family) {
		this.family = family;
	}	
}

package com.safetynet.alerts.model.DTO;

import java.util.List;

public class FamiliesAndStationDTO {

	public String station;
	public List<FamilyDTO> families;
	
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public List<FamilyDTO> getFamilies() {
		return families;
	}
	public void setFamilies(List<FamilyDTO> families) {
		this.families = families;
	}
}

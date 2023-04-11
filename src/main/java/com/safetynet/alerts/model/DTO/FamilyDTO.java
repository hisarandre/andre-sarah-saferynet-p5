package com.safetynet.alerts.model.DTO;

import java.util.List;

public class FamilyDTO {
	public List<PersonPhoneAgeMedicalRecordDTO> family;

	public List<PersonPhoneAgeMedicalRecordDTO> getFamily() {
		return family;
	}

	public void setFamily(List<PersonPhoneAgeMedicalRecordDTO> family) {
		this.family = family;
	}
}

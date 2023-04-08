package com.safetynet.alerts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

@Component
public class MockData {
	
	public Data getAllMockData() {
		
		Data mockData = new Data();
		List<Person> mockPersons = new ArrayList<>();
		List<MedicalRecord> mockMedicalRecords = new ArrayList<>(); 
		List<Firestation> mockFirestations = new ArrayList<>();
        List<String> mockAllergies = new ArrayList<>();
        List<String> mockMedications = new ArrayList<>();

        Person p1 = new Person();
        p1.setAddress("1 rue petite");
        p1.setCity("Lyon");
        p1.setEmail("jay.d@gmail.com");
        p1.setPhone("076818811");
        p1.setZip("69000");
        p1.setFirstName("Jay");
        p1.setLastName("Dean");

        Person p2 = new Person();
        p2.setAddress("2 rue grande");
        p2.setCity("Lyon");
        p2.setEmail("nathan@gmail.com");
        p2.setPhone("08799009");
        p1.setZip("69000");
        p2.setFirstName("Nathan");
        p2.setLastName("Han");

        Person p3 = new Person();
        p3.setAddress("3 rue grande");
        p3.setCity("Lyon");
        p3.setEmail("alix@gmail.com");
        p3.setPhone("0711121314");
        p1.setZip("69000");
        p3.setFirstName("Alix");
        p3.setLastName("Han");

        mockPersons.add(p1);
        mockPersons.add(p2);
        mockPersons.add(p3);

        mockData.setPersons(mockPersons);

        Firestation f1 = new Firestation();
        f1.setAddress("9 rue grande");
        f1.setStation("1");

        Firestation f2 = new Firestation();
        f2.setAddress("9 rue petite");
        f2.setStation("2");

        Firestation f3 = new Firestation();
        f3.setAddress("9 rue moyenne");
        f3.setStation("3");

        mockFirestations.add(f1);
        mockFirestations.add(f2);
        mockFirestations.add(f3);

        mockData.setFirestations(mockFirestations);

        mockAllergies.add("cats");
        mockAllergies.add("peanuts");
        mockMedications.add("tetracyclaz:650mg");
        mockMedications.add("dodoxadin:30mg");
        
        MedicalRecord m1 = new MedicalRecord();
        m1.setFirstName("Alix");
        m1.setLastName("Han");
        m1.setBirthdate("18/05/1992");
        m1.setAllergies(mockAllergies);
        m1.setMedications(mockMedications);

        MedicalRecord m2 = new MedicalRecord();
        m2.setFirstName("Nathan");
        m2.setLastName("Han");
        m2.setBirthdate("30/01/2000");
        m1.setAllergies(mockAllergies);
        m1.setMedications(mockMedications);

        MedicalRecord m3 = new MedicalRecord();
        m3.setFirstName("Jay");
        m3.setLastName("Dean");
        m3.setBirthdate("03/12/1986");
        m1.setAllergies(mockAllergies);
        m1.setMedications(mockMedications);

        mockMedicalRecords.add(m1);
        mockMedicalRecords.add(m2);
        mockMedicalRecords.add(m3);

        mockData.setMedicalrecords(mockMedicalRecords);

        return mockData;
	}
}

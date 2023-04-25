package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordServiceTest {

    @Mock
    MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    MedicalRecordService medicalRecordService;

    @Test
    public void testGetAllMedicalRecords() {
        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("John");
        medicalRecord1.setLastName("Doe");
        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Jane");
        medicalRecord2.setLastName("Doe");
        List<MedicalRecord> expectedMedicalRecords = Arrays.asList(medicalRecord1, medicalRecord2);

        when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(expectedMedicalRecords);

        List<MedicalRecord> actualMedicalRecords = medicalRecordService.getAllMedicalRecords();

        assertThat(actualMedicalRecords).containsExactlyElementsOf(expectedMedicalRecords);
    }

    @Test
    public void testAddMedicalRecord() {
        MedicalRecord medicalRecordToAdd = new MedicalRecord();
        medicalRecordToAdd.setFirstName("John");
        medicalRecordToAdd.setLastName("Doe");

        medicalRecordService.addMedicalRecord(medicalRecordToAdd);

        verify(medicalRecordRepository).createMedicalRecord(eq(medicalRecordToAdd));
    }

    @Test
    public void testDeleteMedicalRecord() {
        String firstNameToDelete = "John";
        String lastNameToDelete = "Doe";
        MedicalRecord medicalRecordToDelete = new MedicalRecord();
        medicalRecordToDelete.setFirstName(firstNameToDelete);
        medicalRecordToDelete.setLastName(lastNameToDelete);
        List<MedicalRecord> expectedMedicalRecordsAfterDeletion = Arrays.asList(new MedicalRecord());

        when(medicalRecordRepository.deleteMedicalRecord(firstNameToDelete, lastNameToDelete)).thenReturn(expectedMedicalRecordsAfterDeletion);

        List<MedicalRecord> actualMedicalRecordsAfterDeletion = medicalRecordService.deleteMedicalRecord(firstNameToDelete, lastNameToDelete);

        assertThat(actualMedicalRecordsAfterDeletion).containsExactlyElementsOf(expectedMedicalRecordsAfterDeletion);
    }
    
    @Test
    public void testUpdateMedicalRecord() {
        String firstNameToUpdate = "John";
        String lastNameToUpdate = "Doe";
        MedicalRecord medicalRecordToUpdate = new MedicalRecord();
        medicalRecordToUpdate.setFirstName(firstNameToUpdate);
        medicalRecordToUpdate.setLastName(lastNameToUpdate);
        MedicalRecord expectedMedicalRecordAfterUpdate = new MedicalRecord();
        expectedMedicalRecordAfterUpdate.setFirstName(firstNameToUpdate);
        expectedMedicalRecordAfterUpdate.setLastName(lastNameToUpdate);
        expectedMedicalRecordAfterUpdate.setBirthdate("01/01/1970");

        when(medicalRecordRepository.updateMedicalRecord(eq(medicalRecordToUpdate), eq(firstNameToUpdate), eq(lastNameToUpdate))).thenReturn(expectedMedicalRecordAfterUpdate);

        MedicalRecord actualMedicalRecordAfterUpdate = medicalRecordService.updateMedicalRecord(medicalRecordToUpdate, firstNameToUpdate, lastNameToUpdate);

        assertThat(actualMedicalRecordAfterUpdate).isEqualTo(expectedMedicalRecordAfterUpdate);

        verify(medicalRecordRepository).updateMedicalRecord(eq(medicalRecordToUpdate), eq(firstNameToUpdate), eq(lastNameToUpdate));
    }

    @Test
    public void testCalculateAge() {
        String birthdate = "01/01/2000";
        int expectedAge = LocalDate.now().getYear() - 2000;

        int actualAge = medicalRecordService.calculateAge(birthdate);

        assertThat(actualAge).isEqualTo(expectedAge);
    }
}
package com.task.servingwebcontent.repository;

import com.task.servingwebcontent.model.Patient;
import com.task.servingwebcontent.model.PatientStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<Patient, Integer> {
    List<Patient> findByPatientStatus(PatientStatus patientStatus);

    Patient findById(int id);
}

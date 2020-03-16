package com.task.servingwebcontent.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Objects;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private PatientStatus patientStatus;

    public Patient() {

    }

    public Patient(String name, String surname, String birthDate, int patientStatus) {
        this.name = name;
        this.surname = surname;

        this.birthDate = LocalDate.of(Integer.parseInt(birthDate.substring(0, 4)),
                Integer.parseInt(birthDate.substring(5, 7)), Integer.parseInt(birthDate.substring(8, 10)));

        switch (patientStatus) {
            case 1: {
                this.patientStatus = PatientStatus.healed;
                break;
            }
            case 2: {
                this.patientStatus = PatientStatus.died;
                break;
            }
            case 3: {
                this.patientStatus = PatientStatus.healing;
                break;
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public PatientStatus getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(PatientStatus patientStatus) {
        this.patientStatus = patientStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(name, patient.name) &&
                Objects.equals(surname, patient.surname) &&
                Objects.equals(birthDate, patient.birthDate) &&
                patientStatus == patient.patientStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, birthDate, patientStatus);
    }

    public int calculateAge() {
        LocalDate currentDate = LocalDate.now();
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }
}


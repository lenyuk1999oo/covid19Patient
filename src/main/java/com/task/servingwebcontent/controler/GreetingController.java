package com.task.servingwebcontent.controler;

import com.task.servingwebcontent.model.Patient;
import com.task.servingwebcontent.model.PatientStatus;
import com.task.servingwebcontent.repository.PatientRepository;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class GreetingController {
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/main")
    public void main(Map<String, Object> model) {
        Iterable<Patient> patients = patientRepository.findAll();
        model.put("patients", patients);
    }

    @GetMapping("/choose")
    public String choose(Map<String, Object> model) {
        Iterable<Patient> patients = patientRepository.findAll();
        model.put("patients", patients);
        return "choose";
    }

    @PostMapping("/choose")
    public String choose(@RequestParam(name = "patient", required = false, defaultValue = "-1") int patient,
                         Map<String, Object> model) {
        if (patient != -1) {
            Patient patient1 = patientRepository.findById(patient);
            model.put("patient", patient1);
        }
        return "change";
    }

    @PostMapping("/change")
    public String change(@RequestParam int id, @RequestParam String name, @RequestParam String surname,
                         @RequestParam String birthDate, @RequestParam int patientStatus, Map<String, Object> model) {
        patientRepository.delete(patientRepository.findById(id));
        Patient patient = new Patient(name, surname, birthDate, patientStatus);
        patientRepository.save(patient);
        Iterable<Patient> patients = patientRepository.findAll();
        model.put("patients", patients);
        return "choose";
    }

    @GetMapping("/delete")
    public String delete(Map<String, Object> model) {
        Iterable<Patient> patients = patientRepository.findAll();
        model.put("patients", patients);
        return "delete";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int patient, Map<String, Object> model) {
        patientRepository.delete(patientRepository.findById(patient));
        Iterable<Patient> patients = patientRepository.findAll();
        model.put("patients", patients);
        return "delete";
    }

    @GetMapping("/add")
    public String add(Map<String, Object> model) {
        return "add";
    }

    @PostMapping("/add")
    public String add(@RequestParam String name, @RequestParam String surname, @RequestParam String birthDate,
                      @RequestParam int patientStatus, Map<String, Object> model) {
        Patient patient = new Patient(name, surname, birthDate, patientStatus);
        patientRepository.save(patient);
        return "add";
    }

    @GetMapping("/filterByStatus")
    public String filterByStatus(Map<String, Object> model) {
        Iterable<Patient> patients = patientRepository.findAll();
        model.put("patients", patients);
        return "filterByStatus";
    }

    @PostMapping("/filterByStatus")
    public String filterByStatus(@RequestParam int patientStatus, Map<String, Object> model) {
        Iterable<Patient> patients = patientRepository.findAll();
        switch (patientStatus) {
            case 1: {
                patients = patientRepository.findByPatientStatus(PatientStatus.healed);
                break;
            }
            case 2: {
                patients = patientRepository.findByPatientStatus(PatientStatus.died);
                break;
            }
            case 3: {
                patients = patientRepository.findByPatientStatus(PatientStatus.healing);
                break;
            }
        }

        model.put("patients", patients);
        return "filterByStatus";
    }

    @GetMapping("/filterByAge")
    public String filterByAge(Map<String, Object> model) {
        ArrayList<Patient> patients = (ArrayList<Patient>) patientRepository.findAll();
        ArrayList<Patient> dieds = new ArrayList<>();
        dieds.add(new Patient());
        if (patientRepository.findByPatientStatus(PatientStatus.died).size() != 0)
            for (Patient patient : dieds) {
                patient.setName(Float.toString(
                        ((float) patientRepository.findByPatientStatus(PatientStatus.died).size() /
                                (float) patients.size()) * 100));
            }
        else
            for (Patient patient : dieds) {
                patient.setName("Sick 0");
            }
        model.put("dieds", dieds);
        model.put("patients", patients);
        return "filterByAge";
    }

    @PostMapping("/filterByAge")
    public String filterByAge(@RequestParam int patientAge, Map<String, Object> model) {
        ArrayList<Patient> patients = new ArrayList<>();
        Iterable<Patient> patients1 = patientRepository.findAll();
        int diedGroup = 0;
        for (Patient patient : patients1) {
            if (patientAge == 0) {
                patients.add(patient);
                if (patient.getPatientStatus() == PatientStatus.died) {
                    ++diedGroup;
                }
            } else if (patientAge == 1 && patient.calculateAge() < 11) {
                patients.add(patient);
                if (patient.getPatientStatus() == PatientStatus.died) {
                    ++diedGroup;
                }
            } else if (patientAge == 2 && patient.calculateAge() > 10 && patient.calculateAge() < 16) {
                patients.add(patient);
                if (patient.getPatientStatus() == PatientStatus.died) {
                    ++diedGroup;
                }
            } else if (patientAge == 3 && patient.calculateAge() > 15 && patient.calculateAge() < 19) {
                patients.add(patient);
                if (patient.getPatientStatus() == PatientStatus.died) {
                    ++diedGroup;
                }
            } else if (patientAge == 4 && patient.calculateAge() > 18 && patient.calculateAge() < 24) {
                patients.add(patient);
                if (patient.getPatientStatus() == PatientStatus.died) {
                    ++diedGroup;
                }
            } else if (patientAge == 5 && patient.calculateAge() > 23 && patient.calculateAge() < 66) {
                patients.add(patient);
                if (patient.getPatientStatus() == PatientStatus.died) {
                    ++diedGroup;
                }
            } else if (patientAge == 6 && patient.calculateAge() > 65) {
                patients.add(patient);
                if (patient.getPatientStatus() == PatientStatus.died) {
                    ++diedGroup;
                }
            }
        }

        ArrayList<Patient> dieds = new ArrayList<>();
        dieds.add(new Patient());
        if (patients.size() != 0)
            for (Patient patient : dieds) {
                patient.setName(Float.toString(((float) diedGroup / (float) patients.size()) * 100));
            }
        else
            for (Patient patient : dieds) {
                patient.setName("Sick 0");
            }
        model.put("dieds", dieds);

        model.put("patients", patients);
        return "filterByAge";
    }
}
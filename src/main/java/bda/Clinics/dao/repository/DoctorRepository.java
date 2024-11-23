package bda.Clinics.dao.repository;

import bda.Clinics.dao.model.entity.Doctor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> , JpaSpecificationExecutor<Doctor> {
    @EntityGraph(value = "doctor.clinics")
    List<Doctor> findAll();
    Optional<Doctor> findByFullName(String fullName);
    @Query("SELECT MAX(d.doctorId) FROM Doctor d")
    Long findMaxDoctorId();
    Doctor getDoctorByDoctorId(Long doctorId);
    List<Doctor> findByIsActiveFalse();
    @Query("SELECT DISTINCT d.speciality FROM Doctor d WHERE d.speciality IS NOT NULL")
    List<String> findDistinctSpecialities();

    @EntityGraph(value = "doctor.clinics", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Doctor> findById(Long id);

    Optional<Doctor> findByFullNameAndSpeciality(String fullName, String speciality);

    Doctor findBySpeciality(String specialityName);

    Optional<Doctor> findBySpecialityAndFullName(String speciality, String fullName);
}

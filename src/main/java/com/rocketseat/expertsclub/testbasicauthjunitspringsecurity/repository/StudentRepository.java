package com.rocketseat.expertsclub.testbasicauthjunitspringsecurity.repository;

import com.rocketseat.expertsclub.testbasicauthjunitspringsecurity.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}

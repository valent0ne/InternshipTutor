package it.univaq.we.internshipTutor.repository;

import com.sun.xml.internal.bind.v2.model.core.ID;
import it.univaq.we.internshipTutor.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

     List<Student> findAll();

     Student findStudentById(Long id);

    <S extends Student> S save(S student);

}
package it.univaq.we.internshipTutor.repository;

import it.univaq.we.internshipTutor.model.Company;
import it.univaq.we.internshipTutor.model.Internship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InternshipRepository extends JpaRepository<Internship, Long> {

    List<Internship> findAll();

    List<Internship> findInternshipsByActiveTrueOrderByIdDesc();

    Page<Internship> findInternshipsByActiveTrueOrderByIdDesc(Pageable pageable);

    Page<Internship> findAll(Pageable pageable);

    Internship findInternshipById(Long id);

    <S extends Internship> S save(S internship);

    void deleteInternshipById(Long id);

    //List of all active internships published by a given company
    List<Internship> findInternshipsByCompanyAndActiveTrue(Company c);

    //List of all inactive internships published by a given company
    List<Internship> findInternshipsByCompanyAndActiveFalse(Company c);

    @Query(value = "select * from " +
            "internship where " +
            "active=1 and" +
            "((upper(title) like CONCAT('%', upper(:q1), '%')) or " +
            "(upper(address) like CONCAT('%', upper(:q2), '%')) or " +
            "(upper(city) like CONCAT('%', upper(:q3), '%')) or " +
            "(upper(province) like CONCAT('%', upper(:q4), '%')) or " +
            "(upper(state) like CONCAT('%', upper(:q5), '%')) or " +
            "(upper(details_en_gb) like CONCAT('%', upper(:q6), '%')) or " +
            "(upper(mode_en_gb) like CONCAT('%', upper(:q7), '%')) or " +
            "(upper(goals_en_gb) like CONCAT('%', upper(:q8), '%')) or " +
            "(upper(facilitations) like CONCAT('%', upper(:q9), '%'))) order by id desc", nativeQuery = true)
    Page<Internship> findInternshipByQuery(Pageable pageable,
                                           @Param("q1") String q1, @Param("q2") String q2, @Param("q3") String q3,
                                           @Param("q4") String q4, @Param("q5") String q5, @Param("q6") String q6 ,
                                           @Param("q7") String q7, @Param("q8") String q8, @Param("q9") String q9);

}

package it.univaq.we.internshipTutor.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "student_internship")
public class StudentInternship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Transient
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internship_id", nullable = false)
    private Internship internship;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;

    @Column(name = "cfu", nullable = true)
    private Integer cfu;

    @Column(name = "review", nullable = true)
    private Integer review;

    @Column(name = "accepted", nullable = true)
    private Byte accepted;

    public StudentInternship() {}

    public StudentInternship(UUID uuid) { setUuid(uuid); }

    public int getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Internship getInternship() {
        return internship;
    }

    public void setInternship(Internship internship) {
        this.internship = internship;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Integer getCfu() {
        return cfu;
    }

    public void setCfu(Integer cfu) {
        this.cfu = cfu;
    }

    public Integer getReview() {
        return review;
    }

    public void setReview(Integer review) {
        this.review = review;
    }

    public Byte getAccepted() {
        return accepted;
    }

    public void setAccepted(Byte accepted) {
        this.accepted = accepted;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof StudentInternship))
            return false;
        return getUuid().equals(((StudentInternship) obj).getUuid());
    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }
}

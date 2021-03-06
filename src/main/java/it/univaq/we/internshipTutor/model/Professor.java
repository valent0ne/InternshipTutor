package it.univaq.we.internshipTutor.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Transient
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    @NotNull(message = "this field is mandatory")
    private Department department;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "professor")
    private List<StudentInternship> studentInternships;

    @Column(name = "first_name", nullable = false, length = 255)
    @NotEmpty
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 255)
    @NotEmpty
    private String lastName;

    @Column(name = "email", nullable = false, length = 255)
    @Email
    @NotEmpty
    private String email;

    @Column(name = "phone_number", nullable = false, length = 255)
    @NotEmpty
    private String phoneNumber;

    public Professor() {}

    public Professor(UUID uuid) { setUuid(uuid); }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<StudentInternship> getStudentInternships() {
        return studentInternships;
    }

    public void setStudentInternships(List<StudentInternship> studentInternships) {
        this.studentInternships = studentInternships;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        if (!(obj instanceof Professor))
            return false;
        return getUuid().equals(((Professor) obj).getUuid());
    }

    @Override
    public int hashCode() {
        if (this.getUuid() == null){
            this.setUuid(UUID.randomUUID());
        }
        return getUuid().hashCode();
    }

    //Return info's about professor
    public String getInfo(){ return this.getFirstName() + " "+ this.getLastName()+" - "+this.getDepartment().getName(); }
}

package it.univaq.we.internshipTutor.controller;

import it.univaq.we.internshipTutor.model.*;
import it.univaq.we.internshipTutor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.reflect.ConstructorDelegate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    ProfessorService professorService;

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @Autowired
    StudentService studentService;

    @Autowired
    StudentInternshipService studentInternshipService;

    @Autowired
    InternshipService internshipService;

    @RequestMapping(value = {"/admin/dashboard"}, method = RequestMethod.GET)
    public String renderAdminDashboard(ModelMap model) {

        model.addAttribute("userType",  "Admin");

        List<User> users = userService.findAll();
        int numUsers = users.size();
        model.addAttribute("numUsers", numUsers);

        List<Company> companiesAwaitingForApproval = companyService.findCompaniesByActiveFalse();
        int numCompaniesAwaitingForApproval = companiesAwaitingForApproval.size();
        model.addAttribute("numCompaniesAwaitingForApproval", numCompaniesAwaitingForApproval);

        List<Company> companies = companyService.findAll();
        int numCompanies = companies.size();
        model.addAttribute("numCompanies", numCompanies);

        List<Student> students = studentService.findAll();
        int numStudents = students.size();
        model.addAttribute("numStudents", numStudents);

        model.addAttribute("numAdmins", numUsers - numCompanies - numStudents);

        List<IProfessorInternshipCountProjection> mostRequestedProfessors = professorService.mostRequestedProfessors(10);
        model.addAttribute("mostRequestedProfessors", mostRequestedProfessors);

        List<ICompanyStudentInternshipCountProjection> companiesWithMostStudents = companyService.companiesWithMostStudents(10);
        model.addAttribute("companiesWithMostStudents", companiesWithMostStudents);

        List<IBestCompanyProjection> bestCompanies = companyService.bestCompanies(10);
        model.addAttribute("bestCompanies", bestCompanies);

        List<IWorstCompanyProjection> worstCompanies = companyService.worstCompanies(10);
        model.addAttribute("worstCompanies", worstCompanies);

        return "dashboard_admin";
    }

    @RequestMapping(value = {"/student/dashboard"}, method = RequestMethod.GET)
    public String renderStudentDashboard(ModelMap model, Principal principal) {

        model.addAttribute("userType",  "Student");

        User user = userService.findUserByEmail(principal.getName());
        Long userId = user.getId();

        User u = userService.findUserById(userId);
        Student s = u.getStudent();
        model.addAttribute("user", u);

        List<StudentInternship> internshipsAwaitingForApproval = studentInternshipService.internshipsAwaitingForApproval(s);
        model.addAttribute("internshipsAwaitingForApproval", internshipsAwaitingForApproval);

        List<StudentInternship> ongoingInternships = studentInternshipService.ongoingInternshipsByStudent(s);
        model.addAttribute("ongoingInternships", ongoingInternships);

        List<StudentInternship> completedInternships = studentInternshipService.completedInternshipsByStudent(s);
        model.addAttribute("completedInternships", completedInternships);

        return "dashboard_student";
    }

    @RequestMapping(value = {"/company/dashboard"}, method = RequestMethod.GET)
    public String renderCompanyDashboard(ModelMap model, Principal principal) {

        model.addAttribute("userType",  "Company");

        User user = userService.findUserByEmail(principal.getName());
        Long userId = user.getId();

        User u = userService.findUserById(userId);
        Company c = u.getCompany();
        model.addAttribute("user", u);

        List<Internship> activeInternships = internshipService.findActiveInternships(c);
        model.addAttribute("activeInternships", activeInternships);

        List<Internship> inactiveInternships = internshipService.findInactiveInternships(c);
        model.addAttribute("inactiveInternships", inactiveInternships);

        List<StudentInternship> ongoingInternships = studentInternshipService.ongoingInternshipsByCompany(c);
        model.addAttribute("ongoingInternships", ongoingInternships);

        List<StudentInternship> completedInternships = studentInternshipService.completedInternshipsByCompany(c);
        model.addAttribute("completedInternships", completedInternships);

        return "dashboard_company";
    }

}

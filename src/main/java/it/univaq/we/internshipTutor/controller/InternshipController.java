package it.univaq.we.internshipTutor.controller;


import it.univaq.we.internshipTutor.model.Internship;
import it.univaq.we.internshipTutor.model.Company;
import it.univaq.we.internshipTutor.model.Popup;
import it.univaq.we.internshipTutor.service.CompanyService;
import it.univaq.we.internshipTutor.service.InternshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import static it.univaq.we.internshipTutor.model.Popup.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@Controller
public class InternshipController {

    @Autowired
    InternshipService internshipService;

    @Autowired
    CompanyService companyService;

    @RequestMapping(value={"/create/internship"}, method = RequestMethod.POST)
    public String doCreate(@Valid @ModelAttribute("internship") Internship internship, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // if there are errors during the binding (e.g. NotNull, Min, etc.)
            // redirect to the form displaying the errors
            // add error message in the model as flash attribute
            redirectAttributes.addFlashAttribute("popup", new Popup("warning", WAR_MSG_EN));
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.internship", result);
            redirectAttributes.addFlashAttribute("internship", internship);

            return "redirect:/create/internship";
        }

        try{
            internshipService.save(internship);
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("popup", new Popup("warning", WAR_MSG_EN));
            return "redirect:/create/internship";
        }


        // add success message in the model as flash attribute
        redirectAttributes.addFlashAttribute("popup", new Popup());

        // render Create form
        return "redirect:/create/internship";
    }



    @RequestMapping(value={"/update/internship"}, method = RequestMethod.POST)
    public String doUpdate(@Valid @ModelAttribute("internship") Internship internship, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // if there are errors during the binding (e.g. NotNull, Min, etc.)
            // redirect to the form displaying the errors
            redirectAttributes.addFlashAttribute("popup", new Popup("warning", WAR_MSG_EN));
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.internship", result);
            redirectAttributes.addFlashAttribute("internship", internship);
            return "redirect:/update/internship/" + internship.getId();
        }

        try{
            internshipService.save(internship);
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("popup", new Popup("warning", WAR_MSG_EN));
            return "redirect:/update/internship/" + internship.getId();
        }

        // add success message in the model
        redirectAttributes.addFlashAttribute("popup", new Popup());
        // render Update form
        return "redirect:/update/internship/" + internship.getId();
    }



    @RequestMapping(value={"/delete/internship/{id}"}, method = RequestMethod.POST)
    public String doDelete(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
        if (id == null || id < 0) {
            // if there are errors during the binding (e.g. NotNull, Min, etc.)
            // redirect to the form displaying the errors
            // add error message in the model
            redirectAttributes.addFlashAttribute("popup", new Popup("warning", WAR_MSG_EN));
            return "redirect:/update/internship/" + id;
        }

        try{
            internshipService.deleteInternshipById(id);
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("popup", new Popup("warning", WAR_MSG_EN));
            return "redirect:/update/internship/" + id;
        }

        // add success message in the model
        redirectAttributes.addFlashAttribute("popup", new Popup());
        return "redirect:/create/internship";
    }


    @RequestMapping(value={"/create/internship"}, method = RequestMethod.GET)
    public String renderCreate(ModelMap model) {

        if(!model.containsAttribute("internship")){
            model.addAttribute("internship", new Internship(UUID.randomUUID()));
        }
        List<Internship> internships = internshipService.findAll();
        model.addAttribute("internships", internships);
        List<Company> companies = companyService.findAll();
        model.addAttribute("companies", companies);

        return "internship_create";
    }


    @RequestMapping(value={"/update/internship/{id}"}, method = RequestMethod.GET)
    public String renderUpdate(ModelMap model, @PathVariable(value = "id") Long id) {

        Internship i = internshipService.findInternshipById(id);

        if(!model.containsAttribute("internship")){
            model.addAttribute("internship", i);
        }

        List<Internship> internships = internshipService.findAll();
        model.addAttribute("internships", internships);
        List<Company> companies = companyService.findAll();
        model.addAttribute("companies", companies);

        return "internship_update";
    }

}
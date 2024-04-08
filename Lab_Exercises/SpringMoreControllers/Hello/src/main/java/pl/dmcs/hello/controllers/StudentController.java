package pl.dmcs.hello.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.dmcs.hello.model.Student;

@Controller
public class StudentController {
    @RequestMapping("/student")
    public String student(Model model){
        model.addAttribute("message", "Simple String from StudentController");
        Student newStudent = new Student();
        model.addAttribute("student", newStudent);
        return "student";
    }
    @RequestMapping(value = "/addStudent.html", method = RequestMethod.POST)
    public String evaluateQuadratic(@ModelAttribute("student") Student student){
        System.out.println(student.getFirstname() + " " + student.getLastname());
        return "redirect:student";
    }
}

package pl.dmcs.hello.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.dmcs.hello.model.Quadratic;

@Controller
public class QuadraticController {
    @RequestMapping("/quadratic")
    public String quadratic(Model model){
        model.addAttribute("message", "Quadratic roots evaluator");
        Quadratic quadratic = new Quadratic();
        model.addAttribute("quadratic", quadratic);
        return "quadratic";
    }
    @RequestMapping(value = "/evalQuadratic.html", method = RequestMethod.POST)
    public String evaluateResult(@ModelAttribute("quadratic") Quadratic quadratic){
        System.out.println(quadratic.evaluate());
        return "redirect:quadratic";
    }
}

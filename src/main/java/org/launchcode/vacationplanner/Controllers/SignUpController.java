package org.launchcode.vacationplanner.Controllers;

import org.launchcode.vacationplanner.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Dan on 7/5/2017.
 */

@Controller
@RequestMapping(value="vacation/user")
public class SignUpController {


    @RequestMapping(value="signup")
    public String userSignUp(Model model) {
        model.addAttribute("title", "User Sign Up");
        model.addAttribute("user", new User());

        return "user/signup";
    }
}

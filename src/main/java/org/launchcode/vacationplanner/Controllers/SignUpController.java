package org.launchcode.vacationplanner.Controllers;

import org.launchcode.vacationplanner.Models.Data.UserDao;
import org.launchcode.vacationplanner.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Dan on 7/5/2017.
 */

@Controller
@RequestMapping(value="vacation/user")
public class SignUpController {

    @Autowired
    UserDao userDao;

    @RequestMapping(value="signup", method= RequestMethod.GET)
    public String userSignUp(Model model) {
        model.addAttribute("title", "User Sign Up");
        model.addAttribute("user", new User());

        return "user/signup";
    }

    @RequestMapping(value="signup", method = RequestMethod.POST)
    public String processUserSignup(Model model, String verify, @ModelAttribute @Valid User newUser, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "User Sign Up");
            model.addAttribute("user", newUser);

            return "user/signup";
        }

        else if (newUser.getPassword().equals(verify)) {
            userDao.save(newUser);
            return "redirect:/vacation/";
        } else {

            model.addAttribute("verify", "The passwords do not match.");
            newUser.setPassword("");
            model.addAttribute("user", newUser);

            return "user/signup";
        }
    }

    @RequestMapping(value="login", method=RequestMethod.GET)
    public String userLogIn(Model model) {
        model.addAttribute("title", "Log In");

        return "user/login";
    }

    @RequestMapping(value="login", method=RequestMethod.POST)
    public String processUserLogIn(Model model, String username, String password) {
        Iterable<User> users = userDao.findAll();
        for(User user : users) {
            if(user.getUsername().equals(username)) {
                if(user.getPassword().equals(password)) {
                    return "redirect:/vacation/";
                } else {
                    model.addAttribute("password", "Password is incorrect");

                    return "user/login";
                }

            }


        }
        model.addAttribute("username", "Username does not exist");

        return "user/login";
    }

}

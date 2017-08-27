package org.launchcode.vacationplanner.Controllers;

import org.launchcode.vacationplanner.Models.Data.TripDao;
import org.launchcode.vacationplanner.Models.Data.UserDao;
import org.launchcode.vacationplanner.Models.Helpers.LogInHelper;
import org.launchcode.vacationplanner.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.launchcode.vacationplanner.Models.Helpers.CookieHelper.clearCookieSession;
import static org.launchcode.vacationplanner.Models.Helpers.CookieHelper.setCookieSession;
import static org.launchcode.vacationplanner.Models.Helpers.LogInHelper.hasPermission;
import static org.launchcode.vacationplanner.Models.Helpers.SessionHelper.buildSession;
import static org.launchcode.vacationplanner.Models.Helpers.SessionHelper.clearSession;

/**
 * Created by Dan on 7/5/2017.
 */

@Controller
@RequestMapping(value="vacation/user")
public class SignUpController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TripDao tripDao;

    @RequestMapping(value="signup", method= RequestMethod.GET)
    public String userSignUp
            (Model model) {
        model.addAttribute("title", "User Sign Up");
        model.addAttribute("user", new User());

        return "user/signup";
    }

    @RequestMapping(value="signup", method = RequestMethod.POST)
    public String processUserSignup
            (Model model, String verify, @ModelAttribute @Valid User newUser,
             Errors errors, HttpServletResponse response, HttpSession session) {


        if (errors.hasErrors()) {
            model.addAttribute("title", "User Sign Up");
            model.addAttribute("user", newUser);

            return "user/signup";
        }
        //user signup is successful
        else if (newUser.getPassword().equals(verify)) {

            String hex = LogInHelper.Hash(newUser.getPassword());
            newUser.setPassword(hex);
            userDao.save(newUser);

            setCookieSession(response, newUser);
            buildSession(session, newUser);

            return "redirect:/vacation/";
        } else {

            model.addAttribute("verify", "The passwords do not match.");
            newUser.setPassword("");
            model.addAttribute("user", newUser);

            return "user/signup";
        }
    }

    @RequestMapping(value="login", method=RequestMethod.GET)
    public String userLogIn
            (Model model) {
        model.addAttribute("title", "Log In");

        return "user/login";
    }

    @RequestMapping(value="login", method=RequestMethod.POST)
    public String processUserLogIn
            (HttpServletResponse response, HttpSession session, Model model, String username, String password) {
        User theUser = LogInHelper.findUserByCredentials(userDao, username, password);
        if(theUser != null) {
            setCookieSession(response, theUser);
            buildSession(session, theUser);

            return "redirect:/vacation/mytrips";
        }

        model.addAttribute("username", "Username and password do not match");
        return "user/login";
    }

    @RequestMapping(value="logout")
    public String userLogOut
            (HttpServletResponse response, HttpSession session, Model model) {
        //clears all cookies and session data
        clearCookieSession(response);
        clearSession(session);

        return "user/logout";
    }

    @RequestMapping(value="account/{id}", method=RequestMethod.GET)
    public String userAccount
            (HttpServletRequest request, Model model, @PathVariable int id) {
        if (hasPermission(request, userDao, id)) {
                model.addAttribute("title", "Your Account Information");
                model.addAttribute("user", userDao.findOne(id));
                return "user/acc-info";
            } else {
                return "redirect:/vacation/user/no-permission";
            }

    }

    @RequestMapping(value="account/{id}", method=RequestMethod.POST)
    public String updateAccount(Model model, @ModelAttribute @Valid User user, Errors errors, @PathVariable int id) {
        User editedUser = userDao.findOne(id); //find user to be edited by its id

        if (errors.hasErrors()) {
            model.addAttribute("title", "Your Account Information");
            model.addAttribute("user", user);
            return "user/acc-info";
        }
        //update fields in the database with new input from user

        editedUser.setEmail(user.getEmail());
        editedUser.setLocation(user.getLocation());

        userDao.save(editedUser);

        return "redirect:/vacation/mytrips";
    }

    // in instances where LogInHelper.hasPermission() returns false, the user will be rerouted to this handler
    @RequestMapping(value="no-permission")
    public String noPermission() {
        return "user/no-permission";
    }

}

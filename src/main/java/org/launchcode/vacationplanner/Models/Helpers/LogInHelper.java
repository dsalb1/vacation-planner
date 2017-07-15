package org.launchcode.vacationplanner.Models.Helpers;

import org.launchcode.vacationplanner.Models.Data.UserDao;
import org.launchcode.vacationplanner.Models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Dan on 7/13/2017.
 */

//helper methods for authenticating and validating sessions
public class LogInHelper extends HttpServlet {

    public static User findUserByCredentials(UserDao userDao, String username, String password) {
        Iterable<User> users = userDao.findAll();
        User theUser = new User();


        String hex = LogInHelper.Hash(password);

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(hex)) {
                theUser = user;
                return theUser;
            }
        }
        theUser = null;
        return theUser;
    }

    public static User findUserById(UserDao userDao, Integer id) {
        User loggedInUser = userDao.findOne(id);
        return loggedInUser;
    }

    public static String Hash(String password) {
        String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
        return sha256hex;
    }

    public static Boolean isLoggedIn(HttpServletRequest request, UserDao userDao) {
        String idString = CookieHelper.getCookieValue(request, "id");
        String hex = CookieHelper.getCookieValue(request, "hex");

        if (idString != null) {
            Integer id = Integer.parseInt(idString);
            User theUser = userDao.findOne(id);
            if (theUser.getPassword().equals(hex)) {
                return true;
            }
        }
        return false;
    }
}





package org.launchcode.vacationplanner.Models.Helpers;

import org.launchcode.vacationplanner.Models.Data.UserDao;
import org.launchcode.vacationplanner.Models.Trip;
import org.launchcode.vacationplanner.Models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static org.launchcode.vacationplanner.Models.Helpers.CookieHelper.getCookieValue;
import static org.launchcode.vacationplanner.Models.Helpers.TripHelper.getTripsByUser;

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

    public static String Hash(String text) {
        String sha256hex = sha256Hex(text);
        return sha256hex;
    }

    //checks session cookies to see if a user is logged in
    public static Boolean isLoggedIn(HttpServletRequest request, UserDao userDao) {
        String idString = getCookieValue(request, "id");
        String hex = getCookieValue(request, "hex");

        if (idString != null) {
            Integer id = Integer.parseInt(idString);
            User theUser = userDao.findOne(id);
            if (theUser.getPassword().equals(hex)) {
                return true;
            }
        }
        return false;
    }

    //after determining a user is logged in, checks the user's trips against a specific trip id to determine if user has permissions to edit that trip (i.e., that user OWNS that trip)
    public static Boolean hasPermission(HttpServletRequest request, UserDao userDao, int id) {
        if (isLoggedIn(request, userDao)) {
            Iterable<Trip> trips = getTripsByUser(userDao, request);
            for (Trip trip : trips) {
                if (trip.getId() == id) {
                    return true;
                }
            }

        }
        return false;
    }

}





package org.launchcode.vacationplanner.Models.Helpers;

import org.launchcode.vacationplanner.Models.Data.UserDao;
import org.launchcode.vacationplanner.Models.Trip;
import org.launchcode.vacationplanner.Models.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Dan on 7/15/2017.
 */
public class TripHelper {

    public static Iterable<Trip> getTripsByUser(UserDao userDao, HttpServletRequest request) {
        Integer id = Integer.parseInt(CookieHelper.getCookieValue(request, "id"));
        User loggedInUser = userDao.findOne(id);
        return loggedInUser.getTrips();
    }

    public static Iterable<Trip> sessionGetTripsByUser(UserDao userDao, HttpServletRequest request) {
        int id = Integer.parseInt(request.getSession().getAttribute("id").toString());
        return userDao.findOne(id).getTrips();

    }

}

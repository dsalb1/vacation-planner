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
}

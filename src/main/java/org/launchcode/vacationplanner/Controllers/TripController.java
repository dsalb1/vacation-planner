package org.launchcode.vacationplanner.Controllers;

import org.launchcode.vacationplanner.Models.Data.PointOfInterestDao;
import org.launchcode.vacationplanner.Models.Data.TripDao;
import org.launchcode.vacationplanner.Models.Data.UserDao;
import org.launchcode.vacationplanner.Models.Helpers.CookieHelper;
import org.launchcode.vacationplanner.Models.PointOfInterest;
import org.launchcode.vacationplanner.Models.Trip;
import org.launchcode.vacationplanner.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.launchcode.vacationplanner.Models.Helpers.LogInHelper.*;
import static org.launchcode.vacationplanner.Models.Helpers.TripHelper.sessionGetTripsByUser;

/**
 * Created by Dan on 7/5/2017.
 */

@Controller
@RequestMapping(value="vacation")
public class TripController {

    @Autowired
    private TripDao tripDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PointOfInterestDao pointOfInterestDao;

    /*
    @RequestMapping(value="")
    public String index(Model model) {
        model.addAttribute("title", "Recently Added Trips");
        model.addAttribute("trips", tripDao.findAll());
        model.addAttribute("index");
        return "trip/index";
    }
    */

    @RequestMapping(value="")
    public String index(Model model) {
        model.addAttribute("title", "Ready To Plan Your Next Adventure?");

        return "trip/index-jumbo";
    }


    //restricted
    @RequestMapping(value="mytrips")
    public String index(Model model, HttpSession session, HttpServletRequest request) {
        //if (isLoggedIn(request, userDao)) {
        if (sessionIsLoggedIn(request, userDao)) {
            //Iterable<Trip> myTrips = getTripsByUser(userDao, request);
            Iterable<Trip> myTrips = sessionGetTripsByUser(userDao, request);

            model.addAttribute("title", "Your Trips");
            model.addAttribute("trips", myTrips);
            return "trip/mytrips";
        }

        return "redirect:/vacation/user/login";
    }

    //restricted
    @RequestMapping(value="mytrips/add", method=RequestMethod.GET)
    public String addTripForm(Model model, HttpSession session, HttpServletRequest request) {
        //checks to see if user is logged in
        //if (isLoggedIn(request, userDao)) {
        if (sessionIsLoggedIn(request, userDao)) {
            model.addAttribute("title", "Add Trip");
            model.addAttribute(new Trip());
            return "trip/add";
            }


        return "redirect:/vacation/user/login";
    }

    @RequestMapping(value="mytrips/add", method=RequestMethod.POST)
    public String processAddTripForm(Model model, @ModelAttribute @Valid Trip newTrip, Errors errors, HttpServletRequest request) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Trip");
            model.addAttribute("trip", newTrip);

            return "trip/add";
        }

        //Find user by id and set User field in the new trip object;
        Integer id = Integer.parseInt(CookieHelper.getCookieValue(request, "id"));
        User loggedInUser = findUserById(userDao, id);
        newTrip.setUser(loggedInUser);

        tripDao.save(newTrip);

        return "redirect:";
    }

    //restricted
    @RequestMapping(value="edit/{id}", method=RequestMethod.GET)
    public String editTripForm(Model model, @PathVariable int id, HttpSession session, HttpServletRequest request) {
        //if (hasPermission(request, userDao, id)) {
        if (sessionHasPermission(request, userDao, id)) {
            model.addAttribute("title", "Edit Trip");
            model.addAttribute(tripDao.findOne(id));

            return "trip/edit";
        }
        return "redirect:/vacation/user/no-permission";
    }

    @RequestMapping(value="edit/{id}", method=RequestMethod.POST)
    public String processEditTripForm(Model model, @ModelAttribute @Valid Trip trip, Errors errors, @PathVariable int id) {

        Trip editedTrip = tripDao.findOne(id); //find trip to be edited by its id

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Trip");
            model.addAttribute("trip", trip);

            return "trip/edit";
        }
        //update fields in the database with new input from user
        editedTrip.setName(trip.getName());
        editedTrip.setDescription(trip.getDescription());
        editedTrip.setLengthNight(trip.getLengthNight());

        tripDao.save(editedTrip);

        return "redirect:/vacation/mytrips";
    }

    //check for restriction
    @RequestMapping(value="trip/{id}")
    public String tripView(Model model, HttpSession session, HttpServletRequest request, @PathVariable int id) {

        model.addAttribute("trip", tripDao.findOne(id));

        //if (hasPermission(request, userDao, id)) {
        if (sessionHasPermission(request, userDao, id)) {
            return "trip/view-logged-in";
        }

        model.addAttribute("title", "Your Trip");

        return "trip/view";
    }

    //restricted
    @RequestMapping(value="add-item/{id}", method=RequestMethod.GET)
    public String addPointOfInterest(Model model, @PathVariable int id, HttpSession session, HttpServletRequest request) {
        //if (hasPermission(request, userDao, id)) {
        if (sessionHasPermission(request, userDao, id)) {
            model.addAttribute("title", "Add an Activity");
            model.addAttribute("point", new PointOfInterest());
            model.addAttribute("trip", tripDao.findOne(id));

            return "trip/add-item";
        }
        return "redirect:/vacation/user/no-permission";
    }

    @RequestMapping(value="add-item/{id}", method=RequestMethod.POST)
    public String processPointOfInterest(Model model, @PathVariable int id, @ModelAttribute @Valid PointOfInterest myPoint, Errors errors) {

        Trip trip = tripDao.findOne(id); //find trip to be edited by its id

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add an Activity");
            model.addAttribute("point", myPoint);
            model.addAttribute("trip", tripDao.findOne(id));
            model.addAttribute("nameError", "size must be between 2 and 50");
            return "trip/add-item";
        }

        //save new PointOfInterest to database and add the PointOfInterest instance to the trip
        pointOfInterestDao.save(myPoint);
        trip.addInterests(myPoint);

        tripDao.save(trip);

        return "redirect:/vacation/trip/{id}";
    }

    @RequestMapping(value="remove-item/{id}", method=RequestMethod.GET)
    public String removePointOfInterest(Model model, HttpSession session, HttpServletRequest request, @PathVariable int id) {
        //if (hasPermission(request, userDao, id)) {
        if (sessionHasPermission(request, userDao, id)) {
            model.addAttribute("title", "Remove activities");
            model.addAttribute("trip", tripDao.findOne(id));

            return "/trip/remove-activity";
        }
        return "redirect: /vacation/user/no-permission";
    }

    @RequestMapping(value="remove-item/{id}", method=RequestMethod.POST)
    public String processRemovePointOfInterest(Model model, @RequestParam(required=false) int[] pointIds, @PathVariable int id) {
            if (pointIds == null) {
                return "redirect:/vacation/remove-item/{id}";
            }
            for(int pointId : pointIds) {
                pointOfInterestDao.delete(pointId);
            }

            return "redirect:/vacation/trip/{id}";
        }

    @RequestMapping(value="mytrips/remove", method=RequestMethod.GET)
    public String removeTrip(Model model, HttpSession session, HttpServletRequest request) {
        //if (isLoggedIn(request, userDao)) {
        if (sessionIsLoggedIn(request, userDao)) {
            //Iterable<Trip> myTrips = getTripsByUser(userDao, request);
            Iterable<Trip> myTrips = sessionGetTripsByUser(userDao, request);
            model.addAttribute("title", "Remove Trips");
            model.addAttribute("trips", myTrips);
            return "trip/remove-trip";
        }

        return "redirect:/vacation/user/login";
    }

    @RequestMapping(value="mytrips/remove", method=RequestMethod.POST)
    public String processRemoveTrip(Model model, @RequestParam(required=false) int[] tripIds) {
        if (tripIds == null) {
            return "redirect:/vacation/mytrips/remove";
        }
        for (int tripId : tripIds) {
            tripDao.delete(tripId);
        }

        return "redirect:/vacation/mytrips";

    }

    @RequestMapping(value="mytrips/compare", method=RequestMethod.GET)
    public String compareTrips(Model model, HttpSession session, HttpServletRequest request) {
        //if (isLoggedIn(request, userDao)) {
        if (sessionIsLoggedIn(request, userDao)) {
            //Iterable<Trip> myTrips = getTripsByUser(userDao, request);
            Iterable<Trip> myTrips = sessionGetTripsByUser(userDao, request);
            model.addAttribute("title", "Select Two Trips To Compare");
            model.addAttribute("trips", myTrips);
            return "trip/compare-index";
        }

        return "redirect:/vacation/user/login";
    }

    @RequestMapping(value="mytrips/compare", method=RequestMethod.POST)
    public String processCompareTrips(Model model, @RequestParam(required=false) int[] tripIds) {
        List<Trip> tripsToCompare = new ArrayList<>();

        if (tripIds == null) {

            return "redirect:/vacation/mytrips/compare";
        }

        for (int id : tripIds) {
            tripsToCompare.add(tripDao.findOne(id));
        }
        model.addAttribute("title", "Comparing Trips");
        model.addAttribute("trips", tripsToCompare);
        return "trip/view-compare";
    }

}

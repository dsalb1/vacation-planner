package org.launchcode.vacationplanner.Controllers;

import org.launchcode.vacationplanner.Models.Data.PointOfInterestDao;
import org.launchcode.vacationplanner.Models.Data.TripDao;
import org.launchcode.vacationplanner.Models.PointOfInterest;
import org.launchcode.vacationplanner.Models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Dan on 7/5/2017.
 */

@Controller
@RequestMapping(value="vacation")
public class TripController {

    @Autowired
    private TripDao tripDao;

    @Autowired
    private PointOfInterestDao pointOfInterestDao;

    @RequestMapping(value="")
    public String index(Model model) {
        model.addAttribute("title", "My Trips");
        model.addAttribute("trips", tripDao.findAll());
        return "trip/index";
    }

    @RequestMapping(value="add", method=RequestMethod.GET)
    public String addTripForm(Model model) {
        model.addAttribute("title", "Add Trip");
        model.addAttribute(new Trip());

        return "trip/add";
    }

    @RequestMapping(value="add", method=RequestMethod.POST)
    public String processAddTripForm(Model model, @ModelAttribute @Valid Trip newTrip, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Trip");
            model.addAttribute("trip", newTrip);

            return "trip/add";
        }

        tripDao.save(newTrip);

        return "redirect:";
    }

    @RequestMapping(value="edit/{id}", method=RequestMethod.GET)
    public String editTripForm(Model model, @PathVariable int id) {
        model.addAttribute("title", "Edit Trip");
        model.addAttribute(tripDao.findOne(id));

        return "trip/edit";
    }

    @RequestMapping(value="edit/{id}", method=RequestMethod.POST)
    public String processEditTripForm(Model model, @ModelAttribute @Valid Trip trip, Errors errors, @PathVariable int id) {

        Trip editedTrip = tripDao.findOne(id); //find trip to be edited by its id

        if (errors.hasErrors()) {
            model.addAttribute("title", "Edit Trip");
            model.addAttribute("trip", trip);

            return "trip/edit";
        }
        //update record in the database with new input from user
        editedTrip.setName(trip.getName());
        editedTrip.setDescription(trip.getDescription());
        editedTrip.setLengthNight(trip.getLengthNight());

        tripDao.save(editedTrip);

        return "redirect:/vacation/";
    }

    @RequestMapping(value="trip/{id}")
    public String tripDisplayer(Model model, @PathVariable int id) {

        model.addAttribute("trip", tripDao.findOne(id));
        model.addAttribute("title", "Your Trip");

        return "trip/view";
    }

    @RequestMapping(value="add-item/{id}", method=RequestMethod.GET)
    public String addPointOfInterest(Model model, @PathVariable int id) {

        model.addAttribute("title", "Add a Point of Interest");
        model.addAttribute("point", new PointOfInterest());
        model.addAttribute("trip", tripDao.findOne(id));

        return "trip/add-item";
    }

    @RequestMapping(value="add-item/{id}", method=RequestMethod.POST)
    public String processPointOfInterest(Model model, @PathVariable int id, @ModelAttribute @Valid PointOfInterest myPoint, Errors errors) {

        Trip trip = tripDao.findOne(id); //find trip to be edited by its id

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add a Point of Interest");
            model.addAttribute("point", myPoint);
            model.addAttribute("trip", tripDao.findOne(id));
            model.addAttribute("nameError", "size must be between 2 and 50");
            return "trip/add-item";
        }

        //save new PointOfInterest to database and add the PointOfInterest instance to the trip
        pointOfInterestDao.save(myPoint);
        trip.addInterests(myPoint);

        tripDao.save(trip);

        return "redirect:/vacation/";
    }

}

package org.launchcode.vacationplanner.Controllers;

import org.launchcode.vacationplanner.Models.Data.PointOfInterestDao;
import org.launchcode.vacationplanner.Models.Data.TripDao;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
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

        return "trip/index";
    }

}

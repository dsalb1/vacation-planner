package org.launchcode.vacationplanner.Models.Data;

import org.launchcode.vacationplanner.Models.Trip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Dan on 7/5/2017.
 */

@Repository
@Transactional
public interface TripDao extends CrudRepository<Trip, Integer> {
}

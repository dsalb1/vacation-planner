package org.launchcode.vacationplanner.Models.Data;

import org.launchcode.vacationplanner.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Dan on 7/10/2017.
 */

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {
}

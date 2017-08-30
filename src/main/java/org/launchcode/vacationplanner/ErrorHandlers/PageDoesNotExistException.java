package org.launchcode.vacationplanner.ErrorHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such page exists") //404
public class PageDoesNotExistException extends RuntimeException {
}

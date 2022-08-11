package com.techelevator.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.NOT_FOUND, reason = "Item Not Found.")
public class ItemNotFoundException extends RuntimeException {
}

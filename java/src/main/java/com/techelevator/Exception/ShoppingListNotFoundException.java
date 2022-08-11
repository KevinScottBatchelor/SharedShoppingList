package com.techelevator.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.NOT_FOUND, reason = "Shopping List Not Found.")
public class ShoppingListNotFoundException extends RuntimeException {
}

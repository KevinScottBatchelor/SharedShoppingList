package com.techelevator.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterUserDTO {

    @NotEmpty

    private String username;
    @NotEmpty
    @Size(min = 8, message = "Password must contain one capital letter, one lower, one number, " +
            "special character minimum of 8 characters")
//    @Pattern(regexp = )
//    (?=.*[a-z])        // use positive look ahead to see if at least one lower case letter exists
//    (?=.*[A-Z])        // use positive look ahead to see if at least one upper case letter exists
//    (?=.*\d)           // use positive look ahead to see if at least one digit exists
//    (?=.*\W)           // use positive look ahead to see if at least one non-word character exists
    private String password;
    @NotEmpty
    private String confirmPassword;
    @NotEmpty(message = "Please select a role for this user.")
    private String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

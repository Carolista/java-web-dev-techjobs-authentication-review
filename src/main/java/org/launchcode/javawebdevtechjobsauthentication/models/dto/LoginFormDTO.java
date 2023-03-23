package org.launchcode.javawebdevtechjobsauthentication.models.dto;

import javax.validation.constraints.*;

public class LoginFormDTO {

    @NotNull(message = "Username is required.")
    @NotBlank(message = "Username is required.")
    @Size(min = 3, max = 30, message = "Invalid username. Must be 3-30 characters long.")
    private String username;

    @NotNull(message = "Password is required.")
    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 30, message = "Invalid username. Must be between 3 and 20 characters.")
    private String password;

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
}

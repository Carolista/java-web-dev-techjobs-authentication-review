package org.launchcode.javawebdevtechjobsauthentication.models.dto;

import javax.validation.constraints.*;

public class RegistrationFormDTO extends LoginFormDTO {

    @NotNull(message = "Password is required.")
    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 30, message = "Invalid username. Must be between 3 and 20 characters.")
    private String verifyPassword;

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
}

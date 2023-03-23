package org.launchcode.javawebdevtechjobsauthentication.controllers;

import org.launchcode.javawebdevtechjobsauthentication.models.User;
import org.launchcode.javawebdevtechjobsauthentication.models.data.UserRepository;
import org.launchcode.javawebdevtechjobsauthentication.models.dto.RegistrationFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    // The key to store user IDs
    private static final String userSessionKey = "user";

    // Stores key/value pair with session key and user ID
    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    // Look up user with key
    public User getUserFromSession(HttpSession session) {

        // Get user ID from database using key
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        // Get optional back from database
        Optional<User> userOpt = userRepository.findById(userId);

        // Early return with null if user not found
        if (userOpt.isEmpty()) {
            return null;
        }

        // Return user object (unboxed from optional)
        return userOpt.get();
    }

    // Handlers for registration page
    @GetMapping("/register")
    public String displayRegistrationForm(Model model) {
        model.addAttribute(new RegistrationFormDTO()); // automatically creates variable registrationFormDTO
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegistrationFormDTO registrationFormDTO,
                                          Errors errors,
                                          HttpServletRequest request,
                                          Model model) {

        // Send user back to form if errors are found
        if (errors.hasErrors()) {
            return "register";
        }

        // Look up user in database using username they provided in the form
        User existingUser = userRepository.findByUsername(registrationFormDTO.getUsername());

        // Send user back to form if username already exists
        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            return "register";
        }

        // Send user back to form if passwords didn't match
        String password = registrationFormDTO.getPassword();
        String verifyPassword = registrationFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            return "register";
        }

        // OTHERWISE, save new username and hashed password in database, start a new session, and redirect to home page
        User newUser = new User(registrationFormDTO.getUsername(), registrationFormDTO.getPassword());
        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);
        return "redirect:";
    }




    // Handlers for login page

}

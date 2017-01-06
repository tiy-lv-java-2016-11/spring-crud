package com.theironyard.controllers;

import com.theironyard.models.User;
import com.theironyard.repositories.BookRepository;
import com.theironyard.repositories.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by melmo on 12/29/16.
 */
@Controller
public class UserController {
    public static final String SESSION_USER_ID = "currentUserId";

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password, String type, final RedirectAttributes redAtt) throws PasswordStorage.CannotPerformOperationException, PasswordStorage.InvalidHashException {
        User currentUser;
        String message = null;
        if (type.equals("register")){
            if (userRepository.findByUsername(username) == null){
                currentUser = new User(username, PasswordStorage.createHash(password));
                userRepository.save(currentUser);
                session.setAttribute(SESSION_USER_ID, currentUser.getId());
                message = "Account created for: " + currentUser.getUsername();
            }
            else {
                message = "That username is already taken";
            }
        }
        if (type.equals("login")){
            currentUser = userRepository.findByUsername(username);
            if (currentUser != null && PasswordStorage.verifyPassword(password, currentUser.getPassword())){
                session.setAttribute(SESSION_USER_ID, currentUser.getId());
            }
            else {
                message = "Username/Password is incorrect";
            }
        }
        redAtt.addFlashAttribute("message", message);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}

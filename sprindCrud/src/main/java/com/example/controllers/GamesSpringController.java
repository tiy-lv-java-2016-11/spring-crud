package com.example.controllers;

import com.example.Utillities.PasswordStorage;
import com.example.entities.Game;
import com.example.entities.User;
import com.example.repositories.GameRepository;
import com.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.List;

/**
 * Created by sparatan117 on 1/3/17.
 */

@Controller
public class GamesSpringController {
    public static final String SESSION_USERNAME = "username";

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model, String genre, String platform){
        String username = (String) session.getAttribute(SESSION_USERNAME);
        List<Game> games = gameRepository.findAll();
        if(username != null){
        }
        model.addAttribute("user", session.getAttribute(SESSION_USERNAME));
        return "Home";
    }


    @RequestMapping(path = "/Login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password) throws Exception{
        User user = userRepository.findFirstByName(username);
        if(user == null){
            user = new User(username, PasswordStorage.createHash(password));
            userRepository.save(user);
        }
        else if(!PasswordStorage.verifyPassword(password, user.getPassword())){
            throw new Exception("incorrect password");
        }
        session.setAttribute(SESSION_USERNAME, username);
        return "redirect:/";

    }
}

package com.spring.crud.controllers;

import com.spring.crud.utilities.PsswordStorage;
import com.spring.crud.repositories.CreateWeaponRepository;
import com.spring.crud.repositories.UserRepository;
import com.spring.crud.entities.Weapons;
import com.spring.crud.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by darionmoore on 12/30/16.
 */
@Controller
public class WeaponSpringController {
    public static final String USERNAME = "userName";
    @Autowired
    CreateWeaponRepository createWeaponRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        String userName = (String)session.getAttribute(USERNAME);
        List<Weapons> weapons = createWeaponRepository.findAllByOrderByDateTimeDesc();

        if(userName != null){
            User user = userRepository.findFirstByName(userName);
            model.addAttribute("user", user);
            model.addAttribute("now", LocalDateTime.now());
        }
        model.addAttribute("weapons", weapons);
        return "home";
    }

    @RequestMapping(path = "/create-weapon", method = RequestMethod.POST)
    public String createWeapons(HttpSession session, String description){
        String userName = (String) session.getAttribute(USERNAME);
        if(userName != null){
            User user = userRepository.findFirstByName(userName);
            Weapons weapons = new Weapons(description, user);
            createWeaponRepository.save(weapons);
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password ) throws Exception{
        User user = userRepository.findFirstByName(userName);
        if(user == null){
            user = new User(userName, PsswordStorage.createHash(password));
            userRepository.save(user);
        }
        session.setAttribute(USERNAME, userName);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}

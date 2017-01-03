package com.spring.crud.controllers;

import com.spring.crud.entities.User;
import com.spring.crud.entities.Weapons;
import com.spring.crud.repositories.CreateWeaponRepository;
import com.spring.crud.repositories.UserRepository;
import com.spring.crud.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
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

    @PostConstruct
    public void init(){
        if(userRepository.count() == 0){
            User user = new User("this", "guy");
            userRepository.save(user);
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        String userName = (String)session.getAttribute(USERNAME);
        List<Weapons> weaponses = createWeaponRepository.findAll();

        if(userName != null){
            User user = userRepository.findFirstByName(userName);
            model.addAttribute("user", session.getAttribute(USERNAME));

        }
        model.addAttribute("AddWeapon", weaponses);
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
    public String login(HttpSession session, String name, String password ) throws Exception{
        User user = userRepository.findFirstByName(name);
        if(user == null){
            user = new User(name, PasswordStorage.createHash(password));
            userRepository.save(user);
        } else if (!PasswordStorage.verifyPassword(password, user.getPassword())){
            throw new Exception("Your name or password is incorrect");
        }
        session.setAttribute(USERNAME, user);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}

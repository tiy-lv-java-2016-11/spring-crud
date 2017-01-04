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
import java.util.List;

@Controller
public class GamesSpringController {
    public static final String SESSION_USERNAME = "username";

    @Autowired
    GameRepository gameRepository;

    @Autowired
    UserRepository userRepository;


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model){
        String username = (String) session.getAttribute(SESSION_USERNAME);
        User user = userRepository.findFirstByUsername(username);


        if(user != null) {
            model.addAttribute("user", user);

            List<Game> games = gameRepository.findAll();
            model.addAttribute("games", games);

            List<Game> usergames = gameRepository.findByUser(user);
            model.addAttribute("usergames", usergames);
        }
        return "home";
    }


    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password) throws Exception{
        User user = userRepository.findFirstByUsername(username);
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
    @RequestMapping(path = "/add-game", method = RequestMethod.POST)
    public String addgame(HttpSession session, String name, String genre, String platform){
        String username = (String)session.getAttribute(SESSION_USERNAME);
        User user = userRepository.findFirstByUsername(username);
        if(user != null){
            Game game = new Game(name, genre, platform, user);
            gameRepository.save(game);
        }
        return "redirect:/";
    }


    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.GET)
    public String edit(HttpSession session,int game, Model model){
        String username = (String)session.getAttribute(SESSION_USERNAME);
        User user = userRepository.findFirstByUsername(username);
        Game gameid = gameRepository.findById(game);
        if(user != null){
            model.addAttribute("games", gameid);
            model.addAttribute("user", user);
        }
        return "edit";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public String datele(HttpSession session, int id){
        String username = (String)session.getAttribute(SESSION_USERNAME);
        User user = userRepository.findFirstByUsername(username);
        if(user != null){
            gameRepository.delete(id);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String update(HttpSession session,int id, String name, String genre, String platform){
        String username = (String)session.getAttribute(SESSION_USERNAME);
        User user = userRepository.findFirstByUsername(username);
        if(user != null){
            Game game1 = gameRepository.findById(id);
            game1.setName(name);
            game1.setGenre(genre);
            game1.setPlatform(platform);
            gameRepository.save(game1);
        }
        return "redirect:/";
    }
}

package com.egfavre.controllers;

import com.egfavre.PasswordStorage;
import com.egfavre.entities.Events;
import com.egfavre.entities.User;
import com.egfavre.services.EventsRepository;
import com.egfavre.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * Created by user on 6/23/16.
 */
@Controller
public class CalendarSpringController {

    @Autowired
    UserRepository users;

    @Autowired
    EventsRepository events;



    @RequestMapping (path = "/", method = RequestMethod.GET)
    public String home (HttpSession session, Model model){
        String username = (String) session.getAttribute("username");

        model.addAttribute("username", username);
        model.addAttribute("events", events.findAll());
        model.addAttribute("now", LocalDateTime.now());
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String username, String password) throws Exception {
        User user = users.findByName(username);
        if (user == null){
            user = new User(username, PasswordStorage.createHash(password));
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(password, user.getPassword())){
            throw new Exception("wrong password");
        }

        session.setAttribute("username", username);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/create-event", method = RequestMethod.POST)
    public String createEvent (HttpSession session, String description, String time){
        String username = (String) session.getAttribute("username");
        User user = users.findByName(username);
        Events event = new Events (description, LocalDateTime.parse(time), user);
        events.save(event);
        return "redirect:/";
    }
}

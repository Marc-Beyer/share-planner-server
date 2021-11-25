package com.vpr.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Controller // This means that this class is a Controller
@RequestMapping(path="/vpr") // This means URL's start with /demo (after Application path)
public class MainController {

    // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    @Autowired
    private com.vpr.server.UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserEventRepository userEventRepository;

    // POST-request at /add with request parameter
    // @ResponseBody means the returned String is the response, not a view name
    @PostMapping(path="/add-user")
    public @ResponseBody String addNewUser (
            @RequestParam String name,
            @RequestParam String forename,
            @RequestParam String password,
            @RequestParam String isAdmin
            ) {

        com.vpr.server.User user = new com.vpr.server.User();

        // TODO set correct token and password
        user.setName(name);
        user.setForename(forename);
        user.setPassword(password);
        user.setToken("test");
        user.setAdmin(isAdmin.equals("1"));

        userRepository.save(user);
        return "Saved";
    }

    // GET-request at /all-users
    // returns JSON-data
    @GetMapping(path="/all-users")
    public @ResponseBody Iterable<com.vpr.server.User> getAllUsers() {
        return userRepository.findAll();
    }

    // POST-request at /users-by-name
    // returns JSON-data
    @PostMapping(path="/users-by-name")
    public @ResponseBody Iterable<com.vpr.server.User> getUsersByName(@RequestParam String name) {
        return userRepository.findByName(name);
    }

    // GET-request at /all-events
    // returns JSON-data
    @GetMapping(path="/all-events")
    public @ResponseBody Iterable<com.vpr.server.Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @GetMapping(path="/test")
    public @ResponseBody Iterable<Object> getTest() {
        return eventRepository.test();
    }
}
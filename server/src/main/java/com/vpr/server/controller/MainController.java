package com.vpr.server.controller;

import com.vpr.server.data.Event;
import com.vpr.server.data.User;
import com.vpr.server.data.UserEvent;
import com.vpr.server.repository.EventRepository;
import com.vpr.server.repository.UserEventRepository;
import com.vpr.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.text.SimpleDateFormat;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/vpr") // This means URL's start with /demo (after Application path)
public class MainController {

    // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserEventRepository userEventRepository;

    @GetMapping(path = "/status-test")
    public String statusTest(){
        throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "TestTestTest");
    }

    @PostMapping(path = "/header-test")
    public ResponseEntity<String> headerTest(@RequestHeader("Authorization") String authorizationHeader){
        System.out.println("authorizationHeader: " + authorizationHeader);
        return new ResponseEntity<>(authorizationHeader, HttpStatus.OK);
    }
}
package com.vpr.server.controller;

import com.vpr.server.data.Event;
import com.vpr.server.data.User;
import com.vpr.server.data.UserEvent;
import com.vpr.server.dao.interfaces.EventDAO;
import com.vpr.server.json.JSONMapper;
import com.vpr.server.json.Validator;
import com.vpr.server.repository.EventRepository;
import com.vpr.server.repository.UserEventRepository;
import com.vpr.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping(path = "/event")
public class EventController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserEventRepository userEventRepository;

    @Autowired
    private EventDAO eventDAO;

    /******************
     * POST-ENDPOINTS *
     ******************/

    @PostMapping(path = "/add")
    public @ResponseBody
    ResponseEntity<String> addEvent(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam long userId,
            @RequestParam String date,
            @RequestParam String name,
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam Integer priority,
            @RequestParam Boolean isFullDay,
            @RequestParam Boolean isPrivate
    ) {
        User authUser = userRepository.findByToken(authorizationHeader.split("\\s")[1]);
        if (authUser == null || (!authUser.isAdmin() && authUser.getId() != userId)) {
            return new ResponseEntity<>("Du hast keine Rechte um den Termin zu erstellen", HttpStatus.UNAUTHORIZED);
        }

        ResponseEntity<String> BAD_REQUEST = createEventAndUserEvent(userId, date, name, start, end, priority, isFullDay, isPrivate);
        if (BAD_REQUEST != null) return BAD_REQUEST;

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping(path = "/del")
    public @ResponseBody
    ResponseEntity<String> delEvent(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam long eventId,
            @RequestParam long userId,
            @RequestParam String date
    ) {
        User authUser = userRepository.findByToken(authorizationHeader.split("\\s")[1]);
        if (authUser == null || (!authUser.isAdmin() && authUser.getId() != userId)) {
            return new ResponseEntity<>("Du hast keine Rechte um den Termin zu löschen", HttpStatus.UNAUTHORIZED);
        }

        eventRepository.deleteUserEventsById(userId, eventId, date);
        if(eventDAO.getAllEventsWithId(eventId).size() == 0){
            eventRepository.deleteById(eventId);
        }

        return new ResponseEntity<>("", HttpStatus.OK);
    }


    @PostMapping(path = "/all")
    public @ResponseBody
    ResponseEntity<String> getAllEvents(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        User authUser = userRepository.findByToken(authorizationHeader.split("\\s")[1]);
        if (authUser == null) {
            return new ResponseEntity<>("Bitte erneut einloggen", HttpStatus.UNAUTHORIZED);
        }

        List<Event> eventList = eventDAO.getAllEventsInTimespan(authUser.getId(), startDate, endDate);

        return new ResponseEntity<>(JSONMapper.ToJSON(eventList), HttpStatus.OK);
    }


    @PostMapping(path = "/edit")
    public @ResponseBody
    ResponseEntity<String> editEvent(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam Long eventId,
            @RequestParam Long userId,
            @RequestParam String date,
            @RequestParam String newDate,
            @RequestParam String newName,
            @RequestParam String newStart,
            @RequestParam String newEnd,
            @RequestParam Integer newPriority,
            @RequestParam Boolean newIsFullDay,
            @RequestParam Boolean newIsPrivate
    ) {
        User authUser = userRepository.findByToken(authorizationHeader.split("\\s")[1]);
        if (authUser == null || (!authUser.isAdmin() && authUser.getId() != userId)) {
            return new ResponseEntity<>("Du hast keine Rechte um den Termin zu bearbeiten", HttpStatus.UNAUTHORIZED);
        }

        List<Event> eventList = eventDAO.getAllEventsWithIdAndDate(userId, eventId, date);

        if (eventList == null || eventList.size() == 0) {
            return new ResponseEntity<>("Der Termin exestiert nicht in der Datenbank", HttpStatus.BAD_REQUEST);
        }
        if (eventList.size() > 1) {
            return new ResponseEntity<>("Drr Termin ist doppelt vorhanden. (Um das zu lösen versuche den Termin zu löschen und erneut zu erstellen)", HttpStatus.BAD_REQUEST);
        }

        eventRepository.deleteUserEventsById(userId, eventId, date);
        if(eventDAO.getAllEventsWithId(eventId).size() == 0){
            eventRepository.deleteById(eventId);
        }

        ResponseEntity<String> BAD_REQUEST = createEventAndUserEvent(userId, newDate, newName, newStart, newEnd, newPriority, newIsFullDay, newIsPrivate);
        if (BAD_REQUEST != null) return BAD_REQUEST;

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    private ResponseEntity<String> createEventAndUserEvent(long userId, String date, String name, String start, String end, Integer priority, Boolean isFullDay, Boolean isPrivate) {
        User user = userRepository.findById(userId);
        if(user == null){
            return new ResponseEntity<>("UserId nicht korrekt", HttpStatus.BAD_REQUEST);
        }

        try {
            Event event = new Event();

            event.setName(Validator.ValidateEventName(name));
            event.setStart(Validator.ValidateEventTime(start));
            event.setEnd(Validator.ValidateEventTime(end));
            event.setPriority(priority);
            event.setFullDay(isFullDay);
            event.setPrivate(isPrivate);

            UserEvent userEvent = new UserEvent();

            userEvent.setDate(Validator.ValidateEventDate(date));
            userEvent.setEvent(event);
            userEvent.setUser(user);

            eventRepository.save(event);
            userEventRepository.save(userEvent);
        }catch (IllegalArgumentException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}

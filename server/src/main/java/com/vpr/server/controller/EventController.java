package com.vpr.server.controller;

import com.vpr.server.data.Event;
import com.vpr.server.data.User;
import com.vpr.server.data.UserEvent;
import com.vpr.server.dao.interfaces.EventDAO;
import com.vpr.server.json.EventJSONMapper;
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
            @RequestParam Integer userId,
            @RequestParam String date,
            @RequestParam String name,
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam Integer prority,
            @RequestParam Boolean isFullDay,
            @RequestParam Boolean isPrivate
    ) {
        String errorString = "";

        Event event = new Event();

        System.out.println(name.length() + ". name " + name);
        if (name.length() > 3) {
            event.setName(name);
        } else {
            System.out.println("NAME IST ZU KURZ");
            return new ResponseEntity<>("Der Name ist zu kurz", HttpStatus.BAD_REQUEST);
        }

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
            long ms = simpleDateFormat.parse(start).getTime();
            event.setStart(new Time(ms));
        } catch (Exception e) {
            event.setStart(null);
        }

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
            long ms = simpleDateFormat.parse(end).getTime();
            event.setEnd(new Time(ms));
        } catch (Exception e) {
            event.setEnd(null);
        }

        event.setPriority(prority);
        event.setFullDay(isFullDay);
        event.setPrivate(isPrivate);

        UserEvent userEvent = new UserEvent();

        try {
            System.out.println("date " + date);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            userEvent.setDate(new java.sql.Date(simpleDateFormat.parse(date).getTime()));
        } catch (Exception e) {
            System.out.println("DATE FORMAT NOT CORRECT");
            return new ResponseEntity<>("Datumformat nicht korrekt", HttpStatus.BAD_REQUEST);
        }

        userEvent.setEvent(event);
        long uId = Long.valueOf(userId);
        User user = userRepository.findById(uId);
        userEvent.setUser(user);

        System.out.println(userEvent);
        System.out.println(user);

        eventRepository.save(event);
        userEventRepository.save(userEvent);
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
        System.out.println("authorizationHeader " + authorizationHeader);
        User authUser = userRepository.findByToken(authorizationHeader.split("\\s")[1]);
        if(authUser == null || (!authUser.isAdmin() && authUser.getId() != userId)){
            return new ResponseEntity<>( "Du hast keine Rechte um den Termin zu löschen", HttpStatus.UNAUTHORIZED);
        }

        EventRepository.UserEventInterface userEvent = eventRepository.findUserEventByEventIdUserIdAndDate(eventId, authUser.getId(), date);

        //Optional<Event> event = eventRepository.findById(eventId);

        if (userEvent == null){
            return new ResponseEntity<>( "Der Termin exestiert nicht", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>( "Der Termin exestiert", HttpStatus.OK);

/*
        eventRepository.deleteUserEventsById(eventId);
        eventRepository.deleteById(eventId);
        return new ResponseEntity<>("", HttpStatus.OK);
 */
    }

    /*
    @PostMapping(path = "/all")
    public @ResponseBody
    List<Event> getAllEvents(
            @RequestParam long userId,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        return eventRepository.findEventsInDateRange(userId, startDate, endDate);
    }
     */

    @PostMapping(path = "/edit")
    public @ResponseBody
    String editEvent(
            @RequestParam Long eventId,
            @RequestParam Long userId,
            @RequestParam String date
    ) {
        //EventRepository.UserEventInterface userEvent = eventRepository.findUserEventByEventIdUserIdAndDate(eventId, userId, date);
        //List<Event> userEvent = eventRepository.findByNativeQuery();
        List<Event> eventList = eventDAO.getAllEvents();

        return EventJSONMapper.ToJSON(eventList);
    }

}

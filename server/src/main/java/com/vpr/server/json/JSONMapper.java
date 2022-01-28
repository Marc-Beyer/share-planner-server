package com.vpr.server.json;

import com.vpr.server.data.Event;
import com.vpr.server.data.User;
import com.vpr.server.data.UserEvent;

import java.sql.Time;

import java.util.ArrayList;
import java.util.List;

public class JSONMapper {

    public static String userToJSON(User user) {
        return "{" +
                "\"userId\": " + user.getId() + ", " +
                "\"forename\": \"" + user.getForename() + "\", " +
                "\"name\": \"" + user.getName() + "\", " +
                "\"login\": \"" + user.getLogin() + "\"," +
                "\"admin\": " + user.isAdmin() +
                "}";
    }

    public static String userListToJSON(List<User> userList) {
        StringBuilder userListJSON = new StringBuilder();
        for (User user : userList) {
            userListJSON.append(", ");
            userListJSON.append(userToJSON(user));
        }
        userListJSON.delete(0, 2);

        return "[" + userListJSON + "]";
    }

    public static List<String> eventToJSON(Event event) {
        List<String> eventListJSON = new ArrayList<>();

        for (UserEvent userEvent : event.getUserEvent()) {

            String eventJSON = "{" +
                    "\"ownerId\": " + userEvent.getUser().getId() + ", " +
                    "\"ownerName\": \"" + userEvent.getUser().getForename() + " " + userEvent.getUser().getName() + "\", " +
                    "\"date\": \"" + userEvent.getDate() + "\", " +
                    "\"id\": " + event.getId() + "," +
                    "\"name\": \"" + event.getName() + "\"," +
                    "\"priority\": " + event.getPriority() + "," +
                    "\"fullDay\": " + event.isFullDay() + "," +
                    "\"private\": " + event.isPrivate() + "," +
                    "\"start\": " + timeToJSON(event.getStart()) + "," +
                    "\"end\": " + timeToJSON(event.getEnd()) +
                    "}";

            eventListJSON.add(eventJSON);
        }

        return eventListJSON;
    }

    public static String eventListToJSON(List<Event> eventList) {
        StringBuilder eventListJSON = new StringBuilder();
        for (Event event : eventList) {
            List<String> eventsJSON = eventToJSON(event);
            for (String eventJSON : eventsJSON) {
                eventListJSON.append(", ");
                eventListJSON.append(eventJSON);
            }
        }
        eventListJSON.delete(0, 2);

        return "[" + eventListJSON + "]";
    }

    public static String timeToJSON(Time time) {
        if (time == null) {
            return "null";
        }

        return "\"" + time + "\"";
    }
}

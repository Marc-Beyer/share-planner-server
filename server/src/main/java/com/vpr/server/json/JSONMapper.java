package com.vpr.server.json;

import com.vpr.server.data.Event;
import com.vpr.server.data.UserEvent;
import java.sql.Time;

import java.util.ArrayList;
import java.util.List;

public class JSONMapper {
    public static List<String> ToJSON(Event event){
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
                        "\"start\": " + ToJSON(event.getStart()) + "," +
                        "\"end\": " + ToJSON(event.getEnd()) +
                    "}";

            eventListJSON.add(eventJSON);
        }

        return eventListJSON;
    }

    public static String ToJSON(List<Event> eventList){
        StringBuilder eventListJSON = new StringBuilder();
        for(Event event : eventList){
            List<String> eventsJSON = ToJSON(event);
            for(String eventJSON : eventsJSON){
                eventListJSON.append(", ");
                eventListJSON.append(eventJSON);
            }
        }
        eventListJSON.delete(0, 2);

        return "[" + eventListJSON + "]";
    }

    public static String ToJSON(Time time){
        if(time == null){
            return "null";
        }

        return "\"" + time + "\"";
    }
}

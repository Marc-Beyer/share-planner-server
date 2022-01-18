package com.vpr.server.dao.interfaces;

import com.vpr.server.data.Event;

import java.util.List;

public interface EventDAO {

    List<Event> getAllEvents();

    List<Event> getAllEventsInTimespan(long userId, String startDate, String endDate);

    List<Event> getAllEventsWithIdAndDate(long userId, long eventId, String date);

    List<Event> getAllEventsWithId(long eventId);
}

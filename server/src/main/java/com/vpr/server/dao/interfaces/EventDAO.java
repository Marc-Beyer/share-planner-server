package com.vpr.server.dao.interfaces;

import com.vpr.server.data.Event;

import java.util.List;

public interface EventDAO {
    List<Event> getAllEvents();
}

package com.vpr.server.dao.implementation;

import com.vpr.server.dao.interfaces.EventDAO;
import com.vpr.server.data.Event;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class EventDAOImplementation implements EventDAO {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Event> getAllEvents() {
        return manager.createNamedQuery("getAllEvents", Event.class).getResultList();
    }

    @Override
    public List<Event> getAllEventsWithId(long eventId) {
        return manager.createNamedQuery("getAllEventsWithId", Event.class)
                .setParameter("eventId", eventId)
                .getResultList();
    }

    @Override
    public List<Event> getAllEventsInTimespan(long userId, String startDate, String endDate) {
        return manager.createNamedQuery("getAllEventsInTimespan", Event.class)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    @Override
    public List<Event> getAllEventsWithIdAndDate(long userId, long eventId, String date) {
        return manager.createNamedQuery("getAllEventsWithIdAndDate", Event.class)
                .setParameter("userId", userId)
                .setParameter("eventId", eventId)
                .setParameter("date", date)
                .getResultList();
    }
}

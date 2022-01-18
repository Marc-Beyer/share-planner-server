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
}

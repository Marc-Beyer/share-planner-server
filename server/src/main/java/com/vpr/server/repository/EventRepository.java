package com.vpr.server.repository;

import com.vpr.server.data.Event;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called eventRepository
// CRUD refers Create, Read, Update, Delete

public interface EventRepository extends CrudRepository<Event, Integer> {
    @Query(
            value = "SELECT e.id AS eid, e.name AS ename, e.start, e.end, e.priority , e.is_full_day, " +
            "ue.date, " +
            "u.id AS uid, u.forename, u.name AS uname " +
            "FROM event e " +
            "INNER JOIN user_event ue " +
            "ON e.id = ue.event_id " +
            "INNER JOIN user u " +
            "ON ue.user_id = u.id " +
            "WHERE u.id = ?1 " +
            "OR e.is_private = 0",
            nativeQuery = true
    )
    Object[] findAllVisibleByUserId(long id);

    @Query(
            value = "SELECT * " +
            "FROM event e " +
            "INNER JOIN user_event ue " +
            "ON e.id = ue.event_id " +
            "WHERE ue.user_id = ?1",
            nativeQuery = true
    )
    Object[] findAllByUserId(long id);


    @Query(
            value = "DELETE ue FROM user_event ue WHERE ue.event_id = ?1",
            nativeQuery = true
    )
    Object[] findUserIdByEventId(long id);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE ue FROM user_event ue WHERE ue.event_id = ?1",
            nativeQuery = true
    )
    void deleteUserEventsById(long id);


    @Modifying
    @Transactional
    @Query(
            value = "DELETE e FROM event e WHERE e.id = ?1",
            nativeQuery = true
    )
    void deleteById(long id);
}
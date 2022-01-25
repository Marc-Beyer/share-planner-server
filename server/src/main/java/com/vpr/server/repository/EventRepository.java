package com.vpr.server.repository;

import com.vpr.server.data.Event;
import com.vpr.server.data.UserEvent;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
            value = "SELECT ue.user_id as userId, ue.event_id as eventId, ue.date as date " +
                    "FROM event e " +
                    "INNER JOIN user_event ue " +
                    "ON e.id = ue.event_id " +
                    "WHERE ue.event_id = ?1 " +
                    "AND ue.user_id = ?2 " +
                    "AND ue.date = ?3",
            nativeQuery = true
    )
    UserEventInterface findUserEventByEventIdUserIdAndDate(long eventId, long userId, String date);

    interface UserEventInterface{
        long getEventId();
        long getUserId();
        long getDate();
    }

    @Modifying
    @Transactional
    @Query(
            value = "DELETE ue FROM user_event ue WHERE ue.event_id = :eventId AND ue.user_id = :userId AND ue.date = :date",
            nativeQuery = true
    )
    void deleteUserEventsById(long userId, long eventId, String date);


    @Modifying
    @Transactional
    @Query(
            value = "DELETE e FROM event e WHERE e.id = ?1",
            nativeQuery = true
    )
    void deleteById(long id);
}
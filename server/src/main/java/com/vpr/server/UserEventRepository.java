package com.vpr.server;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called eventListRepository
// CRUD refers Create, Read, Update, Delete

public interface UserEventRepository extends CrudRepository<UserEvent, Integer> {
    @Query("SELECT " +
            "   ue.date " +
            "FROM " +
            "   user_event ue")
    List<java.sql.Date> findAllDates();

    /*
    @Query("SELECT " +
            "   new com.vpr.server.DateEvent(ue.date) " +
            "FROM " +
            "   user_event ue")
    List<DateEvent> findAllAsDateEvent();

     */
}

package com.vpr.server;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

// @Entity creates a table out of this class with Hibernate
// @Table defines the table-name
@Entity
@Table(name="user_event")
@IdClass(UserEventId.class)
public class UserEvent {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

    private Date date;

    /*********************
     * Getter and Setter *
     *********************/

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // toString
    @Override
    public String toString() {
        return "UserEvent{" +
                "user=" + user.getId() +
                ", event=" + event.getId() +
                ", date=" + date +
                '}';
    }
}
package com.vpr.server;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

// @Entity creates a table out of this class with Hibernate
@Entity(name = "Event")
public class Event {
    // Generate the primary key
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    private String name;
    private Integer priority;
    private boolean isFullDay;
    private boolean isPrivate;
    private Time start;
    private Time end;

    @OneToMany(mappedBy = "event")
    private List<UserEvent> userEvent;

    /*********************
     * Getter and Setter *
     *********************/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public boolean isFullDay() {
        return isFullDay;
    }

    public void setFullDay(boolean fullDay) {
        isFullDay = fullDay;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public List<UserEvent> getUserEvent() {
        return userEvent;
    }

    public void setUserEvent(List<UserEvent> userEvent) {
        this.userEvent = userEvent;
    }

    // toString
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                ", isFullDay=" + isFullDay +
                ", start=" + start +
                ", end=" + end +
                ", userEvent=" + userEvent +
                '}';
    }
}

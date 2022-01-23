package com.vpr.server.data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.List;

@Entity(name = "Event") // @Entity creates a table out of this class with Hibernate
@Table(name = "event")
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "getAllEvents",
        query = "SELECT * FROM event",
        resultClass = Event.class
    ),
    @NamedNativeQuery(
            name = "getAllEventsInTimespan",
            query = "SELECT * " +
                    "FROM event e " +
                    "INNER JOIN user_event ue " +
                    "ON e.id = ue.event_id " +
                    "WHERE (ue.user_id = :userId OR e.is_private = 0) " +
                    "AND ue.date >= :startDate " +
                    "AND ue.date < :endDate " +
                    "ORDER BY ue.date, e.priority DESC, e.start",
            resultClass = Event.class
    ),
    @NamedNativeQuery(
            name = "getAllEventsWithIdAndDate",
            query = "SELECT * " +
                    "FROM event e " +
                    "INNER JOIN user_event ue " +
                    "ON e.id = ue.event_id " +
                    "WHERE ue.user_id = :userId " +
                    "AND ue.event_id = :eventId " +
                    "AND ue.date = :date",
            resultClass = Event.class
    ),
    @NamedNativeQuery(
            name = "getAllEventsWithId",
            query = "SELECT * " +
                    "FROM event e " +
                    "INNER JOIN user_event ue " +
                    "ON e.id = ue.event_id " +
                    "WHERE ue.event_id = :eventId",
            resultClass = Event.class
    )
})
public class Event implements Serializable {
    // Generate the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "is_full_day", nullable = false)
    private boolean isFullDay;

    @Column(name = "is_private", nullable = false)
    private boolean isPrivate;

    @Column(name = "start")
    private Time start;

    @Column(name = "end")
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

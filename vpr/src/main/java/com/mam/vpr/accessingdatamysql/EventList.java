package com.mam.vpr.accessingdatamysql;

import javax.persistence.*;
import java.sql.Date;

// @Entity creates a table out of this class with Hibernate
// @Table defines the table-name
@Entity
@Table(name="event_list")
public class EventList {
    // Generate the primary key
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    // Generate the foreign key
    //@ManyToOne
    //@JoinColumn(name = "user_id", referencedColumnName = "id")
    private Integer userId;
    //@ManyToOne
    //@JoinColumn(name = "event_id", referencedColumnName = "id")
    private Integer eventId;

    private Date date;

    /*********************
     * Getter and Setter *
     *********************/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
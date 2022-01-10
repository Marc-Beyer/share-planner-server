package com.vpr.server.data;

import java.io.Serializable;
import java.sql.Date;

public class UserEventId implements Serializable {
    private long user;
    private long event;
    private Date date;

    /*********************
     * Getter and Setter *
     *********************/

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getEvent() {
        return event;
    }

    public void setEvent(long event) {
        this.event = event;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

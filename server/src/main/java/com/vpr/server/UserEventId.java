package com.vpr.server;

import java.io.Serializable;

public class UserEventId implements Serializable {
    private long user;
    private long event;

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
}

package com.vpr.server.data;

import java.sql.Date;
import java.sql.Time;

public class DateEvent {
    private long userId;
    private String userName;
    private String userForename;

    private Date date;

    private String name;
    private Integer priority;
    private boolean isFullDay;
    private Time start;
    private Time end;

    public DateEvent(UserEvent userEvent) {
        this.userId = userEvent.getUser().getId();
        this.userName = userEvent.getUser().getName();
        this.userForename = userEvent.getUser().getForename();
        this.date = userEvent.getDate();
        this.name = userEvent.getEvent().getName();
        this.priority = userEvent.getEvent().getPriority();
        this.isFullDay = userEvent.getEvent().isFullDay();
        this.start = userEvent.getEvent().getStart();
        this.end = userEvent.getEvent().getEnd();
    }

    public DateEvent(long userId, String userName, String userForename, Date date, String name, Integer priority, boolean isFullDay, Time start, Time end) {
        this.userId = userId;
        this.userName = userName;
        this.userForename = userForename;
        this.date = date;
        this.name = name;
        this.priority = priority;
        this.isFullDay = isFullDay;
        this.start = start;
        this.end = end;
    }

    /*********************
     * Getter and Setter *
     *********************/

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserForename() {
        return userForename;
    }

    public void setUserForename(String userForename) {
        this.userForename = userForename;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}

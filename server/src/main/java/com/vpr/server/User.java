package com.vpr.server;

import javax.persistence.*;
import java.util.List;

// @Entity creates a table out of this class with Hibernate
@Entity
public class User {
    // Generate the primary key
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="forename", nullable=false)
    private String forename;

    @Column(name="login", nullable=false)
    private String login;

    @Column(name="password", nullable=false)
    private String password;

    @Column(name="token")
    private String token;

    @Column(name="is_admin", nullable=false)
    private boolean isAdmin;

    @OneToMany(mappedBy = "user")
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

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<UserEvent> getEventList() {
        return userEvent;
    }

    public void setEventList(List<UserEvent> userEvent) {
        this.userEvent = userEvent;
    }
}
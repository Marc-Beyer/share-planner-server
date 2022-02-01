package com.vpr.server.data;

import javax.persistence.*;
import java.util.List;

// @Entity creates a table out of this class with Hibernate
@Entity(name = "User")
@Table(name = "user")

@SqlResultSetMapping(name="deleteResult", columns = {
        @ColumnResult(name = "count")
})

@NamedNativeQueries({
    @NamedNativeQuery(
        name = "getAllUser",
        query = "SELECT * FROM user",
        resultClass = User.class
    ),
    @NamedNativeQuery(
        name = "deleteAllUserEvents",
        query = "DELETE FROM user_event WHERE user_id = :userId",
            resultSetMapping = "deleteResult"
    )
})
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
    private byte[] password;

    @Column(name="salt", nullable=false)
    private byte[] salt;

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

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
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

    @Override
    public boolean equals(Object obj){
        if(obj.getClass() == User.class){
            return false;
        }

        User user = (User) obj;
        return user.getId() == getId();
    }

    @Override
    public int hashCode(){
        return (int)getId();
    }
}
package org.yanosik.test.task.common.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class User {
    private Long id;
    private String nick;
    private String login;
    // in production, we would never store a password in plain text
    // we would use a hash function to store the password in a secure way (e.g. bcrypt).
    // Also we would need another field to store the salt,
    // and we would have to rewrite half of the code on the dao layer.
    // so left it as it is for simplicity.
    private String password;
    private LocalDateTime insertTime;
    //I assumed we have OneToMany Relationship here based on the task description
    private List<Vehicle> vehicles;

    public User() {
    }

    public User(String nick, String login, String password) {
        this.nick = nick;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
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

    public LocalDateTime getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(LocalDateTime insertTime) {
        this.insertTime = insertTime;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(nick, user.nick)
                && Objects.equals(login, user.login)
                && Objects.equals(password, user.password)
                && Objects.equals(insertTime, user.insertTime)
                && Objects.equals(vehicles, user.vehicles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nick, login, password, insertTime, vehicles);
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", nick='" + nick + '\''
                + ", insertTime=" + insertTime
                + ", vehicles=" + vehicles
                + '}';
    }
}

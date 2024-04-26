package app.service;

import app.model.entities.User;
import app.model.entities.enums.UserRoles;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class LoggedUser {
    private String username;
    private boolean isLogged;
    private boolean isHost;
    private boolean isAdmin;

    public LoggedUser() {
        isLogged = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        this.isHost = host;
    }

    public void login(User user) {
        this.username = user.getUsername();
        this.isLogged = true;

        this.isAdmin = (user.getRole() == UserRoles.ADMIN);
        this.isHost = (user.getRole() == UserRoles.HOST) || this.isAdmin;
    }

    public void logout() {
        this.username = null;

        this.isLogged = false;
        this.isHost = false;
        this.isAdmin = false;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}

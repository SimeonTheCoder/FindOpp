package app.model.dto;

import app.model.entities.enums.UserRoles;
import app.service.UserService;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterBinding {
    private final UserService userService;

    @Column(unique = true, nullable = false)
    @Size(min = 3, max = 20, message = "Потребителското име трябва да бъде между 3 и 20 символа!")
    private String username;

    @Column(unique = true, nullable = false)
    @Size(min = 3, max = 20, message = "Първото име трябва да бъде между 3 и 20 символа!")
    private String firstName;

    @Column(unique = true, nullable = false)
    @Size(min = 3, max = 20, message = "Фамилното име трябва да бъде между 3 и 20 символа!")
    private String lastName;

    @Size(min = 3, max = 20, message = "Паролата трябва да има дължина между 3 и 20 символа!")
    @Column(nullable = false)
    private String password;

    @Size(min = 3, max = 20, message = "Паролата трябва да има дължина между 3 и 20 символа!")
    @Column(nullable = false)
    private String repeat;

    @Email
    @NotBlank(message = "E-мейлът не може да бъде празен!")
    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles role;

    public UserRegisterBinding(UserService userService) {
        this.userService = userService;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public UserService getUserService() {
        return userService;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

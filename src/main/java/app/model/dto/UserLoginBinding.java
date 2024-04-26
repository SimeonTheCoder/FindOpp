package app.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

public class UserLoginBinding {
    @Column(unique = true, nullable = false)
    @Size(min = 3, max = 20, message = "Потребителското име трябва да бъде между 3 и 20 символа!")
    private String username;

    @Size(min = 3, max = 20, message = "Дължината на паролата трябва да е между 3 и 20 символа!")
    @Column(nullable = false)
    private String password;

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
}

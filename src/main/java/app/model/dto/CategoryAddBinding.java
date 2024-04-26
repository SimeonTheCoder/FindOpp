package app.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

public class CategoryAddBinding {
    @Size(min = 3, max = 20, message = "Името на категорията трябва да бъде между 3 и 20 символа!")
    @Column(nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package app.model.dto;

import app.model.entities.enums.CompetitionType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class CompetitionAddBinding {
    @Size(min = 3, message = "Името на събитието трябва да бъде поне 3 символа!")
    @Column(nullable = false)
    private String name;

    @Size(min = 3, message = "Описанието трябва да бъде дълго поне 3 символа!")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String category;

    private String link;

    private LocalDateTime date;

    @NotBlank(message = "Местоположението не може да бъде празно!")
    @Column(nullable = false)
    private String location;

    @NotNull(message = "Трябва да качите снимка!")
    MultipartFile filename;

    private CompetitionType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MultipartFile getFilename() {
        return filename;
    }

    public void setFilename(MultipartFile filename) {
        this.filename = filename;
    }

    public CompetitionType getType() {
        return type;
    }

    public void setType(CompetitionType type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

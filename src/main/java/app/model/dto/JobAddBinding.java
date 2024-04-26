package app.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class JobAddBinding {
    @Size(min = 3, message = "Заглавието на обявата трябва да бъде поне 3 символа!")
    @Column(nullable = false)
    private String title;

    @Size(min = 3, message = "Описанието трябва да бъде поне 3 символа!")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private String link;

    @NotNull(message = "Трябва да добавите изображение!")
    MultipartFile filename;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getFilename() {
        return filename;
    }

    public void setFilename(MultipartFile filename) {
        this.filename = filename;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

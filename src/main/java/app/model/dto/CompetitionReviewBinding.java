package app.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

public class CompetitionReviewBinding {
    @Column(nullable = false)
    @Size(min = 10, message = "Заглавието на отзива трябва да бъде поне 10 символа")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(min = 10, message = "Описанието трябва да бъде поне 10 символа")
    private String description;

    @Column(nullable = false)
    private String rating;

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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}

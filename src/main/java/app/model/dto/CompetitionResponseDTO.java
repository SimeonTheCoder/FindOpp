package app.model.dto;

import app.model.entities.Competition;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.Base64;

public class CompetitionResponseDTO extends RepresentationModel<CompetitionResponseDTO> {
    private Long id;
    private String name;
    private String description;
    private String addedBy;
    private String category;
    private String location;
    private LocalDateTime date;

    public CompetitionResponseDTO(Competition competition) {
        this.id = competition.getId();
        this.name = competition.getTitle();
        this.description = competition.getDescription();
        this.location = competition.getLocation();
        this.date = competition.getDate();
        this.category = String.valueOf(competition.getCompetitionCategory());
        this.addedBy = competition.getAddedBy().getUsername();
        Base64.Encoder encoder = Base64.getEncoder();
    }

    public CompetitionResponseDTO() {

    }

    @JsonCreator
    public CompetitionResponseDTO(@JsonProperty("id") Long id,
                                  @JsonProperty("name") String name,
                                  @JsonProperty("description") String description,
                                  @JsonProperty("addedBy") String addedBy,
                                  @JsonProperty("category") String category,
                                  @JsonProperty("location") String location,
                                  @JsonProperty("start") LocalDateTime date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.addedBy = addedBy;
        this.category = category;
        this.location = location;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

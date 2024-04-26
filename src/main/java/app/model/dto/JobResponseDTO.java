package app.model.dto;

import app.model.entities.Job;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.Base64;

public class JobResponseDTO extends RepresentationModel<JobResponseDTO> {
    private Long id;
    private String title;
    private String description;
    private String addedBy;

    public JobResponseDTO(Job job) {
        this.id = job.getId();
        this.title = job.getTitle();
        this.description = job.getDescription();
        this.addedBy = job.getAddedBy().getUsername();
        Base64.Encoder encoder = Base64.getEncoder();
    }

    public JobResponseDTO() {

    }

    @JsonCreator
    public JobResponseDTO(@JsonProperty("id") Long id,
                                  @JsonProperty("name") String name,
                                  @JsonProperty("description") String description,
                                  @JsonProperty("addedBy") String addedBy) {
        this.id = id;
        this.title = name;
        this.description = description;
        this.addedBy = addedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }
}

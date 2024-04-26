package app.model.entities;

import app.model.entities.enums.CompetitionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "competitions")
public class Competition extends BaseEntity{
    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private String link;

    private LocalDateTime date;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "competition_category_id")
    private CompetitionCategory competitionCategory;

    @ManyToOne
    @JoinColumn(name = "added_by_id")
    private User addedBy;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> participants;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Review> reviews;

    private String location;

    @Lob
    @Column(nullable = false, columnDefinition="LONGBLOB")
    private byte[] image;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CompetitionType competitionType;

    public Competition() {
        participants = new ArrayList<>();
        reviews = new ArrayList<>();
    }

    public double getAverageRating() {
        double ave = 0;

        System.out.println(reviews.size());

        for (Review review : reviews) {
            ave += review.getRating();
        }

        ave /= reviews.size();

        ave = Math.round(ave * 100);
        ave /= 100;

        return ave;
    }

    public User getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public CompetitionCategory getCompetitionCategory() {
        return competitionCategory;
    }

    public void setCompetitionCategory(CompetitionCategory competitionCategory) {
        this.competitionCategory = competitionCategory;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public CompetitionType getCompetitionType() {
        return competitionType;
    }

    public void setCompetitionType(CompetitionType competitionType) {
        this.competitionType = competitionType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

package app.model.entities;

import app.model.entities.enums.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Email
    @Column(unique = true)
    private String email;

    private LocalDate dateJoined;

    @OneToMany(mappedBy = "addedBy", fetch = FetchType.EAGER)
    private List<Job> addedJobs;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Job> employed;

    @OneToMany(mappedBy = "reviewedBy", fetch = FetchType.EAGER)
    private List<Review> reviews;

    @OneToMany(mappedBy = "addedBy" , fetch = FetchType.EAGER)
    private List<Competition> addedCompetitions;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Competition> competitionParticipation;

    @Column(nullable = false)
    private UserRoles role;

    public User() {
        addedJobs = new ArrayList<>();
        employed = new ArrayList<>();
        reviews = new ArrayList<>();
        addedCompetitions = new ArrayList<>();
        competitionParticipation = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    public List<Job> getAddedJobs() {
        return addedJobs;
    }

    public void setAddedJobs(List<Job> addedJobs) {
        this.addedJobs = addedJobs;
    }

    public List<Job> getEmployed() {
        return employed;
    }

    public void setEmployed(List<Job> employed) {
        this.employed = employed;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Competition> getAddedCompetitions() {
        return addedCompetitions;
    }

    public void setAddedCompetitions(List<Competition> addedCompetitions) {
        this.addedCompetitions = addedCompetitions;
    }

    public List<Competition> getCompetitionParticipation() {
        return competitionParticipation;
    }

    public void setCompetitionParticipation(List<Competition> competitionParticipation) {
        this.competitionParticipation = competitionParticipation;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }
}

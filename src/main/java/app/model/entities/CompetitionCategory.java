package app.model.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "competition_categories")
public class CompetitionCategory extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "competitionCategory", fetch = FetchType.EAGER)
    private List<Competition> competitions;

    public CompetitionCategory() {
        competitions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<Competition> competitions) {
        this.competitions = competitions;
    }
}

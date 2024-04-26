package app.service;

import app.model.dto.CategoryAddBinding;
import app.model.dto.CompetitionAddBinding;
import app.model.entities.Competition;

import java.util.List;

public interface CompetitionService {

    int addCompetition(CompetitionAddBinding competitionAddBinding);

    Competition getCompetition(Long id);

    List<Competition> getHomeData();

    boolean addCategory(CategoryAddBinding categoryAddBinding);

    boolean enroll(Long id);

    boolean remove(Long id);
}

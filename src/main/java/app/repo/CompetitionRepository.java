package app.repo;

import app.model.entities.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository  extends JpaRepository<Competition, Long> {

    @Override
    void deleteById(Long aLong);
}

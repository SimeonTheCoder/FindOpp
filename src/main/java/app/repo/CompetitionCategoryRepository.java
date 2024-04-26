package app.repo;

import app.model.entities.CompetitionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionCategoryRepository extends JpaRepository<CompetitionCategory, Long> {
    CompetitionCategory findByName(String name);

}

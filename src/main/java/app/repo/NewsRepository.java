package app.repo;

import app.model.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository  extends JpaRepository<News, Long> {

}

package app.service;

import app.model.dto.NewsAddBinding;
import app.model.entities.News;

import java.util.List;

public interface NewsService {

    News getNews(Long id);

    boolean addNews(NewsAddBinding newsAddBindingModel);

    List<News> getHomeData(int limit);
}

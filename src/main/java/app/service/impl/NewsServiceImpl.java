package app.service.impl;

import app.model.dto.NewsAddBinding;
import app.model.entities.News;
import app.repo.NewsRepository;
import app.repo.UserRepository;
import app.service.LoggedUser;
import app.service.NewsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {
    private final LoggedUser loggedUser;

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    public NewsServiceImpl(LoggedUser loggedUser, NewsRepository newsRepository, UserRepository userRepository) {
        this.loggedUser = loggedUser;
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public News getNews(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public boolean addNews(NewsAddBinding binding) {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(NewsAddBinding.class, News.class).addMappings(
                mapper -> {
                    mapper.map(
                            n -> {
                                try {
                                    return binding.getFilename().getBytes();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            },
                            News::setMainImage
                    );
                }
        );

        News news = modelMapper.map(binding, News.class);

        news.setCreatedDate(LocalDateTime.now());

        news.setAddedBy(userRepository.findByUsername(loggedUser.getUsername()));
        newsRepository.save(news);

        return true;
    }

    @Override
    public List<News> getHomeData(int limit) {
        List<News> news = newsRepository.findAll();

        news.sort((a, b) -> b.getCreatedDate().compareTo(a.getCreatedDate()));

        if(limit != -1) {
            news = news.stream().limit(limit).collect(Collectors.toList());
        }

        return news;
    }
}

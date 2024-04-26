package app.controller;

import app.exceptions.AccessDeniedException;
import app.exceptions.CompetitionNotFoundException;
import app.exceptions.NewsNotFoundException;
import app.model.dto.CategoryAddBinding;
import app.model.dto.CompetitionAddBinding;
import app.model.dto.NewsAddBinding;
import app.model.entities.Competition;
import app.model.entities.News;
import app.service.CompetitionCategoryService;
import app.service.CompetitionService;
import app.service.LoggedUser;
import app.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NewsController {
    private final NewsService newsService;
    private final LoggedUser loggedUser;

    public NewsController(NewsService newsService, LoggedUser loggedUser) {
        this.newsService = newsService;
        this.loggedUser = loggedUser;
    }

    @GetMapping("add-news")
    public ModelAndView addModelPage(@ModelAttribute("newsAddBindingModel") NewsAddBinding newsletter) {
        if (!loggedUser.isLogged()) throw new AccessDeniedException();
        if (!loggedUser.isHost()) throw new AccessDeniedException();

        return new ModelAndView("add-news");
    }

    @PostMapping("add-news")
    public ModelAndView addModelForm(@ModelAttribute("newsAddBindingModel") @Valid NewsAddBinding newsAddBindingModel, BindingResult bindingResult) {
        if (!loggedUser.isLogged()) throw new AccessDeniedException();
        if (!loggedUser.isHost()) throw new AccessDeniedException();

        boolean error = false;

        try {
            error = !newsService.addNews(newsAddBindingModel);
        }catch (Exception exception) {
            error = true;
        }

        if(error) {
            ModelAndView model = new ModelAndView("add-news");
            model.addObject("error", error);

            return model;
        }

        return new ModelAndView("home");
    }

    @GetMapping("/news-info/{id}")
    public ModelAndView newsInfoPage(@PathVariable("id") Long id) {
        News news = newsService.getNews(id);

        if (news == null) throw new NewsNotFoundException();

        return new ModelAndView(
                "news-info",
                "news",
                news
        );
    }

    @GetMapping("/news-info-min/{id}")
    public ModelAndView newsInfoPageMinimal(@PathVariable("id") Long id) {
        News news = newsService.getNews(id);

        if (news == null) throw new NewsNotFoundException();

        return new ModelAndView(
                "news-info-min",
                "news",
                news
        );
    }
}

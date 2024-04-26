package app.controller;

import app.exceptions.AccessDeniedException;
//import app.service.competitionService;
import app.model.entities.enums.CompetitionType;
import app.service.*;
//import app.service.reviewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private final LoggedUser loggedUser;
    private final CompetitionService competitionService;
    private final CompetitionCategoryService competitionCategoryService;
    private final ReviewService reviewService;
    private final JobService jobService;
    private final NewsService newsService;

    public HomeController(LoggedUser loggedUser,
                          CompetitionService competitionService,
                          CompetitionCategoryService competitionCategoryService, ReviewService reviewService, JobService jobService, NewsService newsService) {
        this.loggedUser = loggedUser;
        this.competitionService = competitionService;
        this.competitionCategoryService = competitionCategoryService;
        this.reviewService = reviewService;
        this.jobService = jobService;
        this.newsService = newsService;
    }

    @GetMapping("/home")
    public ModelAndView homePage() {
        if (!loggedUser.isLogged()) throw new AccessDeniedException();
        return new ModelAndView("home", "news", newsService.getHomeData(6));
    }

    @GetMapping("/browse-competitions")
    public ModelAndView browseCompetitions(@RequestParam(value = "id") Long id) {
        ModelAndView view = new ModelAndView("browse-competitions");
        view.addObject("competitions", competitionService.getHomeData());
        view.addObject("categories", competitionCategoryService.getAll());
        view.addObject("type", CompetitionType.COMPETITION);
        view.addObject("filter", id);

        return view;
    }

    @GetMapping("/browse-concourses")
    public ModelAndView browseConcourses(@RequestParam(value = "id") Long id) {
        ModelAndView view = new ModelAndView("browse-competitions");
        view.addObject("competitions", competitionService.getHomeData());
        view.addObject("categories", competitionCategoryService.getAll());
        view.addObject("type", CompetitionType.CONCOURSE);
        view.addObject("filter", id);

        return view;
    }

    @GetMapping("/browse-olympiads")
    public ModelAndView browseOlympiads(@RequestParam(value = "id") Long id) {
        ModelAndView view = new ModelAndView("browse-competitions");
        view.addObject("competitions", competitionService.getHomeData());
        view.addObject("categories", competitionCategoryService.getAll());
        view.addObject("type", CompetitionType.OLYMPIAD);
        view.addObject("filter", id);

        return view;
    }

    @GetMapping("/browse-jobs")
    public ModelAndView browseJobsPage() {
        ModelAndView view = new ModelAndView("browse-jobs");
        view.addObject("jobs", jobService.getHomeData());
        return view;
    }

    @GetMapping("/browse-news")
    public ModelAndView browseNewsPage() {
        ModelAndView view = new ModelAndView("browse-news");
        view.addObject("news", newsService.getHomeData(-1));
        return view;
    }

    @GetMapping("/manage-reviews")
    public ModelAndView managereviews() {
        if (!loggedUser.isLogged()) throw new AccessDeniedException();
        if (!loggedUser.isHost()) throw new AccessDeniedException();
        return new ModelAndView("manage-reviews", "reviews", reviewService.getAll());
    }

    @GetMapping("/")
    public ModelAndView indexPage() {
        return new ModelAndView("index");
    }

    @GetMapping("/about")
    public ModelAndView aboutPage() {
        return new ModelAndView("about");
    }

    @GetMapping("/remove/{id}")
    public String competitionRemove(@PathVariable("id") Long id) {
        if (!loggedUser.isLogged()) throw new AccessDeniedException();
        if (!loggedUser.isHost()) throw new AccessDeniedException();

        competitionService.remove(id);

        return "redirect:/home";
    }

    @GetMapping("/remove-job/{id}")
    public String jpbRemove(@PathVariable("id") Long id) {
        if (!loggedUser.isLogged()) throw new AccessDeniedException();
        if (!loggedUser.isHost()) throw new AccessDeniedException();

        jobService.remove(id);

        return "redirect:/home";
    }
}

package app.controller;

import app.exceptions.AccessDeniedException;
import app.exceptions.ReviewNotFound;
import app.model.dto.CompetitionReviewBinding;
import app.model.entities.Competition;
import app.model.entities.Review;
import app.model.entities.enums.ReviewType;
import app.service.CompetitionService;
import app.service.LoggedUser;
import app.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReviewController {
    private final LoggedUser loggedUser;
    private final CompetitionService competitionService;
    private final ReviewService reviewService;

    public ReviewController(LoggedUser loggedUser, CompetitionService competitionService, ReviewService reviewService) {
        this.loggedUser = loggedUser;
        this.competitionService = competitionService;
        this.reviewService = reviewService;
    }

    @GetMapping("/review/{id}")
    public ModelAndView competitionreview(@PathVariable("id") Long id) {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();

        Competition competition = competitionService.getCompetition(id);
        return new ModelAndView("review", "competition", competition);
    }

    @PostMapping("/review/{id}")
    public String review(@PathVariable("id") Long id, @ModelAttribute("competitionreviewBindingModel") @Valid CompetitionReviewBinding competitionReviewBinding, BindingResult bindingResult) {
        reviewService.reviewCompetition(id, competitionReviewBinding, ReviewType.COMPETITION);

        return "redirect:/browse";
    }

    @GetMapping("/competition-review-min/{id}")
    public ModelAndView competitionreviewPageMinimal(@PathVariable("id") Long id) {
        Review review = reviewService.getReview(id);

        if(review == null) throw new ReviewNotFound();

        return new ModelAndView(
                "competition-review-min",
                "review",
                review
        );
    }
}

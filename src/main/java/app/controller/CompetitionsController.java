package app.controller;

import app.exceptions.AccessDeniedException;
import app.exceptions.CompetitionNotFoundException;
import app.model.dto.CategoryAddBinding;
import app.model.dto.CompetitionAddBinding;
import app.model.entities.Competition;
import app.service.CompetitionCategoryService;
import app.service.CompetitionService;
import app.service.JobService;
import app.service.LoggedUser;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompetitionsController {
    private final CompetitionService competitionService;
    private final CompetitionCategoryService competitionCategoryService;
    private final JobService jobService;
    private final LoggedUser loggedUser;

    public CompetitionsController(CompetitionService competitionService, CompetitionCategoryService competitionCategoryService, JobService jobService, LoggedUser loggedUser) {
        this.competitionService = competitionService;
        this.competitionCategoryService = competitionCategoryService;
        this.jobService = jobService;
        this.loggedUser = loggedUser;
    }

    @GetMapping("add-competition")
    public ModelAndView addModelPage(@ModelAttribute("competitionAddBindingModel") CompetitionAddBinding competitionAddBinding) {
        if (!loggedUser.isLogged()) throw new AccessDeniedException();
        if (!loggedUser.isHost()) throw new AccessDeniedException();

        return new ModelAndView("add-competition", "categories", competitionCategoryService.getAll());
    }

    @PostMapping("add-competition")
    public ModelAndView addModelForm(@ModelAttribute("competitionAddBindingModel") @Valid CompetitionAddBinding competitionAddBinding, BindingResult bindingResult) {
        if (!loggedUser.isLogged()) throw new AccessDeniedException();
        if (!loggedUser.isHost()) throw new AccessDeniedException();

        int status;

        try {
            status = competitionService.addCompetition(competitionAddBinding);
        }catch (Exception exception) {
            status = -1;
        }

        if(status == -1) {
            ModelAndView model = new ModelAndView("add-competition");
            model.addObject("error", status);

            return model;
        }

        ModelAndView model = new ModelAndView("redirect:/browse-competitions?id=-1");
        model.addObject("type", status);

        return model;
    }

    @GetMapping("add-category")
    public ModelAndView addCategoryPage(@ModelAttribute("categoryAddBindingModel") CategoryAddBinding categoryAddBinding) {
        if (!loggedUser.isLogged()) throw new AccessDeniedException();
        if (!loggedUser.isHost()) throw new AccessDeniedException();

        return new ModelAndView("add-category", "categories", competitionCategoryService.getAll());
    }

    @PostMapping("add-category")
    public ModelAndView addCategoryPage(@ModelAttribute("categoryAddBindingModel") @Valid CategoryAddBinding categoryAddBinding, BindingResult bindingResult) {
        if (!loggedUser.isLogged()) throw new AccessDeniedException();
        if (!loggedUser.isAdmin()) throw new AccessDeniedException();

        boolean error = false;

        try {
            error = !competitionService.addCategory(categoryAddBinding);
        }catch (Exception exception) {
            error = true;
        }

        if(error) {
            ModelAndView model = new ModelAndView("add-category");
            model.addObject("error", error);

            return model;
        }

        return new ModelAndView("browse-competitions", "competitions", competitionService.getHomeData());
    }

    @GetMapping("/competition-info/{id}")
    public ModelAndView competitionInfoPage(@PathVariable("id") Long id) {
        Competition competition = competitionService.getCompetition(id);

        if (competition == null) throw new CompetitionNotFoundException();

        return new ModelAndView(
                "competition-info",
                "competition",
                competition
        );
    }

    @GetMapping("/competition-info-min/{id}")
    public ModelAndView competitionInfoPageMinimal(@PathVariable("id") Long id) {
        Competition competition = competitionService.getCompetition(id);

        if (competition == null) throw new CompetitionNotFoundException();

        return new ModelAndView(
                "competition-info-min",
                "competition",
                competition
        );
    }

    @GetMapping("/enroll/{id}")
    public String competitionEnroll(@PathVariable("id") Long id) {
        if (!loggedUser.isLogged()) throw new AccessDeniedException();

        competitionService.enroll(id);

        return "redirect:/browse";
    }
}

package app.controller;

import app.exceptions.AccessDeniedException;
import app.exceptions.CompetitionNotFoundException;
import app.exceptions.JobNotFoundException;
import app.model.dto.JobAddBinding;
import app.model.entities.Job;
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
public class JobsController {
    private final JobService jobService;
    private final LoggedUser loggedUser;

    public JobsController(JobService jobService, LoggedUser loggedUser) {
        this.jobService = jobService;
        this.loggedUser = loggedUser;
    }

    @GetMapping("add-job")
    public ModelAndView addModelPage(@ModelAttribute("jobAddBindingModel") JobAddBinding jobAddBinding) {
        if (!loggedUser.isLogged()) throw new AccessDeniedException();
        if (!loggedUser.isHost()) throw new AccessDeniedException();

        return new ModelAndView("add-job");
    }

    @PostMapping("add-job")
    public ModelAndView addModelForm(@ModelAttribute("jobAddBindingModel") @Valid JobAddBinding jobAddBinding, BindingResult bindingResult) {
        if (!loggedUser.isLogged()) throw new AccessDeniedException();
        if (!loggedUser.isHost()) throw new AccessDeniedException();

        boolean error;

        try {
            error = !jobService.addJob(jobAddBinding);
        }catch (Exception exception) {
            error = true;
        }

        if(error) {
            ModelAndView model = new ModelAndView("add-job");
            model.addObject("error", error);

            return model;
        }

        return new ModelAndView("home");
    }

    @GetMapping("/job-info/{id}")
    public ModelAndView jobInfoPage(@PathVariable("id") Long id) {
        Job job = jobService.getCompetition(id);

        if (job == null) throw new JobNotFoundException();

        return new ModelAndView(
                "job-info",
                "job",
                job
        );
    }

    @GetMapping("/job-info-min/{id}")
    public ModelAndView jobInfoPageMin(@PathVariable("id") Long id) {
        Job job = jobService.getCompetition(id);

        if (job == null) throw new CompetitionNotFoundException();

        return new ModelAndView(
                "job-info-min",
                "job",
                job
        );
    }

    @GetMapping("/enroll/job/{id}")
    public String jobEnroll(@PathVariable("id") Long id) {
        if (!loggedUser.isLogged()) throw new AccessDeniedException();

        jobService.enroll(id);

        return "redirect:/browse";
    }
//
//    @GetMapping("/remove/{id}")
//    public String competitionRemove(@PathVariable("id") Long id) {
//        if (!loggedUser.isLogged()) throw new AccessDeniedException();
//        if (!loggedUser.isHost()) throw new AccessDeniedException();
//
//        competitionService.remove(id);
//
//        return "redirect:/manage";
//    }
}

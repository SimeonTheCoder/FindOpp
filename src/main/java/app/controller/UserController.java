package app.controller;

import app.exceptions.AccessDeniedException;
import app.model.dto.UserLoginBinding;
import app.model.dto.UserRegisterBinding;
import app.service.CompetitionService;
import app.service.LoggedUser;
import app.service.NewsService;
import app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private final LoggedUser loggedUser;
    private final UserService userService;
    private final CompetitionService competitionService;
    private final NewsService newsService;

    public UserController(LoggedUser loggedUser, UserService userService, CompetitionService competitionService, NewsService newsService) {
        this.loggedUser = loggedUser;
        this.userService = userService;
        this.competitionService = competitionService;
        this.newsService = newsService;
    }

    @GetMapping("/login")
    public ModelAndView loginPage(@ModelAttribute("userLoginBindingModel") UserLoginBinding userLoginBindingModel) {
        return new ModelAndView("/login");
    }

    @PostMapping("/login")
    public ModelAndView loginForm(@ModelAttribute("userLoginBindingModel") @Valid UserLoginBinding userLoginBindingModel, BindingResult bindingResult) {
        boolean error;

        try{
            error = !userService.login(userLoginBindingModel);
        }catch(Exception e){
            error = true;
        }

        if(error){
            return new ModelAndView("/login", "error", error);
        }

        return new ModelAndView("home", "news", newsService.getHomeData(6));
    }

    @GetMapping("/register")
    public ModelAndView registerPage(@ModelAttribute("userRegisterBindingModel") UserRegisterBinding userRegisterBindingModel) {
        return new ModelAndView("/register");
    }

    @PostMapping("/register")
    public ModelAndView registerForm(@ModelAttribute("userRegisterBindingModel") @Valid UserRegisterBinding userRegisterBindingModel, BindingResult bindingResult) {
        boolean error;

        try {
            error = !userService.register(userRegisterBindingModel);
        }
        catch (Exception exception) {
            error = true;
        }

        if(error) {
            ModelAndView newView = new ModelAndView("/register");
            newView.addObject("error", error);

            return newView;
        }

        return new ModelAndView("login", "userLoginBindingModel", new UserLoginBinding());
    }

    @GetMapping("/account")
    public ModelAndView accountPage() {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();

        return new ModelAndView("/account", "user", userService.findByUsername(loggedUser.getUsername()));
    }

    @RequestMapping("/logout")
    public String logout() {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();

        loggedUser.logout();
        return "redirect:/login";
    }

    @GetMapping("/follow/{username}")
    public String followUser(@PathVariable("username") String username) {
        if(!loggedUser.isLogged()) throw new AccessDeniedException();

//        boolean result = userService.follow(username);

//        if(!result) return "redirect:/error";

        return "redirect:/browse";
    }
}

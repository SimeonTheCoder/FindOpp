package app.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        System.out.println("Error: " + status);

        String thymleafStatus = "Опа, нещо май не е наред...";

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                thymleafStatus = "Страницата, която търсите, не може да бъде открита";
            } else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                thymleafStatus = "Грешка на сървъра!";
            } else if(statusCode == HttpStatus.FORBIDDEN.value()) {
                thymleafStatus = "Достъпът отказан!";
            }
        }

        return new ModelAndView("error","status",thymleafStatus);
    }
}

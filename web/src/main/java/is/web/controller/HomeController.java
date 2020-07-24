package is.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private ApplicationContext context;
    @GetMapping("/{exit}")
    public ResponseEntity exit(@PathVariable("exit")int exit){
        String message = null;
        if (exit==0){
            SpringApplication.exit(context,() -> 0);
            message = "exit";
        }else {
            message = "enter 0 number for exit";
        }
        return new ResponseEntity(message, HttpStatus.ACCEPTED);
    }
}

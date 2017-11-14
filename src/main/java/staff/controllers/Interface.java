package staff.controllers;
import org.springframework.web.bind.annotation.RequestMapping;

public interface Interface{
    @RequestMapping("/greeting")
    String returnGreeting();
}
package staff.controllers;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="sales")
public interface Interface{
    @RequestMapping("/greeting")
    public String returnGreeting();
}
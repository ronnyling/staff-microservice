package staff.services;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import staff.models.ResponseModel;
import staff.models.Staff;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@EnableFeignClients
@FeignClient("sales-microservice")
public interface GetSalesInterface {
    //@PostMapping(path = "/sales/getSales")
    //ResponseEntity getSalesRec(@RequestBody Staff staff);
    @RequestMapping(path="/sales/getSales",produces = "application/json; charset=UTF-8",method = POST)
    ResponseModel getSalesRecord (@RequestBody Staff staffB);
}


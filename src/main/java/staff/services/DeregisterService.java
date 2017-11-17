package staff.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import staff.models.ResponseModel;
import staff.models.Staff;
import staff.repositories.StaffRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static staff.configurations.config.RESPONSE_CODE_1000;
import static staff.configurations.config.RESPONSE_CODE_1999;


@Service
@Slf4j
public class DeregisterService {
    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private StaffRepository staffRepository;

    public ResponseModel fallbackMethod(String currUser, Staff staff){
        ResponseModel res =new ResponseModel();
        res.setStatus(9999);
        res.setDataObj("You have reach a timeout on DeregisterService");
        return res;
    }

    @HystrixCommand(fallbackMethod = "fallbackMethod", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "20000"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10")
    })
    public ResponseModel deregister(String currUser, Staff staff){
        ResponseModel res = new ResponseModel();
        res.setStatus(RESPONSE_CODE_1999);
        res.setDataObj("Successfully deregister");
        log.info("{} delete staff {} from the database",currUser,staff.getFName());
        staffRepository.deleteStaff(staff.getEID());
        if(staffRepository.checkExist(Integer.toString(staff.getEID()))=="true"	){
            log.info("Failed deleting user {}",staff.getFName());
            return null;
        }else{res.setStatus(RESPONSE_CODE_1000);}
        return res;
    }
}
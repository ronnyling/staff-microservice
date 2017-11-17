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
public class UpdateBranchService {
    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private StaffRepository staffRepository;

    public ResponseModel fallbackMethod(Staff staff){
        ResponseModel res =new ResponseModel();
        res.setStatus(9999);
        res.setDataObj("You have reach a timeout on UpdateBranchService");
        return res;
    }

    @HystrixCommand(fallbackMethod = "fallbackMethod", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "20000"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10")
    })
    public ResponseModel updateBranch(Staff staff){
        ResponseModel<Staff> res = new ResponseModel<>();
        res.setStatus(RESPONSE_CODE_1999);
        String locBefore=staffRepository.getStaffInfo_byStaffName(staff.getFName()).getLocation();
        staffRepository.updateBranchID(staff.getLocation(),staff.getEID());
        if (locBefore!=staff.getLocation()){
            res.setStatus(RESPONSE_CODE_1000);
            log.info("Successfully updated branch of staff "+staff.getFName());
        }
        res.setDataObj(staff);
        return res;
    }
}
package staff.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.GetMapping;
import staff.models.ResponseModel;
import staff.models.Staff;
import staff.repositories.StaffRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static staff.configurations.config.RESPONSE_CODE_1999;


@Service
@Slf4j
public class SalesRecordService {

    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private GetSalesInterface getSalesInterface;


    /*public ResponseModel getSalesBACKUP(String staff){
        String url ="http://localhost:8090/sales/getSales";
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseModel res = new ResponseModel<>();
        res.setStatus(RESPONSE_CODE_1999);
        HttpHeaders requestHeaders=new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        Staff staffObj =staffRepository.getStaffInfo_byStaffName(staff);
        HttpEntity entity = new HttpEntity<>(staffObj,new HttpHeaders());
        ResponseEntity<ResponseModel> sample;
        sample =restTemplate.exchange(url, HttpMethod.POST, entity, ResponseModel.class);
        res.setDataObj(restTemplate.exchange(url, HttpMethod.POST, entity, ResponseModel.class).getBody().getDataObj());
        return res;
    }
*/
    @HystrixCommand(fallbackMethod = "fallbackMethod", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "20000"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10")
    })
    public ResponseModel getSales(String staff){
        ResponseModel res = new ResponseModel<>();
        res.setStatus(RESPONSE_CODE_1999);
        Staff staffObj =staffRepository.getStaffInfo_byStaffName(staff);
        res.setDataObj(getSalesInterface.getSalesRecord(staffObj).getDataObj());
        return res;
    }
    public ResponseModel fallbackMethod(String staff){
        ResponseModel res =new ResponseModel();
        res.setStatus(9999);
        res.setDataObj("You have reach a timeout on SalesRecordService");
        return res;
    }


}

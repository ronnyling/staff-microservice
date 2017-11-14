package staff.services;

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


    public ResponseModel getSales(String staff){
        String url ="http://localhost:8090/msb/getSales";
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

}
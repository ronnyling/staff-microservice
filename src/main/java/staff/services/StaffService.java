package staff.services;

import staff.models.ResponseModel;
import staff.repositories.StaffRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static staff.configurations.config.RESPONSE_CODE_1000;
import static staff.configurations.config.RESPONSE_CODE_1999;


@Service
@Slf4j
public class StaffService {
    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private StaffRepository staffRepository;
    public ResponseModel getStaffs(String location){
        ResponseModel res = new ResponseModel<>();
        res.setStatus(RESPONSE_CODE_1999);
        //res.setDataObj(staffRepository.getStaffInfo_byBranchID(location));
        res.setDataObj(staffRepository.getStaffInfo_byBranchIDall(location));

        if (res.getDataObj()!=null){
            res.setStatus(RESPONSE_CODE_1000);
            log.info("Successfully retrieve all staff in "+ location);
        }
        return res;
    }
}
package staff.services;

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
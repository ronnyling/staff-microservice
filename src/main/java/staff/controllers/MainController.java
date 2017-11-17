package staff.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import staff.services.DeregisterService;
import staff.models.ResponseModel;
import staff.models.Staff;
import staff.repositories.StaffRepository;
import staff.services.RegisterStaffService;
import staff.services.SalesRecordService;
import staff.services.StaffService;
import staff.services.UpdateBranchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@Controller    // This means that this class is a Controller
@RequestMapping(path="/staff") // This means URL's start with /demo (after Application path)
@Slf4j
@EnableEurekaClient
public class MainController implements Interface {

	private HttpHeaders httpHeaders;
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private SalesRecordService salesRecordService;
	@Autowired
	private RegisterStaffService registerStaffService;
	@Autowired
	private UpdateBranchService updateBranchService;
	@Autowired
	private DeregisterService deregisterService;
	@Autowired
	private StaffService staffService;

	RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private Interface anInterface;


	public String returnGreeting() {
		return "Hello from Eureka Client!";
	}


	@GetMapping(path="/staff",produces = "application/json; charset=UTF-8")
	public ResponseEntity<ResponseModel> retrieveStaffInfo(@RequestHeader String location) {
		return new ResponseEntity<>(staffService.getStaffs(location),httpHeaders, HttpStatus.OK);
	}


	@GetMapping(path="/getSalesRecord",produces = "application/json; charset=UTF-8")
	public ResponseEntity<ResponseModel> retrieveSalesRecord(@RequestHeader String staff) {
		return new ResponseEntity<>(salesRecordService.getSales(staff),httpHeaders, HttpStatus.OK);
	}


	@PostMapping(path="/register",produces = "application/json; charset=UTF-8")
	public ResponseEntity<ResponseModel> registerStafftest(@RequestHeader String currUser,@RequestBody Staff staffDetails) {
		return new ResponseEntity<>(registerStaffService.registerStaff(staffDetails),httpHeaders, HttpStatus.OK);
	}

	@PutMapping(path="/updateBranch",produces = "application/json; charset=UTF-8")
	public ResponseEntity<ResponseModel> updateBranchtest(@RequestBody Staff staff) {
		return new ResponseEntity<>(updateBranchService.updateBranch(staff),httpHeaders, HttpStatus.OK);
	}

	@DeleteMapping(path="/deregister",produces = "application/json; charset=UTF-8")
	public ResponseEntity<ResponseModel> deleteStaff(@RequestHeader String currUser,@RequestBody Staff staffDetails) {
		ResponseModel res =deregisterService.deregister(currUser, staffDetails);
		return new ResponseEntity<>(res, httpHeaders, HttpStatus.OK);
	}

}

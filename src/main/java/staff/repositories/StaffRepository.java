package staff.repositories;

import staff.models.Staff;
import staff.models.Mapper.StaffMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class StaffRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Get the ads_id, ads_url and ads_fontColor
     */

    public String  checkExist(String eID) {
        final String sql ="SELECT * FROM db_example.staff where eid=?";
        try {
            jdbcTemplate.queryForObject(sql,new StaffMapper(),eID);
            return ("Exist");
        } catch (Exception ex) {
            return ("Not Exist");
        }
    }
    public Staff getStaffInfo_byStaffName(String staffName) {
        final String sql ="SELECT * FROM db_example.staff where f_name=?";
        try {
            return jdbcTemplate.queryForObject(sql,new StaffMapper(),staffName);
        } catch (Exception ex) {
            log.info("No staff info found.", ex);
            return null;
        }
    }

    public List<Staff>  getStaffInfo_byBranchIDall(String branchID) {
        final String sql ="SELECT * FROM db_example.staff where location=?";
        try {
            List<Staff> allStaff=jdbcTemplate.query(
                    sql,
                    new Object[]{ branchID },
                    new StaffMapper()
            );
            return allStaff;
        } catch (Exception ex) {
            log.info("No staff info found.", ex);
            return null;
        }
    }

    public Staff  getStaffInfo_byBranchID(String branchID) {
        final String sql ="SELECT * FROM db_example.staff where location=?";
        try {

            return jdbcTemplate.queryForObject(sql,new StaffMapper(),branchID);
        } catch (Exception ex) {
            log.info("No staff info found.", ex);
            return null;
        }
    }
    public void registerStaff(Staff staff){
        final String insertSQL="INSERT INTO db_example.staff(eid, f_name, l_name, " +
                "gender, age, email, job_title, career_lvl, location) " +
                "VALUES(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(insertSQL,staff.getEID(),staff.getFName(),staff.getLName(),staff.getGender(),staff.getAge()
                ,staff.getEmail(),staff.getJobTitle(),staff.getCareerLvl(),staff.getLocation());
    }
    public void updateBranchID(String location,Integer eID){
        final String insertSQL1="update db_example.staff set " +
                "location=? where eid=?";
        jdbcTemplate.update(insertSQL1,location,eID);
    }
    public void deleteStaff(Integer eID){
        final String insertSQL2="delete from db_example.staff " +
                "where eid=?";
        jdbcTemplate.update(insertSQL2,eID);
    }
}

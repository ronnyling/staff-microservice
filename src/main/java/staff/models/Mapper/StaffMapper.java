package staff.models.Mapper;

import staff.models.Staff;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffMapper implements RowMapper<Staff> {

    @Override
    public Staff mapRow(ResultSet rs, int i) throws SQLException {
        Staff ads = new Staff();
        ads.setEID(rs.getInt("eid"));
        ads.setFName(rs.getString("f_name"));
        ads.setLName(rs.getString("l_name"));
        ads.setGender(rs.getString("gender"));
        ads.setAge(rs.getInt("age"));
        ads.setEmail(rs.getString("email"));
        ads.setJobTitle(rs.getString("job_title"));
        ads.setCareerLvl(rs.getString("career_lvl"));
        ads.setLocation(rs.getString("location"));
        return ads;
    }
}
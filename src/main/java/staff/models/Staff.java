package staff.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.ToString;


@lombok.Getter
@lombok.Setter
@ToString
@Entity // This tells Hibernate to make a table out of this class
public class Staff {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer eID;
	private String fName;
	private	String lName;
	private String gender;
	private Integer age;
	private String email;
	private String jobTitle;
	private String careerLvl;
	private String location;


}

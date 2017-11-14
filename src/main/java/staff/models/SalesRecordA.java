package staff.models;

import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@lombok.Getter
@lombok.Setter
@ToString
@Entity // This tells Hibernate to make a table out of this class
public class SalesRecordA {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer transactionNo;
	private	String salesPerson;
	private Integer transactionAmt;
	private String transactionName;



}

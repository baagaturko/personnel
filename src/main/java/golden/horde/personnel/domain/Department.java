package golden.horde.personnel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "a_departament")
public class Department {
	
	@Id
	@Column(name="departament_id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="generate_id")	
	@SequenceGenerator(name="generate_id", sequenceName = "gen_a_id", initialValue=1, allocationSize=1)
	private Long departmentId;
	
	@Column(name="department_name")
	private String departmentName;	
	
	// Constructors
	public Department(String departmentName) {
		super();
		this.departmentName = departmentName;
	}

	public Department() {
		super();
	}
	
	// Getters & setters
	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Override
	public String toString() {
		return "Department [departmentId=" + departmentId + ", departmentName=" + departmentName + "]";
	}	

}
/*
create table A_DEPARTAMENT (
		  departament_id number(10) primary key, -- генерится GEN_A_ID 
		  department_name varchar2(250) not null
		)

		create table A_WORKER (
		  worker_id number(10) primary key, -- генерится GEN_A_ID 
		  department_fk_id number(10) not null,
		  name varchar2(250) not null,
		  surname varchar2(250) not null,
		  position varchar2(250) not null,
		  sex number(1) not null, -- MALE, FEMALE, TRANSGENDER, SOMETHINGELSE
		  is_married number(10),  -- logical 0-no, 1-yes
		  date_of_birth date not null,
		  salary number(10, 2) null,
		  worker_description varchar2(1000) null
		)

		create table A_LANGUAGE (
		  language_id number(10) primary key, -- генерится GEN_A_ID 
		  language_name varchar2(250) not null
		)

		create table A_WORKER_AND_LANGUAGE (
		  worker_and_language_id number(10) primary key, -- генерится GEN_A_ID 
		  worker_fk_id number(10) not null,
		  language_fk_id number(10) not null
		)
*/

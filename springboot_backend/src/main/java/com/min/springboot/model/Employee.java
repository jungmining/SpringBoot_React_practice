package com.min.springboot.model;
/*
 * 멤버필드 생성 
 * getter/setter 생성
 * 생성자 오버로딩(firstName, lastName, emailId)생성자 + default 생성자
 * @(annotation)을 통한 JPA 엔티티 매핑
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //DB랑 연동해서 사용할수있는 컬럼이된다 
@Table(name = "employees")
public class Employee {

	@Id //id라고 적으면 자동으로 PK가 된다 : pk가 되는 id를 선언
	@GeneratedValue(strategy = GenerationType.IDENTITY) //pk가 되면서 유니크키가 됨 : id를 pk로 선언하고 생성 규칙을 정의
	private long id;
	@Column(name = "first_name") //ORM의 구성 -> 컬럼명을 name으로 선언하여 Mapping한다
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "email_id")
	private String emailId;
	
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employee(long id, String firstName, String lastName, String emailId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
}

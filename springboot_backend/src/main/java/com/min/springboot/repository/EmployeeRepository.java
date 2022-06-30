package com.min.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.min.springboot.model.Employee;
/*
 * JpaRepository를 사용
 * Spring Data JPA 를 올려야지만 JpaRepository를 사용할 수 있다.(처음에 부트 프로젝트생성할때 디펜던시 추가하면됨)
 * "JpaRepository"라는 기능을 가지고있는 interface를 통해서 Proxy 하고 있다.
 * 기능을 사용하면 매우 간단하게 데이터를 검색하거나 CRUD를 할 수 있다.
 * JpaRepository가 제공하는 건 검색,정렬,입력,페이징 등의 기능을 제공해준다
 *    
 * ORM은 객체랑 연결되어 있는데
 * ORM 이기 때문에 사용하는 @Entity 객체와 연결해서 사용해야한다
 * 따라서 Employee의 객체인 PK가 되는 id(long)을 통해서 CRUD가 발생할 것이다   
 * 
 * public interface 객체명 extends JpaRepository <엔티티,ID 유형>으로 작성해야한다
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}

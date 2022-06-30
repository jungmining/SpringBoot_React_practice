package com.min.springboot.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.min.springboot.exception.ResourceNotFoundException;
import com.min.springboot.model.Employee;
import com.min.springboot.repository.EmployeeRepository;

/*
 * RESTFul API 를 작성하여 작동
 * @RestController 로 작성할것임 모든 프로토콜 처리를 REST 비동기식으로 처리 할 것이다 (이걸로 작성하면 화면에 값 전송이 안된다)
 * @RequestMapping을 통해서 Contorller에 요청되는 "주소"를 작성한다 
 * 기능을 담고 있는 Repository 클래스인 EmployeeRepository 인터페이스(클래스)를 DI한다
 */
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping(value = "api/v1/") // http://localhost:8080/api/vi/매핑명 
public class EmployeeController {
	@Autowired
	private EmployeeRepository employeeRepository; // @Repository로 선언이 되어 있어야함
	
	//직원 전체 호출
	//호출 Mapping이 /employees로 끝날 경우 JpaRepository를 extends하고 있는 employeeRepository의 findAll()을 통해 Database를 검색하여 전체 내용을 가져옴 
	//JPA 작성을 통해서 테이블이 없는 경우 자동으로 생성되게 만들었다(이게 JpaRepository에 update이다)
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll(); //전체 조회
	}
	
	//직원 입력하기
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) { //@RequestBody 화면에서 전송받은 JSON형태의 text
		return employeeRepository.save(employee); //insert하는거 : 저장을 하는 JPA의 interface
	}
	
	//직원 상세 정보 id로 조회 
	//ResponseEntity는 REST ful 개발할 때 사용한다
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
		
		// ResponseEntity 객체를 생성하는 방법은 두가지가 있는데 new를 통해 객체를 만드는 방법과 빌더를 통해 만드는 방법 두가지가 있다.
		//new를 통해 ResponseEntity 객체를 생성하여 필요한 (값,header,응답)을 입력하여 처리
		//builder를 통해서 값 전달 ok() 응답(status)에 대한 형태가 200일 경우에 반환
		Employee emp = employeeRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(id+"사원이 존재하지 않습니다.")
				); //findById는 Optional이라고하는 모든타입을 받을 수 있는 객체를 사용해야하는데 여기서 orElseThrow()를 쓰면 빨간줄이 안뜬다
		System.out.println("getEmployeeById findById 동작했습니다.");
		
		Optional<Employee> test = employeeRepository.findById(id);
		Employee testEmp = test.get();
//		System.out.println("Optional<Employee>의 값 : "+test.get());
		System.out.println("Optional<Employee>의 값 : "+testEmp.getFirstName());
		
		
		//값이 있으면 넘어가고 없다면 예외처리하겠다는 것임 
		//헤더정보를 변경하려면 아래와 같이 헤더값을 변경해서 입력해주면된다
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(new MediaType("application","json",Charset.forName("UTF-8")));
//		return new ResponseEntity<String>("{\"결과\":\"ok\"}",headers,HttpStatus.OK);
//		return new ResponseEntity<Employee>(emp,headers,HttpStatus.OK);
		return ResponseEntity.ok(emp); //ok() 조회결과가 200인 상태일때 emp객체를 전달 하겠다. 
	}
	
	//수정하기
	@PutMapping("/employees/{id}")
//	@PostMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employeeDetail){//id는 주소를 통해 값을 받아옴
		
		System.out.println("getEmployeeById updateEmployee 동작했습니다.");
		//시나리오
		//1)검색을 한다. 만약 없으면 오류를 발생시킨다
		Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id+"의 사원이 존재하지 않습니다.")); //JPA호출해서 id값 확인하고 값이있으면 값을 주고 없다면 오류를 보내기
		System.out.println("검색된 employee : "+employee);
		
		//2)검색된 값에 전달 받은 값으로 변경한다.
//		Employee employee = new Employee();
		employee.setFirstName(employeeDetail.getFirstName());
		employee.setLastName(employeeDetail.getLastName());
		employee.setEmailId(employeeDetail.getEmailId());
		//3)다시 저장한다.
		Employee updateEmployee =  employeeRepository.save(employee);
		
		return ResponseEntity.ok(updateEmployee); //ResponseEntity의 반환타입은 JSON이다 (Employee 클래스로 보내
	}
	
	//회원 정보 삭제
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable long id){
		//String, Boolean 으로 보내면 맵으로 보내진다
		Employee employee = employeeRepository.findById(id).orElseThrow(
				()-> new ResourceNotFoundException(id+"의 사원이 존재하지 않습니다."));
		employeeRepository.delete(employee);
		Map<String, Boolean> responseMap = new HashMap<String, Boolean>();
		//삭제됐을 때의 message
		responseMap.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(responseMap);
	}
	
	
	
	/*
	//회원 ID로 조회하기
		//REST API를 작성하여 작동
		@GetMapping("/employees/{id}")
		public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
			
			//orElseThrow()  찾는 값이 없다면 예외를 발생 시켜준다
			Employee employee = employeeRepository.findById(id).orElseThrow(
					//사용자 예외 클래스를 통해서 처리 하겠다
					()-> new ResourceNotFoundException(id+"사원은 존재하기 않습니다")
					);
			// new ResponseEntity<Employee>(전달객체들->employee, headers,HttpStatus.valueOf(200))
			//ResponseEntitiy 를 통해서 활용한 응답 생성(빌더 방법)
			return ResponseEntity.ok(employee);  // 200인 상태일때 ok가 실행, employee를 전달 하겠다.
			
		}
		
		//회원 수정 하기
		//REST API(PUT)를 작성하여 작동
		// JPA에서는 update와 modify와 같은 메소드는 제공하지 않음. 따라서 객체를 받아온 후 set을 하면 자동으로 변경
		@PutMapping("/employees/{id}") //put은 똑같은 값을 보내면 한줄 row 밖에 안들어간다 (수정이 1번만) patch는 붙여넣다는 의미인데 PK,FK상관없이 로우를 하나 붙여넣는다는 것이다(파일업로드 시에 많이 사용한다) 
//		@PostMapping("/employees/{id}") //post는 하나의 값을 계속해서 보내기때문에 row가 여러개가 생기지만  
		public  ResponseEntity<Employee> updateEmployee(@PathVariable Long id, //주소에서 id값 검색
				@RequestBody Employee employeeDetail){  //화면에서 전달 받은 값
			Employee employee = //주소에서 전달 받은 {id} 값으로 DataBase에서 검색된 객체
					employeeRepository.findById(id).orElseThrow(  //검색된 데이터가 없을 경우 오류발생
					()-> new ResourceNotFoundException(id+"사원이 존재하지 않습니다."));  //사용자 예외처리
			System.out.println("서버에서 전달 받은 값");
			
			//화면에서 입력받은 값을 검색된 새로운 값으로 변경하여 객체 생성
			employee.setFirstName(employeeDetail.getFirstName());
			employee.setLastName(employeeDetail.getLastName());
			employee.setEmailId(employeeDetail.getEmailId());
			
			// JPA의 save 를 통해서 새로 저장후 결과 값 반환
			Employee updateEmployee = employeeRepository.save(employee);
			
			//ok는 즉, HTTP Statue가 200인 경웅 새로운 변경된 객체를 반환
			return ResponseEntity.ok(updateEmployee);
		}
		
		*/
	
}

package com.mindex.challenge;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.reporting.ReportingStructure;
import com.mindex.challenge.reporting.ReportingStructureHelper;
import com.mindex.challenge.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ChallengeApplicationTests {
	private static final Logger LOG = LoggerFactory.getLogger(ChallengeApplicationTests.class);

	@Autowired
	EmployeeService employeeService;
	@Autowired 
	BeanFactory beanFactory;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	ReportingStructureHelper reportingHelper;

	private String reportingUrl;
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

	private String employeeUrl;


    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        reportingUrl = "http://localhost:" + port + "/reporting/{id}";
    }
	@Test
	public void contextLoads() {
	}

	@Test
	public void calcNumberOfReportsTest() {
		Employee employee = employeeService.read("16a596ae-edd3-4847-99fe-c4518e82c86f");
		ReportingStructure reporting = new ReportingStructure(employee, reportingHelper.generateNumberOfReports(employee));
		Assertions.assertEquals(4, reporting.getNumberOfReports());
		
		Employee employee2 = employeeService.read("03aa1462-ffa9-4978-901b-7c001562cf6f");
		
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employeeService.read("62c1084e-6e34-4630-93fd-9153afb65309"));
		employee2.setDirectReports(employeeList);
		employeeService.update(employee2);
		Assertions.assertEquals(3, reportingHelper.generateNumberOfReports(employee));
		
		ReportingStructure reporting2 = new ReportingStructure(employee2, reportingHelper.generateNumberOfReports(employee2));
		Assertions.assertEquals(1, reporting2.getNumberOfReports());
		
	}
	@Test
	public void testRestEndpoint() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");
        
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(employeeService.read("16a596ae-edd3-4847-99fe-c4518e82c86f"));
        testEmployee.setDirectReports(employeeList);
        
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        ReportingStructure reporting = restTemplate.getForEntity(reportingUrl, ReportingStructure.class, createdEmployee.getEmployeeId()).getBody();	
        Assertions.assertEquals(5, reporting.getNumberOfReports());
        
        
        
	}
}

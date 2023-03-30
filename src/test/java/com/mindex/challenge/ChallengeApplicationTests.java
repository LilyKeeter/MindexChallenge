package com.mindex.challenge;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.reporting.ReportingStructure;
import com.mindex.challenge.service.impl.EmployeeServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ChallengeApplicationTests {
	private static final Logger LOG = LoggerFactory.getLogger(ChallengeApplicationTests.class);

	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void calcNumberOfReportsTest() {
		Employee employee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
		ReportingStructure reporting = new ReportingStructure(employee);
		// LOG.debug(reporting.getEmployee().getFirstName());
		
		reporting.setEmployee(employee);
		Assertions.assertEquals(4, reporting.getNumberOfReports());
	}

}

package com.mindex.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;

@Component
public class DataBootstrap {
	private static final String DATASTORE_LOCATION_EMPLOYEE = "/static/employee_database.json";
	private static final String DATASTORE_LOCATION_COMPENSATION = "/static/compensation_database.json";

	@Autowired
	private EmployeeRepository employeeRepository;
    @Autowired
    private CompensationRepository compRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	public void init() {
		InputStream inputStreamEmployee = this.getClass().getResourceAsStream(DATASTORE_LOCATION_EMPLOYEE);
		InputStream inputStreamCompensation= this.getClass().getResourceAsStream(DATASTORE_LOCATION_COMPENSATION);

		Employee[] employees = null;
		Compensation[] compensations = null;

		try {
			employees = objectMapper.readValue(inputStreamEmployee, Employee[].class);
			compensations = objectMapper.readValue(inputStreamCompensation, Compensation[].class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		for (Employee employee : employees) {
			employeeRepository.insert(employee);
		}
		for(Compensation comp : compensations ) {
			compRepository.insert(comp);
		}
	}

}

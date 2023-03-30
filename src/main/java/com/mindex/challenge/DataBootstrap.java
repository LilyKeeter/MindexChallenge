package com.mindex.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.dao.EmployeeRepository;
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
	private static final String DATASTORE_LOCATION = "/static/employee_database.json";

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	public void init() {
		InputStream inputStream = this.getClass().getResourceAsStream(DATASTORE_LOCATION);

		Employee[] employees = null;

		try {
			employees = objectMapper.readValue(inputStream, Employee[].class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		// instantiate a hashmap for easy retrieval
		HashMap<String, Employee> employeeHashMap = new HashMap<String, Employee>();
		for (Employee employee : employees) {

			employeeHashMap.put(employee.getEmployeeId(), employee);
		}
		//HashSet for skipping duplicates
		HashSet<String> alreadyReported = new HashSet<String>();
		// since the directReports were originally instantiated to be mostly null with
		// only strings due to object mapping. loop through and replace them with their
		// actual object equivalent.
		for (Employee employeee : employees) {
			fixDirectReports(employeee, employeeHashMap, alreadyReported);
		}
	}

	private void fixDirectReports(Employee employee, HashMap<String, Employee> employeeHashMap,
			HashSet<String> alreadyReported) {
		if (alreadyReported.add(employee.getEmployeeId())) {
			if (employee.getDirectReports() != null) {
				List<Employee> directReports = new ArrayList<Employee>();
				for (Employee directReporter : employee.getDirectReports()) {
					fixDirectReports(employeeHashMap.get(directReporter.getEmployeeId()), employeeHashMap,
							alreadyReported);
					directReports.add(employeeHashMap.get(directReporter.getEmployeeId()));

				}

				employee.setDirectReports(directReports);
			}
			employeeRepository.insert(employee);
		}

	}
}

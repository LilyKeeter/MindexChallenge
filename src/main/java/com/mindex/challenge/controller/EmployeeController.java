package com.mindex.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.reporting.ReportingStructure;
import com.mindex.challenge.reporting.ReportingStructureHelper;
import com.mindex.challenge.service.EmployeeService;

@RestController
public class EmployeeController {

	private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

	@Autowired
	private ReportingStructureHelper reportingHelper;
	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/employee")
	public Employee create(@RequestBody Employee employee) {
		LOG.debug("Received employee create request for [{}]", employee);

		return employeeService.create(employee);
	}

	@GetMapping("/employee/{id}")
	public Employee read(@PathVariable String id) {
		LOG.debug("Received employee create request for id [{}]", id);

		return employeeService.read(id);
	}

	@PutMapping("/employee/{id}")
	public Employee update(@PathVariable String id, @RequestBody Employee employee) {
		LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

		employee.setEmployeeId(id);
		return employeeService.update(employee);
	}

	@GetMapping("/reporting/{id}")
	public ReportingStructure retrieveReportingStructure(@PathVariable String id) {
		Employee employee = employeeService.read(id);
		LOG.debug("Received reporting structure query request for id [{}] and employee [{}]", id, employee);
		ReportingStructure a = new ReportingStructure(employee, reportingHelper.generateNumberOfReports(employee));
		return a;
	}
}

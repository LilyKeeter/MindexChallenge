package com.mindex.challenge.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;

@Component
public class ReportingStructureHelper {
	@Autowired
	private EmployeeService employeeService;
	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureHelper.class);

	public int calculateNumOfReports(Employee reporter) {
		int count = 1;
		// loop through direct reporters of this employee if any
		if (reporter.getDirectReports() != null) {
			for (Employee reportee : reporter.getDirectReports()) {
				// add direct reporter counts of subordinates
				count += calculateNumOfReports(employeeService.read(reportee.getEmployeeId()));
			}
		}
		return count;

	}
	//for updating reports after a reporting object already exists
	public void updateNumberOfReports(ReportingStructure reporting) {
		// subtract one because the returned value includes root node
		Employee employee = reporting.getEmployee();
		LOG.debug("Starting calculating reporters for [{}]", employee.getEmployeeId());
		reporting.setNumberOfReports(calculateNumOfReports(employee) - 1);
		LOG.debug("Finished calculating reporters for [{}]", employee.getEmployeeId());
	}
	//for generating number of reports 
	public int generateNumberOfReports(Employee employee) {
		// subtract one because the returned value includes root node
		LOG.debug("Starting calculating reporters for [{}]", employee.getEmployeeId());
		int numberfReports = calculateNumOfReports(employee) - 1;
		LOG.debug("Finished calculating reporters for [{}]", employee.getEmployeeId());
		return numberfReports;
	}
}

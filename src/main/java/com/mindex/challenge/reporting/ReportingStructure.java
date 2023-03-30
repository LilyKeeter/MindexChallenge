package com.mindex.challenge.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import com.mindex.challenge.data.Employee;

@Configurable
public class ReportingStructure {
	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructure.class);
	private int numberOfReports;
	private Employee employee;
	public ReportingStructure(Employee employee) {
		this.employee = employee;
		this.numberOfReports = calculateNumOfReports(this.employee);
	}
	public int getNumberOfReports() {
		return this.numberOfReports;
	}
	public int updateNumberOfReports() {
		numberOfReports = calculateNumOfReports(this.employee);
		return this.numberOfReports;
	}

	public int calculateNumOfReports(Employee reporter) {
		int count;
		if(reporter == employee) { //don't count self
			count = 0;
		}
		else {
			count = 1; // start the counting at 1 for non root employees
		}
		//loop through direct reporters of this employee if any 
		if(reporter.getDirectReports() != null) {
			for(Employee reportee : reporter.getDirectReports()) { 
				LOG.debug(reportee.getFirstName());
				//add direct reporter counts of subordinates 
				count +=  calculateNumOfReports(reportee);
			}
		}
		return count;
		
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}

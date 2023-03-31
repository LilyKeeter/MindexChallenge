package com.mindex.challenge.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import com.mindex.challenge.data.Employee;

public class ReportingStructure {
	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructure.class);
	private int numberOfReports;
	private Employee employee;

	public ReportingStructure(Employee employee, int numberOfReports) {
		this.employee = employee;
		this.numberOfReports = numberOfReports;
	}

	public int getNumberOfReports() {
		return this.numberOfReports;
	}
	public void setNumberOfReports(int numberOfReports) {
		this.numberOfReports = numberOfReports;
	}



	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}

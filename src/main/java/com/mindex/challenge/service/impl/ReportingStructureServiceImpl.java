package com.mindex.challenge.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService{

	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);
	
	@Autowired
    private EmployeeService employeeService;
	
	@Override
	public ReportingStructure read(String employeeId) {
		LOG.debug("Getting reporting structure for employee with id [{}]", employeeId);
		
		ReportingStructure reportingStructure = new ReportingStructure();
		Employee employee = employeeService.read(employeeId);
		reportingStructure.setEmployee(employee);
		int totalReports = 0;
		LOG.debug("Retrieving all reports of employee with id [{}]", employeeId);
		if(employee.getDirectReports() != null) {
			List<Employee> allReports = new ArrayList<Employee>(employee.getDirectReports());
			for(int i = 0; i < allReports.size(); i++) {
				totalReports++;
				Employee report = employeeService.read(allReports.get(i).getEmployeeId());
				if(report.getDirectReports() != null) {
					allReports.addAll(report.getDirectReports());
				}
			}
		}
		reportingStructure.setNumberOfReports(totalReports);
		return reportingStructure;
	}

}

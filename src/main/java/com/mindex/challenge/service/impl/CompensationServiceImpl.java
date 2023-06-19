package com.mindex.challenge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;

@Service
public class CompensationServiceImpl implements CompensationService{
	
	private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);
	
	@Autowired
	private CompensationRepository compensationRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Override
	public Compensation create(Compensation compensation) {
		LOG.debug("Creating compensation [{}]", compensation);
		
		if(compensation.getEmployee() == null || compensation.getEmployee().getEmployeeId() == null) {
			throw new RuntimeException("No employee id found on compensation: " + compensation);
		}
		
		//make sure the employee exists, then only store the ID
		employeeService.read(compensation.getEmployee().getEmployeeId());
		Employee employee = new Employee();
		employee.setEmployeeId(compensation.getEmployee().getEmployeeId());
		compensation.setEmployee(employee);
		
		compensationRepository.insert(compensation);
		return compensation;
	}
	
	@Override
	public Compensation read(String employeeId) {
		LOG.debug("Reading compensation with employee id [{}]", employeeId);
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		
		Compensation compensation = compensationRepository.findByEmployee(employee);
		if(compensation == null) {
			throw new RuntimeException("No compensation found for employee id: " + employeeId);
		}
		
		return compensation;
	}

}

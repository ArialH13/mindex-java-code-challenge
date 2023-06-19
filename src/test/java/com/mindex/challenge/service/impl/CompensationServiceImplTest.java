package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
	
    private String compensationUrl;
    private String compensationIdUrl;
    
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Before
    public void setupEach() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }
    
    @After
    public void cleanupEach() {
    	mongoTemplate.getDb().drop();
    }
    
    @Test
    public void testCreate() {
    	//create employee to associate with compensation
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId("b783TEST-DATA-463b-a7e3-5de1c168beb3");
        testEmployee = mongoTemplate.insert(testEmployee);
        
        Compensation expectedCompensation = new Compensation();
        expectedCompensation.setEmployee(testEmployee);
        expectedCompensation.setSalary(100000);
        expectedCompensation.setEffectiveDate("06/17/2018");

        Compensation resultCompensation = 
        		restTemplate.postForEntity(compensationUrl, expectedCompensation, Compensation.class).getBody();

        assertNotNull(resultCompensation);
        assertCompensationEquivalence(expectedCompensation, resultCompensation);
    }  
    
    @Test
    public void testRead() {
    	//create employee to associate with compensation
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId("b63b-a7e3-5de1c168beb3");
        testEmployee = mongoTemplate.insert(testEmployee);
        
        Compensation expectedCompensation = new Compensation();
        expectedCompensation.setEmployee(testEmployee);
        expectedCompensation.setSalary(100000);
        expectedCompensation.setEffectiveDate("06/17/2018");
        expectedCompensation = mongoTemplate.insert(expectedCompensation);
        
        
        Compensation resultCompensation = 
        		restTemplate.getForEntity(compensationIdUrl, Compensation.class, expectedCompensation.getEmployee().getEmployeeId()).getBody();
        
        assertNotNull(resultCompensation);
        assertCompensationEquivalence(expectedCompensation, resultCompensation);
        
        
    }
    
    
        
    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals(expected.getEmployee().getEmployeeId(), actual.getEmployee().getEmployeeId());
    }


}

package com.mindex.challenge;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compUrl;
    private String compIdUrl;


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compUrl = "http://localhost:" + port + "/compensation";
        compIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreateReadUpdate() {
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployeeId(UUID.randomUUID().toString());
        testCompensation.setSalary(50);
        testCompensation.setEffectiveDate(LocalDate.of(2023, 01, 01));

        // Create checks
        Compensation createdComp = restTemplate.postForEntity(compUrl, testCompensation, Compensation.class).getBody();

        assertNotNull(createdComp.getEmployeeId());
        assertCompensationEquivalent(testCompensation, createdComp);
        
        // Read checks
        Compensation readCompensation = restTemplate.getForEntity(compIdUrl, Compensation.class, createdComp.getEmployeeId()).getBody();
        assertEquals(createdComp.getEmployeeId(), readCompensation.getEmployeeId());
        assertCompensationEquivalent(createdComp, readCompensation);


        // Update checks
        readCompensation.setSalary(60000);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Compensation updatedComp =
                restTemplate.exchange(compIdUrl,
                        HttpMethod.PUT,
                        new HttpEntity<Compensation>(readCompensation, headers),
                        Compensation.class,
                        readCompensation.getEmployeeId()).getBody();

        assertCompensationEquivalent(readCompensation, updatedComp);
    }

    private static void assertCompensationEquivalent(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEquals(expected.getSalary(), actual.getSalary());
    }
}

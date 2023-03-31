package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation comp) {
        LOG.debug("Creating compensation [{}]", comp);
        compensationRepository.insert(comp);

        return comp;
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Reading compensation with id [{}]", id);

        Compensation comp = compensationRepository.findByEmployeeId(id);

        if (comp == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return comp;
    }

    @Override
    public Compensation update(Compensation comp) {
        LOG.debug("Updating compensation [{}]", comp);

        return compensationRepository.save(comp);
    }
}

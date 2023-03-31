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

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

@RestController
public class CompensationController {
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private CompensationService compService;

	@PostMapping("/compensation")
	public Compensation create(@RequestBody Compensation comp) {
		LOG.debug("Received compensation create request for [{}]", comp);

		return compService.create(comp);
	}

	@GetMapping("/compensation/{id}")
	public Compensation read(@PathVariable String id) {
		LOG.debug("Received compensation read request for id [{}]", id);

		return compService.read(id);
	}

	@PutMapping("/compensation/{id}")
	public Compensation update(@PathVariable String id, @RequestBody Compensation comp) {
		LOG.debug("Received employee update request for employee id [{}]", id, comp);

		return compService.update(comp);
	}
}

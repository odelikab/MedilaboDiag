package com.medilaboDiag.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilaboDiag.proxies.MicroserviceNoteProxy;
import com.medilaboDiag.proxies.MicroservicePatientProxy;
import com.medilaboDiag.services.DiagService;

@RestController
@RequestMapping("/diag")
public class DiagController {

	private static final Logger logger = LoggerFactory.getLogger(DiagController.class);

	@Autowired
	MicroservicePatientProxy patientProxy;

	@Autowired
	MicroserviceNoteProxy noteProxy;

	@Autowired
	DiagService diagService;

	@GetMapping("/risque/{name}")
	public String getRisque(@PathVariable("name") String name) {

		return diagService.getRisque(name);

	}
}

package com.medilaboDiag.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilaboDiag.beans.PatientBean;
import com.medilaboDiag.proxies.MicroserviceNoteProxy;
import com.medilaboDiag.proxies.MicroservicePatientProxy;

@RestController
@RequestMapping("/diag")
public class DiagController {

	private static final Logger logger = LoggerFactory.getLogger(DiagController.class);

	@Autowired
	MicroservicePatientProxy patientProxy;

	@Autowired
	MicroserviceNoteProxy noteProxy;

	@GetMapping("/risque")
	public List<String> getRisque(String nom) {
		Optional<PatientBean> patientOpt = patientProxy.getPatient(nom);
		PatientBean patient = patientOpt.get();
		long age = getAge(patient.getDate_de_naissance());
		String patientGenre = patient.getGenre();
		List<String> count = noteProxy.getPatientNotesContaining(nom);
//		if(age>30) 
//				&& decl>=2 && decl<=5)
		return count;
//		
//		if(age<30 && gender=M && decl>=3)
//			(age<30 && gender=F && decl>=4)
//			(age>30 && decl==6 || decl==7)
//			return in danger;
//		
//		if(age<30 && gender=M && decl>=5)
//			(age<30 && gender=F && decl>=7)
//			(age>30 && decl>=8)
//		return Early onset;
	}

	public long getAge(Date dateBirth) {
		LocalDate dateOfBirth = LocalDate.parse(dateBirth.toString());
		LocalDate now = LocalDate.now();
		int age = dateOfBirth.until(now).getYears();
		return age;
	}
}

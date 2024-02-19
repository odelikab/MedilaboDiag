package com.medilaboDiag.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.medilaboDiag.beans.PatientBean;
import com.medilaboDiag.proxies.MicroserviceNoteProxy;
import com.medilaboDiag.proxies.MicroservicePatientProxy;

public class DiagService {

	@Autowired
	MicroservicePatientProxy patientProxy;

	@Autowired
	MicroserviceNoteProxy noteProxy;

	public String getRisque(String name) {

		Optional<PatientBean> patientOpt = patientProxy.getPatient(name);
		PatientBean patient = patientOpt.get();
		long age = getAge(patient.getDate_de_naissance());
		String patientGenre = patient.getGenre();
		List<String> declencheursList = noteProxy.getPatientNotesContaining(name);
		int countDeclencheurs = declencheursList.size();
		int seuil = 30;
		if (age > seuil && countDeclencheurs >= 2 && countDeclencheurs <= 5)
			return "borderline";
		else if (age < seuil && ((patientGenre.equals("M") && countDeclencheurs >= 3 && countDeclencheurs < 5)
				|| (patientGenre.equals("F") && countDeclencheurs >= 4 && countDeclencheurs < 7)))
			return "in danger";
		else if (age > seuil && (countDeclencheurs == 6 || countDeclencheurs == 7))
			return "in danger";
		else if (age < seuil && patientGenre.equals("M") && countDeclencheurs >= 5)
			return "Early onset";
		else if (age < seuil && patientGenre.equals("F") && countDeclencheurs >= 7)
			return "Early onset";
		else if (age > seuil && countDeclencheurs >= 8)
			return "Early onset";
		else
			return "none";
	}

	public long getAge(Date dateBirth) {
		LocalDate dateOfBirth = LocalDate.parse(dateBirth.toString());
		LocalDate now = LocalDate.now();
		int age = dateOfBirth.until(now).getYears();
		return age;
	}

//	
//	if(age<30 && gender=M && decl>=3)
//		(age<30 && gender=F && decl>=4)
//		(age>30 && decl==6 || decl==7)
//		return in danger;
//	
//	if(age<30 && gender=M && decl>=5)
//		(age<30 && gender=F && decl>=7)
//		(age>30 && decl>=8)
//	return Early onset;
}

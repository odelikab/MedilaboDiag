package com.medilaboDiag.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medilaboDiag.beans.PatientBean;
import com.medilaboDiag.proxies.MicroserviceNoteProxy;
import com.medilaboDiag.proxies.MicroservicePatientProxy;

@Service
public class DiagService {

	@Autowired
	MicroservicePatientProxy patientProxy;

	@Autowired
	MicroserviceNoteProxy noteProxy;

	public String getRisque(Integer id) {

		Optional<PatientBean> patientOpt = patientProxy.getPatientById(id);
		PatientBean patient = patientOpt.get();
		long patientAge = getAge(patient.getDate_de_naissance());
		String patientGenre = patient.getGenre();
		List<String> declencheursList = noteProxy.getPatientNotesContaining(patient.getNom());

		int countDeclencheurs = declencheursList.size();
		final String GENREM = "M";
		final String GENREF = "F";
		int ageSeuilDetection = 30;
		final int MINDECLENCHEURBORDERLINE = 2;
		final int MAXDECLENCHEURBORDERLINE = 5;
		final int MINDECLENCHEURDANGERM = 3;
		final int MAXDECLENCHEURDANGERM = 5;
		final int MINDECLENCHEURDANGERF = 4;
		final int MAXDECLENCHEURDANGERF = 7;
		final int MINDECLENCHEURDANGER_SUP_AGESEUIL = 6;
		final int MAXDECLENCHEURDANGER_SUP_AGESEUIL = 7;
		final int DECLENCHEUREARLYONSETM = 5;
		final int DECLENCHEUREARLYONSETF = 7;
		final int MINDECLENCHEUREARLYONSET = 8;

		if (patientAge > ageSeuilDetection && countDeclencheurs >= MINDECLENCHEURBORDERLINE
				&& countDeclencheurs <= MAXDECLENCHEURBORDERLINE)
			return NiveauxRisque.borderline.toString();

		else if (patientAge < ageSeuilDetection && ((patientGenre.equals(GENREM) && countDeclencheurs >= MINDECLENCHEURDANGERM
				&& countDeclencheurs < MAXDECLENCHEURDANGERM)
				|| (patientGenre.equals(GENREF) && countDeclencheurs >= MINDECLENCHEURDANGERF
						&& countDeclencheurs < MAXDECLENCHEURDANGERF)))
			return NiveauxRisque.in_danger.toString();

		else if (patientAge > ageSeuilDetection && (countDeclencheurs == MINDECLENCHEURDANGER_SUP_AGESEUIL
				|| countDeclencheurs == MAXDECLENCHEURDANGER_SUP_AGESEUIL))
			return NiveauxRisque.in_danger.toString();

		else if (patientAge < ageSeuilDetection && patientGenre.equals(GENREM) && countDeclencheurs >= DECLENCHEUREARLYONSETM)
			return NiveauxRisque.early_onset.toString();

		else if (patientAge < ageSeuilDetection && patientGenre.equals(GENREF) && countDeclencheurs >= DECLENCHEUREARLYONSETF)
			return NiveauxRisque.early_onset.toString();

		else if (patientAge > ageSeuilDetection && countDeclencheurs >= MINDECLENCHEUREARLYONSET)
			return NiveauxRisque.early_onset.toString();

		else
			return NiveauxRisque.none.toString();
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

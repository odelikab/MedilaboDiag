package com.medilaboDiag.proxies;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medilaboDiag.beans.PatientBean;

@FeignClient(name = "medilaboPatient", url = "localhost:8081")

public interface MicroservicePatientProxy {

	@GetMapping(value = "/patients/list")
	List<PatientBean> listeDesPatients();

	@GetMapping(value = "/patients/nom/{nom}")
	Optional<PatientBean> getPatientByName(@PathVariable("nom") String nom);

	@GetMapping(value = "/patients/{id}")
	Optional<PatientBean> getPatientById(@PathVariable("id") Integer id);

}

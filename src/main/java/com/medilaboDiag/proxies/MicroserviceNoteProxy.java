package com.medilaboDiag.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.medilaboDiag.beans.NoteBean;

@FeignClient(name = "medilaboNote", url = "localhost:8083")

public interface MicroserviceNoteProxy {

	@GetMapping("/notes/findAllNotes")
	public List<NoteBean> getAllNotes();

	@GetMapping("/notes/{id}")
	public List<NoteBean> getPatientNotes(@PathVariable("id") Integer id);

	@GetMapping("/notes/custom")
	public List<String> getPatientNotesContaining(@RequestParam String patient);

}

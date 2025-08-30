package com.stb.credit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stb.credit.dto.DocumentDTO;
import com.stb.credit.service.DocumentsService;



@RestController
@RequestMapping("/api/documents")
public class DocumentsController {

	@Autowired
	private DocumentsService documentsService;
	
	@PostMapping("/")
	public List<DocumentDTO> find(@RequestBody DocumentDTO documentsDTO) {

		return documentsService.find(documentsDTO);
	}
	
 

}

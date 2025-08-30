package com.stb.credit.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stb.credit.dto.DocumentDTO;
import com.stb.credit.models.Document;
import com.stb.credit.repository.DocumentRepository;
import com.stb.credit.service.CloudinaryService;
import com.stb.credit.service.DocumentsService;



@Service
public class DocumentsServiceImpl implements DocumentsService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentsServiceImpl.class);

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private DocumentRepository documentsRepository;


	@Autowired
    private CloudinaryService cloudinaryService;

	@Override
	public List<DocumentDTO> find(DocumentDTO documentsDTO) {


		   List<Document> documents = documentsRepository.findByLoanRequestIdAndCustomerId(documentsDTO.getLoanRequestId(), documentsDTO.getCustomerId());

		    List<DocumentDTO> dtos = new ArrayList<>();

		    for (Document document : documents) {
		        DocumentDTO dto = mapper.map(document, DocumentDTO.class);

		        if (document.getUrl() != null && !document.getUrl().isEmpty()) {
		            try {
		                byte[] fileData = cloudinaryService.downloadFile(document.getUrl());
		                dto.setFileBytes(fileData);
		            } catch (IOException e) {
		                logger.warn("Failed to fetch file from Cloudinary for doc ID: {}", document.getId(), e);
		            }
		        }

		        dtos.add(dto);
		    }
		    return dtos;
	}


}

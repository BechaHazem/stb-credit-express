package com.stb.credit.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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


		   List<Document> documents;
		    if (documentsDTO.getListName() != null && documentsDTO.getListName().size() > 0) {
		        documents = documentsRepository.findByLoanRequestIdAndCustomerIdAndNameIn(
		                documentsDTO.getLoanRequestId(),
		                documentsDTO.getCustomerId(),
		                documentsDTO.getListName()
		        );
		    } else {
		        documents = documentsRepository.findByLoanRequestIdAndCustomerId(
		                documentsDTO.getLoanRequestId(),
		                documentsDTO.getCustomerId()
		        );
		    }
		    List<DocumentDTO> dtos = new ArrayList<>();

		    for (Document document : documents) {
		        DocumentDTO dto = mapper.map(document, DocumentDTO.class);

		        if (document.getUrl() != null && !document.getUrl().isEmpty()) {
		            try {
		                byte[] fileData = cloudinaryService.downloadFile(document.getUrl());
		                dto.setFileBytes(Base64.getEncoder().encodeToString(fileData));
		            } catch (IOException e) {
		                logger.warn("Failed to fetch file from Cloudinary for doc ID: {}", document.getId(), e);
		            }
		        }

		        dtos.add(dto);
		    }
		    return dtos;
	}

	@Override
	public List<DocumentDTO> saveDocuments(List<DocumentDTO> documents) {
	    List<DocumentDTO> savedDocs = new ArrayList<>();

	    for (DocumentDTO dto : documents) {
	        if (dto.getFileBytes() != null) {
	            // Clean base64 prefix if needed
	            String base64Data = dto.getFileBytes();
	            if (base64Data.contains(",")) {
	                base64Data = base64Data.split(",")[1];
	            }

	            byte[] fileData = Base64.getDecoder().decode(base64Data);

	            // Upload to Cloudinary
	            String url = cloudinaryService.uploadFile(fileData, dto.getFileName());

	            // Persist
	            Document entity = new Document();
	            entity.setId(dto.getId());
	            entity.setCustomerId(dto.getCustomerId());
	            entity.setLoanRequestId(dto.getLoanRequestId());
	            entity.setName(dto.getName());
	            entity.setContentType(dto.getContentType());
	            entity.setUrl(url);

	            Document saved = documentsRepository.save(entity);

	            DocumentDTO response = new DocumentDTO();
	            response.setId(saved.getId());
	            response.setUrl(saved.getUrl());
	            response.setCustomerId(saved.getCustomerId());
	            response.setLoanRequestId(saved.getLoanRequestId());
	            response.setName(saved.getName());
	            response.setContentType(saved.getContentType());
	            savedDocs.add(response);
	        }
	    }
	    return savedDocs;
	}


}

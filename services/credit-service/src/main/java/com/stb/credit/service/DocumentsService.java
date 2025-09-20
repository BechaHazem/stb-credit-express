package com.stb.credit.service;

import java.util.List;

import com.stb.credit.dto.DocumentDTO;

public interface DocumentsService {

	List<DocumentDTO> find(DocumentDTO documentDTO);
    List<DocumentDTO> saveDocuments(List<DocumentDTO> documents);
}

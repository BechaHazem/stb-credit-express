package com.stb.credit.service.impl;

import com.stb.credit.models.CreditType;
import com.stb.credit.repository.CreditTypeRepository;
import com.stb.credit.service.CreditTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditTypeServiceImpl implements CreditTypeService {
    @Autowired
    CreditTypeRepository creditTypeRepository;

    @Override
    public CreditType addType(CreditType type) {
        return creditTypeRepository.save(type);
    }

    @Override
    public CreditType updateType(CreditType type, long id) {
        CreditType c = creditTypeRepository.getReferenceById(id);

        c.setType(type.getType());
        c.setApr(type.getApr());
        return creditTypeRepository.save(c);
    }

    @Override
    public void deleteType(long id) {
          creditTypeRepository.deleteById(id);
    }

    @Override
    public CreditType getType(long id) {
        return creditTypeRepository.getReferenceById(id);
    }

    @Override
    public List<CreditType> getAllTypes() {
        return creditTypeRepository.findAll();
    }
}

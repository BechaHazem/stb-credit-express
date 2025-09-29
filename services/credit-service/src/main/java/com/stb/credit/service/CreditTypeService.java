package com.stb.credit.service;

import com.stb.credit.models.CreditType;

import java.util.List;

public interface CreditTypeService {
    public CreditType addType(CreditType  type);
    public CreditType updateType(CreditType type,long id);
    public void deleteType(long id);
    public CreditType getType(long id);
    public List<CreditType> getAllTypes();

}

package com.stb.credit.service;

import com.stb.credit.dto.ProductRequest;
import com.stb.credit.models.Product;
import com.stb.credit.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;
    public Product create(ProductRequest dto) {
        Product p = new Product();
        mapDto(p, dto);
        return repo.save(p);
    }

    public List<Product> findAll() {
        return repo.findAll();
    }

    public Product findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product update(Long id, ProductRequest dto) {
        Product p = findById(id);
        mapDto(p, dto);
        return repo.save(p);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    private void mapDto(Product p, ProductRequest dto) {
        p.setCode(dto.code());
        p.setDescription(dto.description());
        p.setRate(dto.rate());
        p.setMinimumAmount(dto.minimumAmount());
        p.setMaximumAmount(dto.maximumAmount());
        p.setMinimumTerm(dto.minimumTerm());
        p.setMaximumTerm(dto.maximumTerm());
        p.setMinimumAge(dto.minimumAge());
        p.setMaximumAge(dto.maximumAge());
        p.setMinimumDeferredPeriod(dto.minimumDeferredPeriod());
        p.setMaximumDeferredPeriod(dto.maximumDeferredPeriod());
        p.setIssueFeeAmount1(dto.issueFeeAmount1());
    }
}

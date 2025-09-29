package com.stb.credit.controller;

import com.stb.credit.models.CreditType;
import com.stb.credit.service.CreditTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/credit-types")
public class CreditTypeController {

    @Autowired
    private CreditTypeService creditTypeService;

    @PostMapping
    public CreditType addType(@RequestBody CreditType type) {
        return creditTypeService.addType(type);
    }

    @PutMapping("/{id}")
    public CreditType updateType(@PathVariable long id, @RequestBody CreditType type) {
        return creditTypeService.updateType(type, id);
    }

    @DeleteMapping("/{id}")
    public void deleteType(@PathVariable long id) {
        creditTypeService.deleteType(id);
    }

    @GetMapping("/{id}")
    public CreditType getType(@PathVariable long id) {
        return creditTypeService.getType(id);
    }

    @GetMapping
    public List<CreditType> getAllTypes() {
        return creditTypeService.getAllTypes();
    }
}
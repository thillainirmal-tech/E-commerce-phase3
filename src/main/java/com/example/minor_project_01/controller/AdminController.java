package com.example.minor_project_01.controller;


import com.example.minor_project_01.dto.CreateCompanyRequestDto;
import com.example.minor_project_01.dto.CreateResponseDTO;
import com.example.minor_project_01.dto.ResponseDTO;
import com.example.minor_project_01.dto.SellerDTO;
import com.example.minor_project_01.exception.NotFoundException;
import com.example.minor_project_01.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ADMIN Panel APIs", description = "APIs for managing Sellers,Company,Category")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;



    @PostMapping("/company")
    public ResponseEntity<CreateResponseDTO> createCompany(@RequestBody CreateCompanyRequestDto companyRequest){
        LOGGER.info("Creating company");
        return ResponseEntity.ok(adminService.createCompany(companyRequest));
    }

    @PostMapping("/seller")
    public ResponseEntity<CreateResponseDTO> createSeller(@RequestBody @Valid SellerDTO sellerDTO){
        LOGGER.info("Creating seller");
        return ResponseEntity.ok(adminService.createSeller(sellerDTO));
    }

    @GetMapping("/seller")
    public ResponseEntity<List<SellerDTO>> getAllSellers(){
        LOGGER.info("Getting all sellers");
        return ResponseEntity.ok(adminService.getAllSellers());
    }

    @DeleteMapping("/seller/{id}")
    public ResponseEntity<ResponseDTO> deleteSeller(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(adminService.deleteSeller(id));
    }

}

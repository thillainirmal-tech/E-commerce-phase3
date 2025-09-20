package com.example.minor_project_01.controller;


import com.example.minor_project_01.dto.CreateResponseDTO;
import com.example.minor_project_01.dto.ProductDTO;
import com.example.minor_project_01.dto.ResponseDTO;
import com.example.minor_project_01.entity.User;
import com.example.minor_project_01.exception.NotFoundException;
import com.example.minor_project_01.service.SellerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Tag(name = "Seller Panel API",description = "For Creating,Updating,deleting products")
@RestController
@RequestMapping("/api/seller")
public class SellerController {

    private static Logger LOGGER = LoggerFactory.getLogger(SellerController.class);

    @Value("${image.upload.home}")
    private String imageUploadHome;

    @Value("${static.domain.name}")
    private String staticDomainName;


    @Autowired
    private SellerService sellerService;


    @PostMapping("/product")
    public ResponseEntity<CreateResponseDTO> createProduct(@RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(sellerService.createProduct(productDTO));
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getProducts(){
        return ResponseEntity.ok(sellerService.getProducts());
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) throws NotFoundException {
        return ResponseEntity.ok(sellerService.updateProduct(id,productDTO));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<ResponseDTO> deleteProduct(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(sellerService.deleteProduct(id));
    }



//    @PutMapping("/order/{id}/accept")
//    public ResponseEntity<ResponseDTO>  submitOrder(@PathVariable Long id){
//
//    }

    @PostMapping("/product/image")
    public ResponseEntity<String> imageUpload(@RequestParam MultipartFile file){
        LOGGER.info("File Name:{}",file.getOriginalFilename());
        String fileName = UUID.randomUUID()+"_"+file.getOriginalFilename();
        String uploadPath = imageUploadHome+fileName;
        String publicUrl = staticDomainName+"content/"+fileName;
        try {
            file.transferTo(new File(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(publicUrl);
    }


    @PostMapping("/product/bulk")
    public ResponseEntity<List<CreateResponseDTO>> createProductInBulk(@RequestParam MultipartFile file) throws IOException {
        LOGGER.info("File Name:{}",file.getOriginalFilename());
        List<CreateResponseDTO> responseDTOList = new ArrayList<>();
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        List<CSVRecord> csvRecords = csvParser.getRecords();
        for(CSVRecord csvRecord: csvRecords){
            ProductDTO requestDto = new ProductDTO();
            requestDto.setName(csvRecord.get("name"));
            requestDto.setDescription(csvRecord.get("description"));
            requestDto.setPrice(Double.valueOf(csvRecord.get("price")));
            requestDto.setActive(Boolean.valueOf(csvRecord.get("active")));
            requestDto.setImageUrl(csvRecord.get("imageUrl"));
            requestDto.setCategoryId(Long.valueOf(csvRecord.get("categoryId")));
            CreateResponseDTO responseDTO = sellerService.createProduct(requestDto);
            responseDTOList.add(responseDTO);
        }
        return ResponseEntity.ok(responseDTOList);

    }







}
/*
image=10MB
Heap=1000MB

100 concurrent users are uploading file of 10MB.
16KB * 100
1600KB ~ 1.6 MB
 */

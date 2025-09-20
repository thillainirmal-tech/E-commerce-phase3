package com.example.minor_project_01.controller;

import com.example.minor_project_01.dto.PasswordReqDto;
import com.example.minor_project_01.dto.ProductDTO;
import com.example.minor_project_01.dto.SignUpDto;
import com.example.minor_project_01.dto.UserDto;
import com.example.minor_project_01.service.CustomerService;
import com.example.minor_project_01.service.PublicService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Search API",description = "visible to public")
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PublicService  publicService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProductsByKeyword(@RequestParam String keyword, @RequestParam Integer pageSize, @RequestParam Integer pageNo){
        Pageable pageable = Pageable.ofSize(pageSize)
                .withPage(pageNo);
        return ResponseEntity.ok(customerService.getProductByKeyword(keyword,pageable));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpDto> createcustomer(@RequestBody UserDto userdto){
        SignUpDto signUpDto = publicService.createuser(userdto);
        return ResponseEntity.ok(signUpDto);
    }

    @PostMapping("/resetpassword/{token}")
    public ResponseEntity<String> resetpassword(@RequestBody PasswordReqDto passwordReqDto,@PathVariable String token){
        String resetpassword = publicService.resetpassword(passwordReqDto,token);
        return ResponseEntity.ok(resetpassword);
    }

}

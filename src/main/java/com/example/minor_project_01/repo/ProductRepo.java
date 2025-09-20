package com.example.minor_project_01.repo;

import com.example.minor_project_01.entity.Company;
import com.example.minor_project_01.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    List<Product> findByCompany(Company company);

    // Select from Product where name like '%keyword%'
    List<Product> findByNameContaining(String name, Pageable pageable);

}

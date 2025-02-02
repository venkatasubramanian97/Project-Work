package com.Business.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Business.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	Product findByProductName(String productName);
}

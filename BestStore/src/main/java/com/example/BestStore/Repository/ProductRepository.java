package com.example.BestStore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BestStore.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

}

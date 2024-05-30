package com.example.BestStore.Service;

import java.util.List;

import com.example.BestStore.Entity.Product;

public interface ProductService {
	List<Product> getAllProduct();
	void saveProduct(Product product);
	Product getProductById(int id);
	void deleteProductById(int id);

}

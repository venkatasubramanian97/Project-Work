package com.example.BestStore.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BestStore.Entity.Product;
import com.example.BestStore.Repository.ProductRepository;

@Service
public class ProductServiceimpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public void saveProduct(Product product) {
		productRepository.save(product);
	}

	@Override
	public Product getProductById(int id) {
		Optional<Product> optional = productRepository.findById(id);
		Product product;
		if (optional.isPresent()) {
			product = optional.get();
		} else {
			throw new RuntimeException("Product is not found for id : " + id);
		}
		return product;
	}

	@Override
	public void deleteProductById(int id) {
		productRepository.deleteById(id);
	}

}

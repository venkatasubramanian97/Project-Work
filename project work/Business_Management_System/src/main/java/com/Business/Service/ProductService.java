package com.Business.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Business.Entity.Product;
import com.Business.Repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	// add Product
	public Product addProduct(Product p) {
		return this.productRepository.save(p);
	}

	// getAll products
	public List<Product> getAllProducts() {
		List<Product> products = this.productRepository.findAll();
		return products;
	}

	// get Single Product
	public Product getProduct(int id) {
		Optional<Product> optional = this.productRepository.findById(id);
		Product product = optional.get();
		return product;
	}

	// update Product
	public void updateproduct(Product p, int id) {
		p.setProductId(id);
		Optional<Product> optional = this.productRepository.findById(id);
		Product prod = optional.get();

		if (prod.getProductId() == id) {
			this.productRepository.save(p);
		}
	}

	// delete product
	public void deleteProduct(int id) {
		this.productRepository.deleteById(id);
	}

	// Get Product By Name
	public Product getProductByName(String name) {
		Product product = this.productRepository.findByProductName(name);
		if (product != null) {
			return product;
		}
		return null;
	}
}

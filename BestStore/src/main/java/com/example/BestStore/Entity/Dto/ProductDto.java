package com.example.BestStore.Entity.Dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ProductDto {
	@NotEmpty(message = "Name is required")
	private String name;
	@NotEmpty(message = "Brand is required")
	private String brand;
	@NotEmpty(message = "Category is required")
	private String category;
	@Min(0)
	private double price;
	@Size(min = 10, message = "Description should be at least 10 characters")
	@Size(max = 500, message = "Description cannot execeed 500 characters")
	private String description;

	private MultipartFile imageFileName;

	public ProductDto() {
	}

	public ProductDto(@NotEmpty(message = "Name is required") String name,
			@NotEmpty(message = "Brand is required") String brand,
			@NotEmpty(message = "Category is required") String category, @Min(0) double price,
			@Size(min = 10, message = "Description should be at least 10 characters") @Size(max = 500, message = "Description cannot execeed 500 characters") String description,
			MultipartFile imageFileName) {
		super();
		this.name = name;
		this.brand = brand;
		this.category = category;
		this.price = price;
		this.description = description;
		this.imageFileName = imageFileName;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(MultipartFile imageFileName) {
		this.imageFileName = imageFileName;
	}
}

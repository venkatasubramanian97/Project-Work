package com.Business.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Business.Entity.Product;
import com.Business.LoginCredential.AdminLogin;
import com.Business.LoginCredential.UserLogin;
import com.Business.Service.ProductService;

@Controller
public class HomeController {
	@Autowired
	private ProductService productService;

	@GetMapping("/home")
	public String home() {
		return "Home.html";
	}

	@GetMapping("/products")
	public String products(Model model) {
		List<Product> allProducts = this.productService.getAllProducts();
		model.addAttribute("products", allProducts);
		return "Product/Products.html";
	}

	@GetMapping("/location")
	public String location() {
		return "Locate_us.html";
	}

	@GetMapping("/about")
	public String about() {
		return "About.html";
	}

	@GetMapping("/ALogin")
	public String Alogin(Model model) {
		model.addAttribute("adminLogin", new AdminLogin());
		return "Admin/ALogin.html";
	}

	@GetMapping("/ULogin")
	public String Ulogin(Model model) {
		model.addAttribute("userLogin", new UserLogin());
		return "User/ULogin.html";
	}

	@GetMapping("/PULogin")
	public String showLoginPage(@RequestParam(value = "productName", required = false) String productName,
			Model model) {
		model.addAttribute("puserLogin", new UserLogin());
		model.addAttribute("productName", productName);
		return "User/PULogin";
	}
	
	@GetMapping("/Logout")
	public String logout(Model model) {
		model.addAttribute("userLogout", new UserLogin());
		return "User/Logout.html";
	}
}

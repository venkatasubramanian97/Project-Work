package com.Business.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Business.BasicLogics.Logic;
import com.Business.Entity.Orders;
import com.Business.Entity.Product;
import com.Business.Entity.User;
import com.Business.Service.OrdersService;
import com.Business.Service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private HttpSession session;

	@GetMapping("/addProduct")
	public String addProductForm() {
		return "Product/Add_Product.html";
	}

	@PostMapping("/addingProduct")
	public String addProduct(@ModelAttribute Product product, @RequestParam("imageFile") MultipartFile imageFile)
			throws IOException {
		String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
		String uploadDir = System.getProperty("user.dir") + "/Images/";
		Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try (InputStream inputStream = imageFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			product.setProductImage("/Images/" + fileName);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Could not save image file: " + fileName, e);
		}

		productService.addProduct(product);
		return "redirect:/admin/services";
	}

	@GetMapping("/updateProduct/{productId}")
	public String updateProduct(@PathVariable("productId") int id, Model model) {
		Product product = this.productService.getProduct(id);
		model.addAttribute("product", product);
		return "Product/Update_Product.html";
	}

	@PostMapping("/updatingProduct/{productId}")
	public String updateProduct(@PathVariable("productId") int id, @ModelAttribute Product product,
			@RequestParam("imageFile") MultipartFile imageFile) throws IOException {

		if (!imageFile.isEmpty()) {
			String fileName = imageFile.getOriginalFilename();
			String uploadDir = "Images/";
			Path uploadPath = Paths.get(uploadDir).normalize().toAbsolutePath();

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			try (InputStream inputStream = imageFile.getInputStream()) {
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				product.setProductImage("/Images/" + fileName);
			} catch (IOException e) {
				throw new IOException("Could not save image file: " + fileName, e);
			}
		} else {
			Product existingProduct = this.productService.getProduct(id);
			product.setProductImage(existingProduct.getProductImage());
		}

		this.productService.updateproduct(product, id);
		return "redirect:/admin/services";
	}

	@GetMapping("/deleteProduct/{productId}")
	public String delete(@PathVariable("productId") int id) {
		this.productService.deleteProduct(id);
		return "redirect:/admin/services";
	}

	@PostMapping("/product/search")
	public String seachHandler(@RequestParam("productName") String name, HttpSession session, Model model) {
		User user = (User) session.getAttribute("currentUser");
		if (user == null) {
			return "redirect:/puserlogin";
		}

		Product product = this.productService.getProductByName(name);
		if (product == null) {
			model.addAttribute("message", "SORRY...! Product Unavailable");
		}

		model.addAttribute("name", user.getUserName());
		model.addAttribute("product", product);
		return "Product/BuyProduct.html";
	}

	@GetMapping("/product/search")
	public String searchHandler(@RequestParam("productName") String name, HttpSession session, Model model) {
		User user = (User) session.getAttribute("currentUser");
		if (user == null) {
			return "redirect:/puserlogin";
		}

		Product product = this.productService.getProductByName(name);

		if (product == null) {
			model.addAttribute("message", "SORRY...! Product Unavailable");
		}

		model.addAttribute("name", user.getUserName());
		model.addAttribute("product", product);
		return "Product/BuyProduct.html";
	}

	@PostMapping("/product/order")
	public String orderHandler(@ModelAttribute Orders order, Model model) {
		User user = (User) session.getAttribute("currentUser");

		if (user == null) {
			model.addAttribute("error", "User is not logged in.");
			return "User/ULogin.html";
		}

		double totalAmount = Logic.countTotal(order.getOrderPrice(), order.getOrderQuantity());
		order.setTotalAmmout(totalAmount);
		order.setUser(user);
		Date d = new Date();
		order.setOrderDate(d);
		this.ordersService.saveOrder(order);
		model.addAttribute("amount", totalAmount);
		return "Order_success.html";
	}

	@GetMapping("/product/back")
	public String back(Model model) {
		User user = (User) session.getAttribute("currentUser");
		List<Orders> orders = this.ordersService.getOrdersForUser(user);
		model.addAttribute("orders", orders);
		model.addAttribute("name", user.getUserName());
		return "Product/BuyProduct.html";
	}
}

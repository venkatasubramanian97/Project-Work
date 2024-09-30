package com.Business.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.Business.Entity.Admin;
import com.Business.Entity.Orders;
import com.Business.Entity.Product;
import com.Business.Entity.User;
import com.Business.LoginCredential.AdminLogin;
import com.Business.Service.AdminService;
import com.Business.Service.OrdersService;
import com.Business.Service.ProductService;
import com.Business.Service.UserService;

@Controller
public class AdminController {
	@Autowired
	private UserService userservice;
	@Autowired
	private AdminService adminService;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrdersService ordersService;

	@PostMapping("/adminLogin")
	public String getAllData(@ModelAttribute AdminLogin login, Model model) {
		String email = login.getAdminEmail();
		String password = login.getAdminPassword();

		if (adminService.validateAdminCredentials(email, password)) {
			return "redirect:/admin/services";
		} else {
			model.addAttribute("error", "Invalid email or password");
			return "Admin/ALogin.html";
		}
	}

	@GetMapping("/admin/services")
	public String returnBack(Model model) {
		List<User> users = this.userservice.getAllUser();
		List<Admin> admins = this.adminService.getAll();
		List<Product> products = this.productService.getAllProducts();
		List<Orders> orders = this.ordersService.getOrders();
		model.addAttribute("users", users);
		model.addAttribute("admins", admins);
		model.addAttribute("products", products);
		model.addAttribute("orders", orders);

		return "Admin/Admin_Page.html";
	}

	@GetMapping("/addAdmin")
	public String addAdminPage() {
		return "Admin/Add_Admin.html";
	}

	@PostMapping("addingAdmin")
	public String addAdmin(@ModelAttribute Admin admin) {
		this.adminService.addAdmin(admin);
		return "redirect:/admin/services";
	}

	@GetMapping("/updateAdmin/{adminId}")
	public String update(@PathVariable("adminId") int id, Model model) {
		Admin admin = this.adminService.getAdmin(id);
		model.addAttribute("admin", admin);
		return "Admin/Update_Admin.html";
	}

	@PostMapping("/updatingAdmin/{adminId}")
	public String updateAdmin(@PathVariable int adminId, @ModelAttribute Admin admin) {
		this.adminService.updateAdmin(adminId, admin);
		return "redirect:/admin/services";
	}

	@GetMapping("/deleteAdmin/{id}")
	public String deleteAdmin(@PathVariable("id") int id) {
		this.adminService.delete(id);
		return "redirect:/admin/services";
	}
}

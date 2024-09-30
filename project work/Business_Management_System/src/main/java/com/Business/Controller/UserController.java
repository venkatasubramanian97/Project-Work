package com.Business.Controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Business.Entity.Orders;
import com.Business.Entity.User;
import com.Business.LoginCredential.UserLogin;
import com.Business.Service.OrdersService;
import com.Business.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	private UserService userservice;
	@Autowired
	private OrdersService ordersService;

	@GetMapping("/userlogin")
	public String userLogin(@ModelAttribute("userLogin") UserLogin login, HttpSession session, Model model) {
		String email = login.getUserEmail();
		String password = login.getUserPassword();
		if (userservice.validateLoginCredentials(email, password)) {
			User users = this.userservice.getUserByEmail(email);
			List<Orders> orders = this.ordersService.getOrdersForUser(users);
			model.addAttribute("orders", orders);
			session.setAttribute("currentUser", users);
			model.addAttribute("name", users.getUserName());
			return "Product/BuyProduct.html";
		} else {
			model.addAttribute("error2", "Invalid email or password");
			return "User/ULogin.html";
		}
	}

	@PostMapping("/puserlogin")
	public String puserLogin(@ModelAttribute("puserLogin") UserLogin login,
			@RequestParam(value = "productName", required = false) String productName, HttpSession session,
			Model model) {
		String email = login.getUserEmail();
		String password = login.getUserPassword();
		User user = this.userservice.getUserByEmail(email);
		if (user != null && userservice.validateLoginCredentials(email, password)) {
			session.setAttribute("currentUser", user);

			if (productName != null && !productName.isEmpty()) {
				return "redirect:/product/search?productName=" + URLEncoder.encode(productName, StandardCharsets.UTF_8);
			} else {
				return "redirect:/product/search";
			}
		} else {
			model.addAttribute("error2", "Invalid email or password");
			return "redirect:/products";
		}
	}

	@PostMapping("/userlogout")
	public String userLogout(@RequestParam("action") String action, @ModelAttribute("userLogout") UserLogin login,
			HttpSession session, Model model) {

		String email = login.getUserEmail();
		String password = login.getUserPassword();

		if (userservice.validateLoginCredentials(email, password)) {
			User users = this.userservice.getUserByEmail(email);
			int userId = users.getUserId();

			if ("update".equals(action)) {
				return "redirect:/userUpdate/" + userId;
			} else if ("logout".equals(action)) {
				session.invalidate();
				return "redirect:/userDelete/" + userId;
			}
		} else {
			model.addAttribute("error2", "Invalid email or password");
			return "User/Logout.html";
		}

		return "User/Logout.html";
	}

	@GetMapping("/addUser")
	public String addUser() {
		return "User/Add_User.html";
	}

	@PostMapping("/addingUser")
	public String addUser(@ModelAttribute User user) {
		this.userservice.addUser(user);
		return "redirect:/admin/services";
	}

	@GetMapping("/updateUser/{userId}")
	public String updateUserPage(@PathVariable("userId") int id, Model model) {
		User user = this.userservice.getUser(id);
		model.addAttribute("user", user);
		return "User/Update_User.html";
	}

	@GetMapping("/updatingUser/{id}")
	public String updateUser(@ModelAttribute User user, @PathVariable("id") int id) {
		this.userservice.updateUser(user, id);
		return "redirect:/admin/services";
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") int id) {
		this.userservice.deleteUser(id);
		return "redirect:/admin/services";
	}

	@GetMapping("/userAdd")
	public String userAdd() {
		return "User/User_Add.html";
	}

	@PostMapping("/userAdding")
	public String useradd(@ModelAttribute User user) {
		this.userservice.addUser(user);
		return "User/ULogin.html";
	}

	@GetMapping("/userUpdate/{userId}")
	public String updateUser(@PathVariable("userId") int id, Model model) {
		User user = this.userservice.getUser(id);
		model.addAttribute("user", user);
		return "User/User_Update.html";
	}

	@GetMapping("/userUpdating/{id}")
	public String updateUserdetails(@ModelAttribute User user, @PathVariable("id") int id) {
		this.userservice.updateUser(user, id);
		return "User/Logout.html";
	}

	@GetMapping("/userDelete/{id}")
	public String deleteUserdetails(@PathVariable("id") int id) {
		this.userservice.deleteUser(id);
		return "User/Logout.html";
	}
}

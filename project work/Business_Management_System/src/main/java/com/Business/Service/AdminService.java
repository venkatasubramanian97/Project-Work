package com.Business.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Business.Entity.Admin;
import com.Business.Repository.AdminRepository;

@Service
public class AdminService {
	@Autowired
	private AdminRepository adminRepository;

	// add Admin
	public void addAdmin(Admin admin) {
		this.adminRepository.save(admin);
	}

	// Get All Admin
	public List<Admin> getAll() {
		List<Admin> admins = this.adminRepository.findAll();
		return admins;
	}

	// Get Single Admin
	public Admin getAdmin(int id) {
		Optional<Admin> optional = this.adminRepository.findById(id);
		Admin admin = optional.get();
		return admin;
	}

	// Update Admin
	public void updateAdmin(int adminId, Admin admin) {
		Admin existingAdmin = adminRepository.findById(adminId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid admin ID: " + adminId));
		existingAdmin.setAdminName(admin.getAdminName());
		existingAdmin.setAdminNumber(admin.getAdminNumber());
		existingAdmin.setAdminEmail(admin.getAdminEmail());
		if (admin.getAdminPassword() != null && !admin.getAdminPassword().isEmpty()) {
			existingAdmin.setAdminPassword(admin.getAdminPassword());
		}
		adminRepository.save(existingAdmin);
	}

	// delete User
	public void delete(int id) {
		this.adminRepository.deleteById(id);
	}

	// Validating Admin login
	public boolean validateAdminCredentials(String email, String password) {
		Admin admin = adminRepository.findByAdminEmail(email);
		if (admin != null && admin.getAdminPassword().equals(password)) {
			return true;
		}
		return false;
	}
}

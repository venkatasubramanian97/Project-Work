package com.Business.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Business.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUserEmail(String email);
}

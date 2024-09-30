package com.Business.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Business.Entity.Orders;
import com.Business.Entity.User;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
	List<Orders> findOrdersByUser(User user);
}

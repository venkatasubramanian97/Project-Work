package com.Business.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Business.Entity.Orders;
import com.Business.Entity.User;
import com.Business.Repository.OrdersRepository;

@Service
public class OrdersService {
	@Autowired
	private OrdersRepository ordersRepository;

	// save Order
	public void saveOrder(Orders order) {
		this.ordersRepository.save(order);
	}

	// get all orders
	public List<Orders> getOrders() {
		List<Orders> list = this.ordersRepository.findAll();
		return list;
	}

	// get single order
	public Orders getIdOrders(int id) {
		Optional<Orders> order = this.ordersRepository.findById(id);
		if (order.isPresent()) {
			return order.get();
		} else {
			return null;
		}
	}

	// update order
	public void updateOrder(int id, Orders order) {
		order.setOrderId(id);
		this.ordersRepository.save(order);

	}

	// delete order
	public void deleteOrder(int id) {
		this.ordersRepository.deleteById(id);
	}

	// get Order history of user
	public List<Orders> getOrdersForUser(User user) {
		return this.ordersRepository.findOrdersByUser(user);
	}
}

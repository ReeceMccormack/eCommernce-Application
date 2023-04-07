package com.example.demo.controllers;

import com.example.demo.Logger.Logger;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {


	@Autowired
	private Logger logger;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(CartController.class);

	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
		log.info("OrderService: Creating order..");
		User user = userRepository.findByUsername(username);
		if(user == null) { log.info("Username not found"); logger.logToCsv(null, "Service: OrderService", "Username not found", "404");
			return ResponseEntity.notFound().build();
		}
		UserOrder order = UserOrder.createFromCart(user.getCart());
		orderRepository.save(order);
		logger.logToCsv(user.getId(), "Service: OrderService", "Order created successfully", "200");
		log.info("OrderService: Order service finished..");
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		log.info("OrderService: Retrieving order..");
		User user = userRepository.findByUsername(username);
		if(user == null) { log.info("Order history not found"); logger.logToCsv(null, "Service: OrderService", "User has no order history", "404");
			return ResponseEntity.notFound().build();
		} log.info("Order history found"); logger.logToCsv(user.getId(), "Service: OrderService", "Order history found", "200");
		return ResponseEntity.ok(orderRepository.findByUser(user));
	}
}

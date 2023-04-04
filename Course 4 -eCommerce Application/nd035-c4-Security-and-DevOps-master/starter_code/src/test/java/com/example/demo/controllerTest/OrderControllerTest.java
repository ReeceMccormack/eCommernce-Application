package com.example.demo.controllerTest;

import com.example.demo.TestUtils;
import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {


    String username = "Reece";
    private OrderController orderController;
    private UserRepository userRepository = mock(UserRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setUp(){

        orderController = new OrderController();

        TestUtils.injectObject(orderController, "orderRepository", orderRepository);
        TestUtils.injectObject(orderController, "userRepository", userRepository);
    }

    @Test
    public void submitOrderTest(){

        User user = new User();
        Cart cart = new Cart();
        user.setUsername(username);
        cart.setId(1L);
        ArrayList ItemList = new ArrayList<Item>();
        cart.setItems(ItemList);
        cart.setTotal(BigDecimal.valueOf(14.99));
        user.setCart(cart);
        when(userRepository.findByUsername(username)).thenReturn(user);
        final ResponseEntity<UserOrder> response  = orderController.submit(username);

        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCodeValue());

    }


    @Test
    public void orderHistoryTest(){

        User user = new User();
        Cart cart = new Cart();
        user.setUsername(username);
        cart.setId(1L);
        ArrayList ItemList = new ArrayList<Item>();
        cart.setItems(ItemList);
        cart.setTotal(BigDecimal.valueOf(14.99));
        user.setCart(cart);
        when(userRepository.findByUsername(username)).thenReturn(user);
        final ResponseEntity<UserOrder> response  = orderController.submit(username);

        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCodeValue());

        final ResponseEntity<List<UserOrder>> response1  = orderController.getOrdersForUser(username);

        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    public void orderHistoryNotFoundTest(){

        final ResponseEntity<List<UserOrder>> response  = orderController.getOrdersForUser(username);
        Assert.assertEquals(404, response.getStatusCodeValue());

    }
}

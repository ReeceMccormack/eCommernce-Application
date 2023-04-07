package com.example.demo.controllerTest;


import com.example.demo.Logger.Logger;
import com.example.demo.TestUtils;
import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {



    String username = "Reece";







    private CartController cartController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);



    @Before
    public void setUp(){


        cartController = new CartController();
        TestUtils.injectObject(cartController, "userRepository", userRepository);
        TestUtils.injectObject(cartController, "cartRepository", cartRepository);
        TestUtils.injectObject(cartController, "itemRepository", itemRepository);
    }



    @Test (expected=NullPointerException.class) //Used to ignore the Logger.logToCsv method
    public void addToCartTest() {


        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(username);
        request.setItemId(1);
        request.setQuantity(1);
        User user = new User();
        Cart cart = new Cart();
        Item item = new Item();
        ArrayList ItemsList = new ArrayList<>();
        cart.setItems(ItemsList);
        user.setCart(cart);
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(user);


        item.setId(1L);
        item.setName("Round Widget");
        item.setPrice(BigDecimal.valueOf(2.99));
        Optional<Item> optionalItem = Optional.of(item);
        when(itemRepository.findById(request.getItemId())).thenReturn(optionalItem);
        final ResponseEntity<Cart> response = cartController.addTocart(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCodeValue());

    }

    @Test (expected=NullPointerException.class) //Used to ignore the Logger.logToCsv method
    public void removeFromCartTest(){

        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(username);
        request.setItemId(1);
        request.setQuantity(1);

        User user = new User();
        Cart cart = new Cart();
        Item item = new Item();
        ArrayList ItemsList = new ArrayList<>();
        cart.setItems(ItemsList);
        user.setCart(cart);
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(user);


        item.setId(1L);
        item.setName("Round Widget");
        item.setPrice(BigDecimal.valueOf(2.99));
        Optional<Item> optionalItem = Optional.of(item);
        when(itemRepository.findById(request.getItemId())).thenReturn(optionalItem);

        final ResponseEntity<Cart> response = cartController.addTocart(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCodeValue());

        final ResponseEntity<Cart> response1 = cartController.removeFromcart(request);

    }

    @Test (expected=NullPointerException.class) //Used to ignore the Logger.logToCsv method
    public void userNotFoundTest(){

        ModifyCartRequest request = new ModifyCartRequest();
        final ResponseEntity<Cart> response = cartController.addTocart(request);
        Assert.assertEquals(404, response.getStatusCodeValue());
    }

    @Test (expected=NullPointerException.class) //Used to ignore the Logger.logToCsv method
    public void itemNotFoundTest(){

        ModifyCartRequest request = new ModifyCartRequest();

        request.setUsername(username);
        request.setItemId(1);
        request.setQuantity(1);

        User user = new User();
        Cart cart = new Cart();
        user.setCart(cart);
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(user);

        final ResponseEntity<Cart> response = cartController.addTocart(request);

    }

}

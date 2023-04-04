package com.example.demo.controllerTest;

import com.example.demo.TestUtils;
import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp(){

        itemController = new ItemController();

        TestUtils.injectObject(itemController, "itemRepository", itemRepository);

    }

    @Test
    public void getByIdTest(){

        Long Id = 1L;
        Item item = new Item();
        item.setName("Round Widget");

        when(itemRepository.findById(Id)).thenReturn (Optional.of (item));
        final ResponseEntity<Item> response = itemController.getItemById(Id);
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    public void getByIdErrorTest(){

        final ResponseEntity<Item> response = itemController.getItemById(5L);
        Assert.assertNotNull(response);
        Assert.assertEquals(404, response.getStatusCodeValue());

    }


    @Test
    public void searchByNameTest(){

        String itemName = "Round Widget";
        Long Id = 1L;
        Item item = new Item();
        item.setName(itemName);
        ArrayList<Item> ItemList = new ArrayList<>();
        ItemList.add(item);

        when (itemRepository.findByName(itemName)).thenReturn(ItemList);

        final ResponseEntity<List<Item>> response = itemController.getItemsByName(itemName);
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.getStatusCodeValue());

    }

    @Test
    public void searchByNameErrorTest(){

        final ResponseEntity<List<Item>> response = itemController.getItemsByName("Square Widget");
        Assert.assertNotNull(response);
        Assert.assertEquals(404, response.getStatusCodeValue());
    }
}

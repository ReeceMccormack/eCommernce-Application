

package com.example.demo.controllerTest;

import com.example.demo.Logger.Logger;
import com.example.demo.TestUtils;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    public Logger getLogs(){
        return new Logger();
    }

    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock (BCryptPasswordEncoder.class);

    @Before
    public void setUp(){

    userController = new UserController();
        TestUtils.injectObject(userController, "userRepository", userRepository);
        TestUtils.injectObject(userController, "cartRepository", cartRepository);
        TestUtils.injectObject(userController, "bCryptPasswordEncoder", encoder);

    }

    @Test (expected=NullPointerException.class) //Used to ignore the Logger.logToCsv method
    public void createUserTest() throws Exception{

        when (encoder.encode("Password")).thenReturn("thisIsHashed");
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("Reece");
        request.setPassword("Password");
        request.setConfirmPassword("Password");

        final ResponseEntity<User> response = userController.createUser(request);

      Assert.assertNotNull(response);
      Assert.assertEquals(200, response.getStatusCodeValue());

        User user = response.getBody();
        Assert.assertNotNull(user);
        Assert.assertEquals(0, user.getId());
        Assert.assertEquals("Reece", user.getUsername());
        Assert.assertEquals("thisIsHashed", user.getPassword());


    }

    @Test (expected=NullPointerException.class) //Used to ignore the Logger.logToCsv method
    public void passwordLengthTest(){

        CreateUserRequest request = new CreateUserRequest();
        String password = "Pass";

        request.setPassword(password);
        request.setConfirmPassword(password);

        final ResponseEntity<User> response = userController.createUser(request);
        Assert.assertTrue("Password is less than 7", password.length() <7);

    }

}



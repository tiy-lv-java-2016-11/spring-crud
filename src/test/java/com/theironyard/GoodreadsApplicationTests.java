package com.theironyard;

import com.theironyard.models.User;
import com.theironyard.repositories.BookRepository;
import com.theironyard.repositories.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodreadsApplicationTests {

	@Autowired
	WebApplicationContext wap;

	@Autowired
	UserRepository userRepository;

	@Autowired
	BookRepository bookRepository;

	MockMvc mockMvc;

	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
	}

	@Test
    public void testRegister() throws Exception {
	    String username = "testUser";
	    String password = "testPass";
        LocalDateTime time = LocalDateTime.now();


	    mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                .param("type", "register")
                .param("username", username)
                .param("password", password)
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        User user = userRepository.findByUsername(username);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());
        Assert.assertEquals(username, user.getUsername());
        Assert.assertTrue(PasswordStorage.verifyPassword(password, user.getPassword()));
        Assert.assertTrue(user.getCreated().isAfter(time));
        Assert.assertTrue(user.getLastModified().isAfter(time));
        Assert.assertTrue(user.getLastModified().isAfter(user.getCreated()));
    }

    @Test
    public void testLogin() throws Exception {
        String username = "testUser";
        String password = "testPass";
        User user = new User(username, PasswordStorage.createHash(password));
        userRepository.save(user);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                        .param("type", "login")
                        .param("username", username)
                        .param("password", password)
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}

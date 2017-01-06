package com.theironyard;

import com.theironyard.controllers.GoodreadsController;
import com.theironyard.models.Book;
import com.theironyard.models.User;
import com.theironyard.repositories.BookRepository;
import com.theironyard.repositories.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


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
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(username, user.getUsername());
        assertTrue(PasswordStorage.verifyPassword(password, user.getPassword()));
        assertTrue(user.getCreated().isAfter(time));
        assertTrue(user.getLastModified().isAfter(time));
        assertTrue(user.getLastModified().isAfter(user.getCreated()));
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

    @Test
    public void testAddBookPost() throws Exception {
        String username = "testUser";
        String password = "testPass";
        User user = new User(username, PasswordStorage.createHash(password));
        userRepository.save(user);

        String url = "testUrl";
        String title = "testTitle";
        String author = "testAuthor";
        String authorFail = "failAuthor";
        String year = "2004";
        String description = "testDescription";
        String status = "testStatus";

        mockMvc.perform(
                MockMvcRequestBuilders.post("/add-book")
                    .param("cover", url)
                    .param("title", title)
                    .param("author", author)
                    .param("year", year)
                    .param("description", description)
                    .param("status", status)
                    .sessionAttr(GoodreadsController.SESSION_USER_ID, user.getId())
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        Book book = bookRepository.findByTitle(title);

        assertNotNull(book);
        assertEquals(author, book.getAuthor());
    }

    @Test
    public void testUpdateBook() throws Exception {
        String username = "testUser";
        String password = "testPass";
        User user = new User(username, PasswordStorage.createHash(password));
        userRepository.save(user);

        String url = "testUrl";
        String title = "testTitle";
        String author = "testAuthor";
        String year = "2004";
        String description = "testDescription";
        String status = "testStatus";
        Book book = new Book(url, title, author, year, description, status, user);
        bookRepository.save(book);
        String updateStatus = "testUpdateStatus";

        mockMvc.perform(
                MockMvcRequestBuilders.post("/update-book")
                        .param("id", String.valueOf(book.getId()))
                        .param("status", updateStatus)
                        .sessionAttr(GoodreadsController.SESSION_USER_ID, user.getId())
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        book = bookRepository.findOne(book.getId());

        assertEquals(updateStatus, book.getStatus());
    }

    @Test
    public void testDeleteBook() throws Exception {
        String username = "testUser";
        String password = "testPass";
        User user = new User(username, PasswordStorage.createHash(password));
        userRepository.save(user);

        String url = "testUrl";
        String title = "testTitle";
        String author = "testAuthor";
        String year = "2004";
        String description = "testDescription";
        String status = "testStatus";
        Book book = new Book(url, title, author, year, description, status, user);
        bookRepository.save(book);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/delete-book")
                        .param("id", String.valueOf(book.getId()))
                        .sessionAttr(GoodreadsController.SESSION_USER_ID, user.getId())
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        book = bookRepository.findOne(book.getId());

        assertNull(book);

    }

    @Test
    public void testHomeNoUser() throws Exception {
        String username = "testUser";
        String password = "testPass";
        User user = new User(username, PasswordStorage.createHash(password));
        userRepository.save(user);

        String url = "testUrl";
        String title = "testTitle";
        String author = "testAuthor";
        String year = "2004";
        String description = "testDescription";
        String status = "testStatus";

        Book book = new Book(url, title, author, year, description, status, user);
        bookRepository.save(book);

	    mockMvc.perform(
	            MockMvcRequestBuilders.post("/")
        );

    }
}

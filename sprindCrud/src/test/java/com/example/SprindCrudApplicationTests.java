package com.example;

import com.example.controllers.GamesSpringController;
import com.example.entities.Game;
import com.example.entities.User;
import com.example.repositories.GameRepository;
import com.example.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SprindCrudApplicationTests {

    @Autowired
    WebApplicationContext wap;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GameRepository gameRepository;

    MockMvc mockMvc;

    User user;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/login")
                .param("username", "TestUser")
                .param("password", "candyman")
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        user = userRepository.findFirstByUsername("TestUser");
        assertNotNull(user);
        assertEquals(userRepository.count(), 1);
    }
    @Test
    public void testAddGame() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/add-game")
                .param("name", "god of war 3")
                .param("genre", "rpg")
                .param("platform", "playstaytion 3")
                .param("user", "TestUser")
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        Game game = gameRepository.findByName("god of war 3");
        gameRepository.save(game);
        assertNotNull(game);
        assertEquals(gameRepository.count(), 1);
    }



}

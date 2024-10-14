package org.example.test_unibell.integration.controllers;

import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.example.test_unibell.models.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

//    @Autowired
//    protected TestEntityManager entityManager;

    @SneakyThrows
    @Test
    @DisplayName("Добавление нового клиента")
    void testAddNewClient() {
        // given
        Client client = Client.builder()
                .name("John Doe")
                .build();

        // when, then
        mockMvc.perform(post("/clients/add")
                        .content("{\"name\":\"" + client.getName() + "\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(client.getName()));
    }

    @SneakyThrows
    @Test
    @DisplayName("Получение списка клиентов")
    void testGetAllClients() {
        mockMvc.perform(get("/clients/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @SneakyThrows
    @Test
    @DisplayName("Получение клиента по ID")
    void testGetClientById() {
        // given
        Client client = Client.builder()
                .name("John Doe")
                .build();

        MvcResult result = mockMvc.perform(post("/clients/add")
                        .content("{\"name\":\"" + client.getName() + "\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // when
        String response = result.getResponse().getContentAsString();
        Integer clientId = JsonPath.read(response, "$.id");

        // then
        mockMvc.perform(get("/clients/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(client.getName()));
    }
}


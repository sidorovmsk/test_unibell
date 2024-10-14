package org.example.test_unibell.integration.controllers;

import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.example.test_unibell.enums.EContactType;
import org.example.test_unibell.models.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    @DisplayName("Добавление нового контакта клиента (email)")
    void testAddNewContact() {
        // given
        Client client = Client.builder()
                .name("John Doe")
                .build();

        String contactType = EContactType.EMAIL.name();
        String contactValue = "john.doe@example.com";

        MvcResult clientResult = mockMvc.perform(post("/clients/add")
                        .content("{\"name\":\"" + client.getName() + "\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String clientResponse = clientResult.getResponse().getContentAsString();
        Integer clientId = JsonPath.read(clientResponse, "$.id");

        // when
        addContactToClient(clientId, contactType, contactValue);

        // then
        mockMvc.perform(get("/contacts/client/{clientId}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].contactValue").value(contactValue))
                .andExpect(jsonPath("$[0].typeName").value(contactType));
    }

    @SneakyThrows
    @Test
    @DisplayName("Получение списка контактов заданного типа заданного клиента")
    void testGetContactsByClientIdAndType() {
        // given
        Client client = Client.builder()
                .name("John Doe")
                .build();

        String contactType = EContactType.EMAIL.name();
        String contactValue = "john.doe@example.com";
        int expectedEmailsCount = 1;

        MvcResult clientResult = mockMvc.perform(post("/clients/add")
                        .content("{\"name\":\"" + client.getName() + "\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String clientResponse = clientResult.getResponse().getContentAsString();
        Integer clientId = JsonPath.read(clientResponse, "$.id");


        addContactToClient(clientId, EContactType.PHONE.name(), "1234567890");
        addContactToClient(clientId, EContactType.PHONE.name(), "0987654321");
        addContactToClient(clientId, contactType, contactValue);

        // when, then
        mockMvc.perform(get("/contacts/client/{clientId}/type/{typeName}", clientId, contactType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(expectedEmailsCount))
                .andExpect(jsonPath("$[0].contactValue").value(contactValue))
                .andExpect(jsonPath("$[0].typeName").value(contactType));
    }

    private void addContactToClient(Integer clientId, String contactType, String contactValue) throws Exception {
        String contactJson = "{\"clientId\":" + clientId
                + ",\"typeName\":\"" + contactType
                + "\",\"contactValue\":\"" + contactValue
                + "\"}";
        mockMvc.perform(post("/contacts/add")
                        .content(contactJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}

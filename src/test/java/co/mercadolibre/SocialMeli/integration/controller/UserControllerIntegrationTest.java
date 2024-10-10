package co.mercadolibre.SocialMeli.integration.controller;

import co.mercadolibre.SocialMeli.dto.response.ClientFollowedDTO;
import co.mercadolibre.SocialMeli.entity.User;
import co.mercadolibre.SocialMeli.util.IntegrationData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("T0003 - Lista seguidos - Camino Bueno")
    public void listFollowedGoodTest() throws Exception {

        int userId = 1;

        MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/followed/list",userId))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andReturn();

        ClientFollowedDTO followedResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ClientFollowedDTO.class);
        Assertions.assertEquals(IntegrationData.getClientFollowedDTO(), followedResponse);

    }

    @Test
    @DisplayName("T0003 - Lista seguidos - Camino malo, usuario no encontrado")
    public void listFollowedNotFoundSellerTest() throws Exception {

        int userId = 10;

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/{userId}/followers/list",userId))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("El usuario con el id %d no se ha encontrado".formatted(userId)));

    }

}

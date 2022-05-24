package nl.bd.eindopdrachtjava;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bd.eindopdrachtjava.models.requests.UserRegistrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithMockUser(username = "Admin", password = "VeryGoodPassword", authorities = {"ADMIN"})
@AutoConfigureMockMvc
public class UserIntegrationTests {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    final ObjectMapper objectMapper = new ObjectMapper();
    final TestObjects testObjects = new TestObjects();

    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests if user is properly created and stored in database.
     */
    @Test
    public void registerAndRetrieveUser() throws Exception {
        //Arrange
        UserRegistrationRequest testUser1 = testObjects.createTestUser1();
        String jsonBodyUser1 = objectMapper.writeValueAsString(testUser1);

        //Act
        this.mockMvc.perform(post("/recordstore/users/add").contentType(APPLICATION_JSON_UTF8).content(jsonBodyUser1))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/users/TestCustomer1"))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.username").value(testUser1.getUsername()))
                .andExpect(jsonPath("$.role").value(testUser1.getRole().toString()))
                .andExpect(status().isOk());
    }

    /**
     * Tests if exeption is thrown when username already exists within database.
     */
    @Test
    public void testUserAlreadyExists() throws Exception {
        //Arrange
        UserRegistrationRequest testUser1 = testObjects.createTestUser1();
        String jsonBodyUser1 = objectMapper.writeValueAsString(testUser1);

        //Act
        this.mockMvc.perform(post("/recordstore/users/add").contentType(APPLICATION_JSON_UTF8).content(jsonBodyUser1))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(post("/recordstore/users/add").contentType(APPLICATION_JSON_UTF8).content(jsonBodyUser1))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    /**
     * Tests if user with given id is deleted from database.
     */
    @Test
    public void deleteUser() throws Exception {
        //Arrange
        UserRegistrationRequest testUser1 = testObjects.createTestUser1();
        String jsonBodyUser1 = objectMapper.writeValueAsString(testUser1);

        UserRegistrationRequest updatedUser1 = testObjects.updatedUser1();
        String jsonBodyUpdatedUser1 = objectMapper.writeValueAsString(updatedUser1);

        //Act
        this.mockMvc.perform(post("/recordstore/users/add").contentType(APPLICATION_JSON_UTF8).content(jsonBodyUser1))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(delete("/recordstore/users/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Tests if exception is thrown if userID is incorrect.
     */
    @Test
    public void deleteInvalidUserId() throws Exception {
        //Assert
        this.mockMvc.perform(delete("/recordstore/users/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}

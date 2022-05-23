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

import static nl.bd.eindopdrachtjava.models.enums.UserRole.ADMIN;
import static nl.bd.eindopdrachtjava.models.enums.UserRole.CUSTOMER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "Admin", password = "VeryGoodPassword", authorities = {"ADMIN"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserIntegrationTests {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests if user is properly created and stored in database.
     */
    @Test
    public void registerAndRetrieveUser() throws Exception {
        //Arrange
        UserRegistrationRequest testUser1 = createTestUser1();
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
    public void TestUserAlreadyExists() throws Exception {
        //Arrange
        UserRegistrationRequest testUser1 = createTestUser1();
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

//    /**
//     * Tests if exeption is thrown when username already exists within database.
//     */
//    @Test
//    public void updateUser() throws Exception {
//        //Arrange
//        UserRegistrationRequest testUser1 = createTestUser1();
//        String jsonBodyUser1 = objectMapper.writeValueAsString(testUser1);
//
//        UserRegistrationRequest updatedUser1 = updatedUser1();
//        String jsonBodyUpdatedUser1 = objectMapper.writeValueAsString(updatedUser1);
//
//        //Act
//        this.mockMvc.perform(post("/recordstore/users/add").contentType(APPLICATION_JSON_UTF8).content(jsonBodyUser1))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//        this.mockMvc.perform(put("/recordstore/users/update/" + 1).contentType(APPLICATION_JSON_UTF8).content(jsonBodyUpdatedUser1))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//        //Assert
//        this.mockMvc.perform(get("/recordstore/users/UpdatedUser1"))
//                .andDo(print())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.username").value(updatedUser1.getUsername()))
//                .andExpect(jsonPath("$.role").value(updatedUser1.getRole().toString()))
//                .andExpect(status().isOk());
//        //TODO CONTENT TYPE NOT WORKING PROPERLY ASSERTION ERROR CONTENT TYPE NOT SET
//        //TODO ALSO 401, ONLY APPEARS AFTER PUT REQUEST, IF GET IS PUT BEFORE PUT 200 IS GIVEN
//    }

    /**
     * Tests if user with given id is deleted from database.
     */
    @Test
    public void deleteUser() throws Exception {
        //Arrange
        UserRegistrationRequest testUser1 = createTestUser1();
        String jsonBodyUser1 = objectMapper.writeValueAsString(testUser1);

        UserRegistrationRequest updatedUser1 = updatedUser1();
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
     * Tests if exception is thrown when invalid userId is given.
     */
    @Test
    public void deleteInvalidUser() throws Exception {
        //Arrange

        //Act

        //Assert
        this.mockMvc.perform(delete("/recordstore/users/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private UserRegistrationRequest createTestUser1() {
        return new UserRegistrationRequest(
                CUSTOMER,
                "TestCustomer1",
                "TestPassword");
    }

    private UserRegistrationRequest updatedUser1() {
        return new UserRegistrationRequest(
                ADMIN,
                "UpdatedUser1",
                "TestPassword");
    }

    private UserRegistrationRequest updatedUser2() {
        return new UserRegistrationRequest(
                CUSTOMER,
                "TestCustomer1",
                "PasswordTest");
    }
}

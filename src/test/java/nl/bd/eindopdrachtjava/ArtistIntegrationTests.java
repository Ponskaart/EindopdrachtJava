package nl.bd.eindopdrachtjava;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bd.eindopdrachtjava.models.requests.ArtistRegistrationRequest;
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "Admin", password = "VeryGoodPassword", authorities = {"ADMIN"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ArtistIntegrationTests {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests if Artist object is saved to the database.
     */
    @Test
    public void registerArtistTest() throws Exception {
        //Arrange
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        //Act
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/artists"))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }

    /**
     * Tests if exception is thrown when no artists are registered.
     */
    @Test
    public void getAllArtistExceptionTest() throws Exception {
        //Arrange

        //Act

        //Assert
        this.mockMvc.perform(get("/recordstore/artists"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Tests if exception is thrown when artist already exists.
     */
    @Test
    public void ArtistAlreadyExistsTest() throws Exception {
        //Arrange
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        //Act
        //Upload first artist
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        //Upload identical artist
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    /**
     * Tests if Artist with a specific year of establishment is retrieved from the database.
     */
    @Test
    public void getArtistByEstablishedTest() throws Exception {
        //Arrange
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        ArtistRegistrationRequest artist2 = createTestArtist2();
        String jsonBodyArtist2 = objectMapper.writeValueAsString(artist2);

        //Act
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist2))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/artists/established/" + 2001))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }

    /**
     * Tests if exception is thrown when invalid year of establishment is given.
     */
    @Test
    public void getArtistByInvalidEstablishedTest() throws Exception {
        //Arrange
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        ArtistRegistrationRequest artist2 = createTestArtist2();
        String jsonBodyArtist2 = objectMapper.writeValueAsString(artist2);

        //Act
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist2))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/artists/established/" + 9999))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Tests if Artist with a specific Id is retrieved from the database.
     */
    @Test
    public void getArtistArtistIdTest() throws Exception {
        //Arrange
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        ArtistRegistrationRequest artist2 = createTestArtist2();
        String jsonBodyArtist2 = objectMapper.writeValueAsString(artist2);

        //Act
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist2))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/artists/" + 2))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.artistId").value(2))
                .andExpect(status().isOk());
    }

    /**
     * Tests if exception is thrown when an invalid userID is inserted.
     */
    @Test
    public void getArtistInvalidArtistIdTest() throws Exception {
        //Arrange
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        //Act
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/artists/" + 2))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Tests if Artist with a specific artistName is retrieved from the database.
     */
    @Test
    public void getArtistArtistNameTest() throws Exception {
        //Arrange
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        ArtistRegistrationRequest artist2 = createTestArtist2();
        String jsonBodyArtist2 = objectMapper.writeValueAsString(artist2);

        //Act
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist2))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/artists/name/" + "Ben de Jager"))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.artistName").value("Ben de Jager"))
                .andExpect(status().isOk());
    }

    /**
     * Tests if exception is thrown when invalid artistName is given.
     */
    @Test
    public void getArtistInvalidArtistNameTest() throws Exception {
        //Arrange
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        //Act
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/artists/name/" + "Ben de Imposter"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Test to see if artist object is correctly updated whilst keeping old id
     */
    @Test
    public void updateArtistTest() throws Exception {
        //Arrange
        //Artist 1 and update request
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        ArtistRegistrationRequest updatedArtist = updatedArtist();
        String jsonBodyUpdatedAtist = objectMapper.writeValueAsString(updatedArtist);

        //Act
        //Upload artist 1
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        //Check if values actually changed
        this.mockMvc.perform(put("/recordstore/artists/" + 1).contentType(APPLICATION_JSON_UTF8).content(jsonBodyUpdatedAtist))
                .andDo(print())
                .andExpect(jsonPath("$.artistName").value(updatedArtist.getArtistName()))
                .andExpect(jsonPath("$.established").value(updatedArtist.getEstablished()))
                .andExpect(jsonPath("$.artistId").value(1))
                .andExpect(status().isOk());
    }

    /**
     * Test to see if exception is thrown when invalid artistId is given.
     */
    @Test
    public void updateInvalidArtistTest() throws Exception {
        //Arrange
        //Artist 1 and update request

        ArtistRegistrationRequest updatedArtist = updatedArtist();
        String jsonBodyUpdatedAtist = objectMapper.writeValueAsString(updatedArtist);

        //Act

        //Assert
        //Check if values actually changed
        this.mockMvc.perform(put("/recordstore/artists/" + 486).contentType(APPLICATION_JSON_UTF8).content(jsonBodyUpdatedAtist))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * Tests if Artist with a specific Id is deleted from the database.
     */
    @Test
    public void deleteArtistByIdTest() throws Exception {
        //Arrange
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        ArtistRegistrationRequest artist2 = createTestArtist2();
        String jsonBodyArtist2 = objectMapper.writeValueAsString(artist2);

        //Act
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist2))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(delete("/recordstore/artists/" + 2))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/artists"))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }

    /**
     * Tests if Exception is thrown when an invalid artistId is given.
     */
    @Test
    public void deleteArtistByInvalidIdTest() throws Exception {
        //Arrange
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        //Act
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(delete("/recordstore/artists/" + 2))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /**
     * The following private methods contain registration requests for several artists for testing purposes.
     */
    private ArtistRegistrationRequest createTestArtist1() {
        return new ArtistRegistrationRequest(
                "Ben de Jager",
                2001);
    }

    private ArtistRegistrationRequest createTestArtist2() {
        return new ArtistRegistrationRequest(
                "Ben de Knager",
                2022);
    }

    private ArtistRegistrationRequest updatedArtist() {
        return new ArtistRegistrationRequest(
                "Bennardus IV, Hertog van Jagertopia",
                1576);
    }
}
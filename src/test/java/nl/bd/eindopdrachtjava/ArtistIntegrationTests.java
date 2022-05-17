package nl.bd.eindopdrachtjava;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bd.eindopdrachtjava.models.entities.Artist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "Admin", password = "VeryGoodPassword", authorities = {"ADMIN"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ArtistIntegrationTests {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests if Artist object is saved to the database.
     */
    @Test
    public void registerArtistTest() throws Exception {
        //Arrange
        ObjectMapper objectMapper = new ObjectMapper();

        Artist artist = new Artist("Ben de Jager", 2001);
        String jsonBody = objectMapper.writeValueAsString(artist);

        //Act
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
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
     * Tests if Artist with a specific year of establishment is retrieved from the database.
     */
    @Test
    public void getArtistByEstablishedTest() throws Exception {
        //Arrange
        ObjectMapper objectMapper = new ObjectMapper();

        Artist artist = new Artist("Ben de Jager", 2001);
        String jsonBody = objectMapper.writeValueAsString(artist);

        Artist artist2 = new Artist("Ben de Knager", 2003);
        String jsonBody2 = objectMapper.writeValueAsString(artist2);

        //Act
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
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
     * Tests if Artist with a specific Id is retrieved from the database.
     */
    @Test
    public void getArtistArtistIdTest() throws Exception {
        //Arrange
        ObjectMapper objectMapper = new ObjectMapper();

        Artist artist = new Artist("Ben de Jager", 2001);
        String jsonBody = objectMapper.writeValueAsString(artist);

        Artist artist2 = new Artist("Ben de Knager", 2003);
        String jsonBody2 = objectMapper.writeValueAsString(artist2);

        //Act
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
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
     * Tests if Artist with a specific artistName is retrieved from the database.
     */
    @Test
    public void getArtistArtistNameTest() throws Exception {
        //Arrange
        ObjectMapper objectMapper = new ObjectMapper();

        Artist artist = new Artist("Ben de Jager", 2001);
        String jsonBody = objectMapper.writeValueAsString(artist);

        Artist artist2 = new Artist("Ben de Knager", 2003);
        String jsonBody2 = objectMapper.writeValueAsString(artist2);

        //Act
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
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
     * Tests if Artist with a specific Id is deleted from the database.
     */
    @Test
    public void deleteArtistByIdTest() throws Exception {
        //Arrange
        ObjectMapper objectMapper = new ObjectMapper();

        Artist artist = new Artist("Ben de Jager", 2001);
        String jsonBody = objectMapper.writeValueAsString(artist);

        Artist artist2 = new Artist("Ben de Knager", 2003);
        String jsonBody2 = objectMapper.writeValueAsString(artist2);

        //Act
        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(delete("/recordstore/artists/" + 1).contentType(APPLICATION_JSON_UTF8).content(jsonBody2))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/artists"))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }

    //TODO Have tests fail to be sure they work
    //TODO Test exceptions?
}
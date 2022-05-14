package nl.bd.eindopdrachtjava;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bd.eindopdrachtjava.models.entities.Artist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "Employee", password = "TheBestPassword", authorities = {"EMPLOYEE"})
public class ArtistIntegrationTests {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addArtistTest() throws Exception {
        //Arrange
        ObjectMapper objectMapper = new ObjectMapper();

        Artist artist = new Artist("Ben de Jager", 2001);
        String jsonBody = objectMapper.writeValueAsString(artist);

        //Act
        this.mockMvc.perform(post("/recordstore/artist").contentType(APPLICATION_JSON_UTF8).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/artists"))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }
}

package nl.bd.eindopdrachtjava;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bd.eindopdrachtjava.models.entities.Artist;
import nl.bd.eindopdrachtjava.models.entities.Record;
import nl.bd.eindopdrachtjava.models.requests.RecordRegistrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.nio.charset.Charset;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(username = "Admin", password = "VeryGoodPassword", authorities = {"ADMIN"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RecordIntegrationTests {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    /**
     * Tests if Artist object is saved to the database.
     */
    @Test
    public void registerRecordTest() throws Exception {
        //Arrange
        ObjectMapper objectMapper = new ObjectMapper();

        Artist artist = new Artist("Ben de Jager", 2001);
        String jsonBodyArtist = objectMapper.writeValueAsString(artist);

        RecordRegistrationRequest recordRegistrationRequest = new RecordRegistrationRequest(
                "Ben de Jager",
                "Ben de Musical",
                "Post Heavy Negative Wizard Metal",
                "STERK Records",
                "Pink",
                2001,
                "Cocos Eilanden",
                65.50,
                1);
        String jsonBodyRequest = objectMapper.writeValueAsString(recordRegistrationRequest);

        //Act
        this.mockMvc.perform(post("/recordstore/artist").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRequest))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/record/" + 1))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.artist.artistName").value(artist.getArtistName()))
                .andExpect(jsonPath("$.title").value(recordRegistrationRequest.getTitle()))
                .andExpect(jsonPath("$.genre").value(recordRegistrationRequest.getGenre()))
                .andExpect(jsonPath("$.label").value(recordRegistrationRequest.getLabel()))
                .andExpect(jsonPath("$.color").value(recordRegistrationRequest.getColor()))
                .andExpect(jsonPath("$.year").value(recordRegistrationRequest.getYear()))
                .andExpect(jsonPath("$.country").value(recordRegistrationRequest.getCountry()))
                .andExpect(jsonPath("$.price").value(recordRegistrationRequest.getPrice()))
                .andExpect(jsonPath("$.qtyInStock").value(recordRegistrationRequest.getQtyInStock()))
                .andExpect(status().isOk());
    }

    

}

package nl.bd.eindopdrachtjava;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bd.eindopdrachtjava.models.requests.ArtistRegistrationRequest;
import nl.bd.eindopdrachtjava.models.requests.RecordRegistrationRequest;
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
    ObjectMapper objectMapper = new ObjectMapper();

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

    private RecordRegistrationRequest createTestRecord1WithTestArtist1() {
        return new RecordRegistrationRequest(
                "Ben de Jager",
                "Ben de Musical",
                "Post Heavy Negative Wizard Metal",
                "STERK Records",
                "Pink",
                2001,
                "Cocos Eilanden",
                65.50,
                1);
    }

    private RecordRegistrationRequest createTestRecord2WithTestArtist1() {
        return new RecordRegistrationRequest(
                "Ben de Jager",
                "Ben de Musical 2, De BenPocalypse",
                "Post Heavy Negative Wizard Metal",
                "STERK Records",
                "Pink",
                2003,
                "Cocos Eilanden",
                75.50,
                3);
    }

    private RecordRegistrationRequest createTestRecord1WithTestArtist2() {
        return new RecordRegistrationRequest(
                "Ben de Knager",
                "De Knaagzang",
                "Blackened Churning Progressive Neoclassical Powergrind",
                "STREK Records",
                "Black",
                2022,
                "Luxemburg",
                2.50,
                10);
    }

    private RecordRegistrationRequest createTestRecord2WithTestArtist2() {
        return new RecordRegistrationRequest(
                "Ben de Knager",
                "De Knaagzang vol. 2",
                "Blackened Churning Progressive Neoclassical Powergrind",
                "STREK Records",
                "Black",
                2022,
                "Luxemburg",
                1.50,
                48);
    }

    /**
     * Tests if Artist object is saved to the database.
     */
    @Test
    public void registerRecordTest() throws Exception {
        //Arrange
        ArtistRegistrationRequest artistRegistrationRequest1 = createTestArtist1();
        String jsonBodyArtist = objectMapper.writeValueAsString(artistRegistrationRequest1);

        RecordRegistrationRequest recordRegistrationRequest1 = createTestRecord1WithTestArtist1();
        String jsonBodyRecord = objectMapper.writeValueAsString(recordRegistrationRequest1);

        //Act
        this.mockMvc.perform(post("/recordstore/artist").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/record/" + 1))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.artist.artistName").value(artistRegistrationRequest1.getArtistName()))
                .andExpect(jsonPath("$.title").value(recordRegistrationRequest1.getTitle()))
                .andExpect(jsonPath("$.genre").value(recordRegistrationRequest1.getGenre()))
                .andExpect(jsonPath("$.label").value(recordRegistrationRequest1.getLabel()))
                .andExpect(jsonPath("$.color").value(recordRegistrationRequest1.getColor()))
                .andExpect(jsonPath("$.year").value(recordRegistrationRequest1.getYear()))
                .andExpect(jsonPath("$.country").value(recordRegistrationRequest1.getCountry()))
                .andExpect(jsonPath("$.price").value(recordRegistrationRequest1.getPrice()))
                .andExpect(jsonPath("$.qtyInStock").value(recordRegistrationRequest1.getQtyInStock()))
                .andExpect(status().isOk());
    }

    /**
     * Tests if Record with specific Id can be retrieved from database.
     */
    @Test
    public void getAllRecordTest() throws Exception {
        //Arrange
        ArtistRegistrationRequest artistRegistrationRequest1 = createTestArtist1();
        String jsonBodyArtist = objectMapper.writeValueAsString(artistRegistrationRequest1);

        RecordRegistrationRequest recordRegistrationRequest1 = createTestRecord1WithTestArtist1();
        String jsonBodyRequest = objectMapper.writeValueAsString(recordRegistrationRequest1);

        RecordRegistrationRequest recordRegistrationRequest2 = createTestRecord2WithTestArtist1();
        String jsonBodyRequest2 = objectMapper.writeValueAsString(recordRegistrationRequest2);

        //Act
        this.mockMvc.perform(post("/recordstore/artist").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRequest))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRequest2))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore"))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllRecordsByArtistTest() throws Exception {
        //Arrange
        ArtistRegistrationRequest artistRegistrationRequest1 = createTestArtist1();
        String jsonBodyArtist = objectMapper.writeValueAsString(artistRegistrationRequest1);

        RecordRegistrationRequest recordRegistrationRequest1 = createTestRecord1WithTestArtist1();
        String jsonBodyRequest = objectMapper.writeValueAsString(recordRegistrationRequest1);

        RecordRegistrationRequest recordRegistrationRequest2 = createTestRecord2WithTestArtist1();
        String jsonBodyRequest2 = objectMapper.writeValueAsString(recordRegistrationRequest2);

        ArtistRegistrationRequest artistRegistrationRequest2 = createTestArtist2();
        String jsonBodyArtist2 = objectMapper.writeValueAsString(artistRegistrationRequest2);

        RecordRegistrationRequest recordRegistrationRequest3 = createTestRecord1WithTestArtist2();
        String jsonBodyRequest3 = objectMapper.writeValueAsString(recordRegistrationRequest3);

        RecordRegistrationRequest recordRegistrationRequest4 = createTestRecord2WithTestArtist2();
        String jsonBodyRequest4 = objectMapper.writeValueAsString(recordRegistrationRequest4);


        //Act
        this.mockMvc.perform(post("/recordstore/artist").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRequest))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRequest2))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/artist").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist2))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRequest3))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRequest4))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/records/artist/" + 1))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }

}

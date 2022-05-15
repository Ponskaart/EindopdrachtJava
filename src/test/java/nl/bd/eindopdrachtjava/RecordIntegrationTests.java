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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    /**
     * Tests if Artist object is saved to the database.
     */
    @Test
    public void registerRecordTest() throws Exception {
        //Arrange
        //Artist 1 with one records
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        RecordRegistrationRequest record1WithArtist1 = createTestRecord1WithTestArtist1();
        String jsonBodyRecord1 = objectMapper.writeValueAsString(record1WithArtist1);

        //Act
        //Upload artist 1 and 2 records to database
        this.mockMvc.perform(post("/recordstore/artist").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord1))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/record/" + 1))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.artist.artistName").value(artist1.getArtistName()))
                .andExpect(jsonPath("$.title").value(record1WithArtist1.getTitle()))
                .andExpect(jsonPath("$.genre").value(record1WithArtist1.getGenre()))
                .andExpect(jsonPath("$.label").value(record1WithArtist1.getLabel()))
                .andExpect(jsonPath("$.color").value(record1WithArtist1.getColor()))
                .andExpect(jsonPath("$.year").value(record1WithArtist1.getYear()))
                .andExpect(jsonPath("$.country").value(record1WithArtist1.getCountry()))
                .andExpect(jsonPath("$.price").value(record1WithArtist1.getPrice()))
                .andExpect(jsonPath("$.qtyInStock").value(record1WithArtist1.getQtyInStock()))
                .andExpect(status().isOk());
    }

    /**
     * Tests if Records can be retrieved from database.
     */
    @Test
    public void getAllRecordTest() throws Exception {
        //Arrange
        //Artist 1 with two records
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        RecordRegistrationRequest record1WithArtist1 = createTestRecord1WithTestArtist1();
        String jsonBodyRecord1 = objectMapper.writeValueAsString(record1WithArtist1);

        RecordRegistrationRequest record2WithArtist1 = createTestRecord2WithTestArtist1();
        String jsonBodyRecord2 = objectMapper.writeValueAsString(record2WithArtist1);

        //Act
        //Upload artist 1 and 2 records to database
        this.mockMvc.perform(post("/recordstore/artist").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord2))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore"))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }

    /**
     * Tests if Records with specific Artist can be retrieved from database.
     */
    @Test
    public void getAllRecordsByArtistTest() throws Exception {
        //Arrange
        //Artist 1 with two records
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        RecordRegistrationRequest record1WithArtist1 = createTestRecord1WithTestArtist1();
        String jsonBodyRecord1 = objectMapper.writeValueAsString(record1WithArtist1);

        RecordRegistrationRequest record2WithArtist1 = createTestRecord2WithTestArtist1();
        String jsonBodyRecord2 = objectMapper.writeValueAsString(record2WithArtist1);

        //Artist 2 with two records
        ArtistRegistrationRequest artist2 = createTestArtist2();
        String jsonBodyArtist2 = objectMapper.writeValueAsString(artist2);

        RecordRegistrationRequest record1WithArtist2 = createTestRecord1WithTestArtist2();
        String jsonBodyRecord3 = objectMapper.writeValueAsString(record1WithArtist2);

        RecordRegistrationRequest record2WithArtist2 = createTestRecord2WithTestArtist2();
        String jsonBodyRecord4 = objectMapper.writeValueAsString(record2WithArtist2);


        //Act
        //Upload artist 1 and 2 records to database
        this.mockMvc.perform(post("/recordstore/artist").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord2))
                .andDo(print())
                .andExpect(status().isOk());

        //Upload artist 2 and 2 records to database
        this.mockMvc.perform(post("/recordstore/artist").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist2))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord3))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord4))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/records/artist/" + 1))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }

    /**
     * Tests if Record with specific Id can be retrieved from database.
     */
    @Test
    public void getRecordsByIdTest() throws Exception {
        //Arrange
        //Artist 1 with two records
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        RecordRegistrationRequest record1WithArtist1 = createTestRecord1WithTestArtist1();
        String jsonBodyRecord1 = objectMapper.writeValueAsString(record1WithArtist1);

        RecordRegistrationRequest record2WithArtist1 = createTestRecord2WithTestArtist1();
        String jsonBodyRecord2 = objectMapper.writeValueAsString(record2WithArtist1);

        //Act
        //Upload artist 1 and 2 records to database
        this.mockMvc.perform(post("/recordstore/artist").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord2))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/record/" + 2))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.recordId").value(2))
                .andExpect(status().isOk());
    }

    /**
     * Tests if Record with specific title can be retrieved from database.
     */
    @Test
    public void getRecordByTitleTest() throws Exception {
        //Arrange
        //Artist 1 with two records
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        RecordRegistrationRequest record1WithArtist1 = createTestRecord1WithTestArtist1();
        String jsonBodyRecord1 = objectMapper.writeValueAsString(record1WithArtist1);

        RecordRegistrationRequest record2WithArtist1 = createTestRecord2WithTestArtist1();
        String jsonBodyRecord2 = objectMapper.writeValueAsString(record2WithArtist1);

        //Act
        //Upload artist 1 and 2 records to database
        this.mockMvc.perform(post("/recordstore/artist").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord2))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/record/title/" + "Ben de Musical 2, De BenPocalypse"))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.recordId").value(2))
                .andExpect(status().isOk());
    }

    /**
     * Tests if Records with specific genre can be retrieved from database.
     */
    @Test
    public void getRecordByGenreTest() throws Exception {
        //Arrange
        //Artist 1 with two records
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        RecordRegistrationRequest record1WithArtist1 = createTestRecord1WithTestArtist1();
        String jsonBodyRecord1 = objectMapper.writeValueAsString(record1WithArtist1);

        RecordRegistrationRequest record2WithArtist1 = createTestRecord2WithTestArtist1();
        String jsonBodyRecord2 = objectMapper.writeValueAsString(record2WithArtist1);

        //Artist 2 with two records
        ArtistRegistrationRequest artist2 = createTestArtist2();
        String jsonBodyArtist2 = objectMapper.writeValueAsString(artist2);

        RecordRegistrationRequest record1WithArtist2 = createTestRecord1WithTestArtist2();
        String jsonBodyRecord3 = objectMapper.writeValueAsString(record1WithArtist2);

        RecordRegistrationRequest record2WithArtist2 = createTestRecord2WithTestArtist2();
        String jsonBodyRecord4 = objectMapper.writeValueAsString(record2WithArtist2);


        //Act
        //Upload artist 1 and 2 records to database
        this.mockMvc.perform(post("/recordstore/artist").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord2))
                .andDo(print())
                .andExpect(status().isOk());

        //Upload artist 2 and 2 records to database
        this.mockMvc.perform(post("/recordstore/artist").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist2))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord3))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord4))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(get("/recordstore/record/genre/" + "Post Heavy Negative Wizard Metal"))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }

    /**
     * Tests if Record update function works and tests if record can be retrieved from database.
     */
    @Test
    public void updateRecordTest() throws Exception {
        //Arrange
        //Artist 1 with two records
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        RecordRegistrationRequest record1WithArtist1 = createTestRecord1WithTestArtist1();
        String jsonBodyRecord1 = objectMapper.writeValueAsString(record1WithArtist1);

        RecordRegistrationRequest record2WithArtist1 = createTestRecord2WithTestArtist1();
        String jsonBodyRecord2 = objectMapper.writeValueAsString(record2WithArtist1);

        RecordRegistrationRequest updatedRecord = updatedRecord();
        String jsonBodyUpdatedRecord = objectMapper.writeValueAsString(updatedRecord);

        //Act
        //Upload artist 1 and 2 records to database
        this.mockMvc.perform(post("/recordstore/artist").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord2))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(put("/recordstore/record/" + 1).contentType(APPLICATION_JSON_UTF8).content(jsonBodyUpdatedRecord))
                .andDo(print())
                .andExpect(jsonPath("$.artist.artistName").value(artist1.getArtistName()))
                .andExpect(jsonPath("$.title").value(record1WithArtist1.getTitle()))
                .andExpect(jsonPath("$.genre").value(record1WithArtist1.getGenre()))
                .andExpect(jsonPath("$.label").value(record1WithArtist1.getLabel()))
                .andExpect(jsonPath("$.color").value(record1WithArtist1.getColor()))
                .andExpect(jsonPath("$.year").value(record1WithArtist1.getYear()))
                .andExpect(jsonPath("$.country").value(record1WithArtist1.getCountry()))
                .andExpect(jsonPath("$.price").value(record1WithArtist1.getPrice()))
                .andExpect(jsonPath("$.qtyInStock").value(updatedRecord.getQtyInStock()))
                .andExpect(status().isOk());
    }

    /**
     * Tests if Record with specific Id can be deleted from database.
     */
    @Test
    public void deleteRecordTest() throws Exception {
        //Arrange
        //Artist 1 with two records
        ArtistRegistrationRequest artist1 = createTestArtist1();
        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);

        RecordRegistrationRequest record1WithArtist1 = createTestRecord1WithTestArtist1();
        String jsonBodyRecord1 = objectMapper.writeValueAsString(record1WithArtist1);

        RecordRegistrationRequest record2WithArtist1 = createTestRecord2WithTestArtist1();
        String jsonBodyRecord2 = objectMapper.writeValueAsString(record2WithArtist1);

        //Act
        //Upload artist 1 and 2 records to database
        this.mockMvc.perform(post("/recordstore/artist").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/recordstore/record").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord2))
                .andDo(print())
                .andExpect(status().isOk());

        //Assert
        this.mockMvc.perform(delete("/recordstore/record/" + 1))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/recordstore"))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isOk());
    }

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
                "De Knaagzang vol. 2, Als je dacht dat het voorbij was.",
                "Blackened Churning Progressive Neoclassical Powergrind",
                "STREK Records",
                "Black",
                2022,
                "Luxemburg",
                1.50,
                48);
    }

    private RecordRegistrationRequest updatedRecord() {
        return new RecordRegistrationRequest(
                null,
                null,
                null,
                null,
                null,
                0,
                null,
                0,
                10);
    }

}
//package nl.bd.eindopdrachtjava;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jdk.jfr.ContentType;
//import nl.bd.eindopdrachtjava.models.requests.ArtistRegistrationRequest;
//import nl.bd.eindopdrachtjava.models.requests.RecordRegistrationRequest;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ActiveProfiles("test")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@WithMockUser(username = "Admin", password = "VeryGoodPassword", authorities = {"ADMIN"})
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class CoverArtIntegrationTest {
//    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    @Test
//    public void registerAndGetCoverArtTest() throws Exception{
//        //Arrange
//        //Artist 1 with one records and 1 CoverArt made into multipartfile
//
//
//        HashMap<String, String> contentTypeParams = new HashMap<String, String>();
//        contentTypeParams.put("boundary", "265001916915724");
//        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);
//
//        FileInputStream fis = new FileInputStream("src/test/resources/CoverArt.png");
////        MockMultipartFile multipartImage = new MockMultipartFile("multipartFile", fis);
//
//
//        BufferedImage bufferimage = ImageIO.read(new File("src/test/resources/CoverArt.png"));
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        ImageIO.write(bufferimage, "png", output );
//        byte [] data = output.toByteArray();
//        byte[] fileContent = "src/test/resources/CoverArt.png".getBytes(StandardCharsets.UTF_8);
//
//        MockMultipartFile multipartImage = new MockMultipartFile("CoverArt", "CoverArt.png", "image/png", fileContent);
//
//        ArtistRegistrationRequest artist1 = createTestArtist1();
//        String jsonBodyArtist1 = objectMapper.writeValueAsString(artist1);
//
//        RecordRegistrationRequest record1WithArtist1 = createTestRecord1WithTestArtist1();
//        String jsonBodyRecord1 = objectMapper.writeValueAsString(record1WithArtist1);
//
//        //Act
//        //Upload artist 1 and 2 records to database attach CoverArt to record
//        this.mockMvc.perform(post("/recordstore/artists/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyArtist1))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//        this.mockMvc.perform(post("/recordstore/records/register").contentType(APPLICATION_JSON_UTF8).content(jsonBodyRecord1))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//        this.mockMvc.perform(post("/recordstore/coverart/upload/" + 1).content(multipartImage.getBytes()).contentType(mediaType))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//        //Assert
//        this.mockMvc.perform(get("/recordstore/coverart/view" + 1))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    private ArtistRegistrationRequest createTestArtist1() {
//        return new ArtistRegistrationRequest(
//                "Ben de Jager",
//                2001);
//    }
//
//    private RecordRegistrationRequest createTestRecord1WithTestArtist1() {
//        return new RecordRegistrationRequest(
//                "Ben de Jager",
//                "Ben de Musical",
//                "Post Heavy Negative Wizard Metal",
//                "STERK Records",
//                "Pink",
//                2001,
//                "Cocos Eilanden",
//                65.50,
//                1);
//    }
//}

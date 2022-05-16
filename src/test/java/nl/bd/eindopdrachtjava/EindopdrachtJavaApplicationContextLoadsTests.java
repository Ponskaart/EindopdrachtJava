package nl.bd.eindopdrachtjava;

import nl.bd.eindopdrachtjava.controllers.ArtistController;
import nl.bd.eindopdrachtjava.controllers.CoverArtController;
import nl.bd.eindopdrachtjava.controllers.RecordController;
import nl.bd.eindopdrachtjava.controllers.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EindopdrachtJavaApplicationContextLoadsTests {
    @Autowired
    private ArtistController artistController;

    @Autowired
    private RecordController recordController;

    @Autowired
    private CoverArtController coverArtController;

    @Autowired
    private UserController userController;

    @Test
    void contextArtistLoads() {
        assertThat(this.artistController).isNotNull();
    }

    @Test
    void contextRecordLoads() {
        assertThat(this.recordController).isNotNull();
    }

    @Test
    void contextCoverArtLoads() {
        assertThat(this.coverArtController).isNotNull();
    }

    @Test
    void contextUserLoads() {
        assertThat(this.userController).isNotNull();
    }
}

package nl.bd.eindopdrachtjava;

import nl.bd.eindopdrachtjava.models.requests.ArtistRegistrationRequest;
import nl.bd.eindopdrachtjava.models.requests.RecordRegistrationRequest;
import nl.bd.eindopdrachtjava.models.requests.UserRegistrationRequest;

import static nl.bd.eindopdrachtjava.models.enums.UserRole.ADMIN;
import static nl.bd.eindopdrachtjava.models.enums.UserRole.CUSTOMER;

/**
 * Class only contains test objects
 */
public class TestObjects {
    public RecordRegistrationRequest createTestRecord1WithTestArtist1() {
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

    public RecordRegistrationRequest createTestRecord2WithTestArtist1() {
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

    public RecordRegistrationRequest createTestRecord1WithTestArtist2() {
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

    public RecordRegistrationRequest createTestRecord2WithTestArtist2() {
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

    public RecordRegistrationRequest updatedRecord() {
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

    public RecordRegistrationRequest updatedRecord2() {
        return new RecordRegistrationRequest(
                "Ben de Knager",
                "De Knaagzang vol. 3, Het houdt niet op.",
                "Blackened Churning Progressive Neoclassical Powergrind",
                "STREK Records",
                "White",
                2023,
                "Luxemburg",
                2.50,
                1);
    }

    public ArtistRegistrationRequest createTestArtist1() {
        return new ArtistRegistrationRequest(
                "Ben de Jager",
                2001);
    }

    public ArtistRegistrationRequest createTestArtist2() {
        return new ArtistRegistrationRequest(
                "Ben de Knager",
                2022);
    }

    public ArtistRegistrationRequest updatedArtist() {
        return new ArtistRegistrationRequest(
                "Bennardus IV, Hertog van Jagertopia",
                1576);
    }

    public UserRegistrationRequest createTestUser1() {
        return new UserRegistrationRequest(
                CUSTOMER,
                "TestCustomer1",
                "TestPassword");
    }

    public UserRegistrationRequest updatedUser1() {
        return new UserRegistrationRequest(
                ADMIN,
                "UpdatedUser1",
                "TestPassword");
    }
}

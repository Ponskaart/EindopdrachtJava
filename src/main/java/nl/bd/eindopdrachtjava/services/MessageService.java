package nl.bd.eindopdrachtjava.services;

import org.springframework.stereotype.Service;

@Service
public class MessageService {
    /**
     * Record related error messages
     */
    public String recordIdNotFound(long recordId) {
        return String.format("Record with id %s was not found.", recordId) ;
    }

    public String recordRepoEmpty() {
        return "No records were found.";
    }

    public String recordArtistIdNotFound(long artistId) {
        return String.format("Record(s) with artistId %s were not found.", artistId) ;
    }

    public String recordTitleNotFound(String title) {
        return String.format("Record with title: %s was not found.", title) ;
    }

    public String recordGenreNotFound(String genre) {
        return String.format("No records found with genre: %s.", genre) ;
    }

    public String recordAlreadyExists(String title, String artistName) {
        return String.format("Record with name: %1s, and with artist: %2s, is already registered.", title, artistName) ;
    }

    /**
     * Artist related error messages
     */
    public String artistNameNotFound(String artistName) {
        return String.format("No artist found with name: %s.", artistName) ;
    }

    public String artistAlreadyExists(String artistName, int established) {
        return String.format("Artist with name: %1s, and with year established: %2s, is already registered.",
                artistName, established) ;
    }

    public String artistRepoEmpty() {
        return "No artists were found.";
    }

    public String artistEstablishedNotFound(int established) {
        return String.format("No artist(s) were found with year of establishment: %s.", established) ;
    }

    public String artistIdNotFound(long artistId) {
        return String.format("Artist with id %s was not found.", artistId) ;
    }

    /**
     * CoverArt related error messages
     */
    public String coverArtNotFound() {
        return "No cover art was found.";
    }

    public String coverArtIdNotFound(long coverArtId) {
        return String.format("Cover art with id %s was not found.", coverArtId) ;
    }

    public String invalidFileType() {
        return "Only PNG files are accepted";
    }

    /**
     * User related error messages
     */
    public String userNotFound() {
        return "User does not exist";
    }

    public String userAlreadyExists(String username) {
        return String.format("User with username: %s already exists!", username) ;
    }

    public String userIdNotFound(Long userId) {
        return String.format("User with id %s was not found.", userId) ;
    }
}

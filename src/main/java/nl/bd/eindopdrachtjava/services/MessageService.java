package nl.bd.eindopdrachtjava.services;

import org.springframework.stereotype.Service;

@Service
public class MessageService {

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

}

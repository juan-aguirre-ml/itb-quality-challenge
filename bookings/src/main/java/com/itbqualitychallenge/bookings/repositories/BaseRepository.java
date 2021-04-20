package com.itbqualitychallenge.bookings.repositories;

import java.io.FileNotFoundException;

public interface BaseRepository<T> {

    T loadFromFile() throws FileNotFoundException;
    void saveToFile();

}

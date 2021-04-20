package com.itbqualitychallenge.bookings.repositories;

import java.io.FileNotFoundException;

public interface BaseRepository<T> {

    public void loadFromFile(String filename) throws FileNotFoundException;
    public void saveToFile(String filename);
    public T getItemById(String id);

}

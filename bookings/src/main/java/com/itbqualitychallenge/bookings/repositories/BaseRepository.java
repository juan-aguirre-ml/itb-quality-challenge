package com.itbqualitychallenge.bookings.repositories;

public interface BaseRepository<T> {

    public void loadFromFile(String filename);
    public void saveToFile(String filename);
    public T getItemById(String id);

}

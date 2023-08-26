package com.example.fit3151_lab5trial.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDao {

    @Query("select * from books ")
    LiveData<List<BookItem>> getAllItem();

    @Query("select * from books where booksTitle=:name")
    List<BookItem> getItem(String name);

    @Insert
    void addItem(BookItem item);

    @Query("delete from books where booksTitle= :name")
    void deleteItem(String name);

    @Query("delete FROM books")
    void deleteAllItems();

    @Query("delete from books where bookID = (SELECT MAX(bookID) FROM books) ")
    void deleteLastItem();

    @Query("delete from books where booksPrice > 50 ")
    void deleteSomeItem();



}
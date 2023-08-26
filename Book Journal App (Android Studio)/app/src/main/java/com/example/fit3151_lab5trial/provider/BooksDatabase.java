package com.example.fit3151_lab5trial.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {BookItem.class}, version = 1)
public abstract class BooksDatabase extends RoomDatabase {
    public static final String BOOKS_DATABASE_NAME = "books_database";

    public abstract BookDao itemDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile BooksDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static BooksDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BooksDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    BooksDatabase.class, BOOKS_DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
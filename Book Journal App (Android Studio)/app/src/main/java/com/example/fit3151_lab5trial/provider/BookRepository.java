package com.example.fit3151_lab5trial.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BookRepository {
    private BookDao mItemDao;
    private LiveData<List<BookItem>> mAllItems;

    BookRepository(Application application) {
        BooksDatabase db = BooksDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItem();
    }
    LiveData<List<BookItem>> getAllItems() {
        return mAllItems;
    }
    void insert(BookItem item) {
        BooksDatabase.databaseWriteExecutor.execute(() -> mItemDao.addItem(item));
    }

    void deleteAll(){
        BooksDatabase.databaseWriteExecutor.execute(()->{
            mItemDao.deleteAllItems();
        });
    }
    void deleteLastItem(){
        BooksDatabase.databaseWriteExecutor.execute(()->{
            mItemDao.deleteLastItem();
        });
    }
    void deleteSomeItem(){
        BooksDatabase.databaseWriteExecutor.execute(()->{
            mItemDao.deleteSomeItem();
        });
    }

}
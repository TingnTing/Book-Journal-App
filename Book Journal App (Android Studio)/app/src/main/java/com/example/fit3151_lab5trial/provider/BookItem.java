package com.example.fit3151_lab5trial.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class BookItem {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "bookID")
    private int itemID;
    @ColumnInfo(name = "bookAuthor")
    private String itemAuthor;
    @ColumnInfo(name = "bookDescription")
    private String itemDescription;
    @ColumnInfo(name = "booksPrice")
    private int itemPrice;
    @ColumnInfo(name = "booksTitle")
    private String itemTitle;
    @ColumnInfo(name = "booksISBN")
    private String itemISBN;

    public BookItem( String itemTitle, String itemISBN, String itemAuthor, String itemDescription, int itemPrice){
        this.itemTitle = itemTitle;
        this.itemISBN = itemISBN;
        this.itemAuthor = itemAuthor;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(@NonNull int itemID) {
        this.itemID = itemID;
    }

    public String getItemAuthor() {
        return itemAuthor;
    }

    public void setItemAuthor(String itemAuthor) {
        this.itemAuthor = itemAuthor;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {this.itemDescription = itemDescription;}

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemISBN() {
        return itemISBN;
    }

    public void setItemISBN(String itemISBN) {
        this.itemISBN = itemISBN;
    }
}
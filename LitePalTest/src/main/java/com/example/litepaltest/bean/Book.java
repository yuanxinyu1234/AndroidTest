package com.example.litepaltest.bean;

import org.litepal.crud.DataSupport;

public class Book extends DataSupport {
    private int id;
    private String name;
    private String author;
    private int pages;
    private String press;

    public Book() {
    }

    public Book(int id, String name, String author, int pages, String press) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.press = press;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                '}';
    }
}

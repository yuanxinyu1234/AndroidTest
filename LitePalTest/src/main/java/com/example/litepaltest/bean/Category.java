package com.example.litepaltest.bean;

import org.litepal.crud.DataSupport;

public class Category extends DataSupport {
    private int id;
    private String categoryName;
    private int cateoryCode;

    public Category() {
    }

    public Category(int id, String categoryName, int cateoryCode) {
        this.id = id;
        this.categoryName = categoryName;
        this.cateoryCode = cateoryCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCateoryCode() {
        return cateoryCode;
    }

    public void setCateoryCode(int cateoryCode) {
        this.cateoryCode = cateoryCode;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", cateoryCode=" + cateoryCode +
                '}';
    }
}

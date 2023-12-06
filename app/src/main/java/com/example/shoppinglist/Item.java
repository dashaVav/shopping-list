package com.example.shoppinglist;

import java.util.Objects;

public class Item {
    private long id;
    private String text;
    private Boolean isChecked;

    public Item(String name) {
        this.text = name;
        this.isChecked = false;
    }

    public Item(long id, String name, String isChecked) {
        this.id = id;
        this.text = name;
        this.isChecked = Objects.equals(isChecked, "1");
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public int getIsCheckedAsInt() {
        return isChecked ? 1 : 0;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

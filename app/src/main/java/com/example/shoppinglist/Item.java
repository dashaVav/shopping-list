package com.example.shoppinglist;

import java.util.Objects;

public class Item {
    private long id;
    private String text;
    private Boolean isChecked;

    public void setId(long id) {
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public Item(String name) {
        this.isChecked = false;
        this.text = name;
    }

    public Item(long id, String name, String isChecked) {
        this.id = id;
        this.isChecked = Objects.equals(isChecked, "1");
        this.text = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public String getText() {
        return text;
    }

    public int getIsCheckedAsInt() {
        if (isChecked) {
            return 1;
        } else {
            return 0;
        }
    }
}

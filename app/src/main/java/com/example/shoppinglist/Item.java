package com.example.shoppinglist;

public class Item {
    private String text;
    private Boolean isChecked;

    public Item(String name) {
        this.isChecked = false;
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
}

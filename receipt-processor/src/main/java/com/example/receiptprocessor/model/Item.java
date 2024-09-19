package com.example.receiptprocessor.model;

import jakarta.validation.constraints.*;

public class Item {
    
    @Pattern(regexp = "^[\\w\\s\\-]+$")
    private String shortDescription;

    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private String price;

    public Item(String shortDescription, String price) {
        this.shortDescription = shortDescription;
        this.price = price;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

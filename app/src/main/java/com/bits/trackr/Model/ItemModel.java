package com.bits.trackr.Model;

public class ItemModel {

    public String title,quantity;
    public ItemModel(){ }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuantity() { return quantity;}

    public void setQuantity(String quantity) { this.quantity=quantity; }
}

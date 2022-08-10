package com.techelevator.model;

import java.time.LocalDate;
import java.util.Date;

public class Item {

    private int itemId;
    private int listId;
    private String itemName;
    private int quantity;
    private LocalDate dateAdded;
    private String createdBy;

    public Item() {}

    public Item(int itemId, int listId, String itemName, int quantity, LocalDate dateAdded, String createdBy) {
        this.itemId = itemId;
        this.listId = listId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.dateAdded = dateAdded;
        this.createdBy = createdBy;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", listId=" + listId +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", dateAdded=" + dateAdded +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}

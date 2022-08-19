package com.techelevator.model;

public class ShoppingList {

    private int listId;
    private String listName;
    private int accountId;
    private String claimedBy;
    public ShoppingList(){}

    public ShoppingList(int listId, String listName, int accountId, String claimedBy) {
        this.listId = listId;
        this.listName = listName;
        this.accountId = accountId;
        this.claimedBy = claimedBy;


    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getClaimedBy() {
        return claimedBy;
    }

    public void setClaimedBy(String claimedBy) {
        this.claimedBy = claimedBy;
    }


    @Override
    public String toString() {
        return "ShoppingList{" +
                "listId=" + listId +
                ", listName='" + listName + '\'' +
                ", accountId=" + accountId +
                ", claimedBy='" + claimedBy + '\'' +
                '}';
    }
}

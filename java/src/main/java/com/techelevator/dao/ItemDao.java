package com.techelevator.dao;

import com.techelevator.model.Item;

import java.time.LocalDate;
import java.util.List;

public interface ItemDao {
    Item getItemByItemId(int itemId);

    List<Item> listAllItemsByListId(int listId, String username);

    void createItem(String itemName, int listId, int quantity, LocalDate dateAdded, String createdBy);

    void removeItem(int itemId);

}

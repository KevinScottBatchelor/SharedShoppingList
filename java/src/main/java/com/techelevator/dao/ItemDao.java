package com.techelevator.dao;

import com.techelevator.model.Item;

import java.time.LocalDate;
import java.util.List;

public interface ItemDao {
    Item getItemByItemId(int itemId);

    List<Item> listAllItemsByListId(int listId, String username);

    Item createItem(Item item, String username);

    void removeItem(int itemId);

    void updateItem(int itemId, String itemName, int quantity, String modifiedBy);

}

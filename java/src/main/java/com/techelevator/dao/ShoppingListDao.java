package com.techelevator.dao;

import com.techelevator.model.Item;
import com.techelevator.model.ShoppingList;

import java.util.List;

public interface ShoppingListDao {

    ShoppingList viewShoppingListByListId(int listId);
    List<ShoppingList> viewShoppingListsByAccountId(int accountId);

    void createShoppingList(ShoppingList shoppingList);

    void removeShoppingList(int listId, int accountId);

    List<ShoppingList> viewGroupShoppingLists(int groupId);

    void clearListWithoutGroup(int listId, int accountId);

    void clearListInGroup(int listId, int accountId);
    void claimShoppingList(int listId, String claimedBy);
}

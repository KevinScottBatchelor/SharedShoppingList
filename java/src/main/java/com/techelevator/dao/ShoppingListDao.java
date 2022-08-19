package com.techelevator.dao;

import com.techelevator.model.Item;
import com.techelevator.model.ShoppingList;

import java.util.List;

public interface ShoppingListDao {

    ShoppingList viewShoppingListByListId(int listId);
    List<ShoppingList> viewShoppingListsByAccountId(int accountId);

    void createShoppingList(ShoppingList shoppingList, int accountId);

    void removeShoppingList(int listId, int accountId);

    List<ShoppingList> viewGroupShoppingLists(int groupId);

    void clearListWithoutGroup(int listId);

    void clearListInGroup(int listId);
    void claimShoppingList(int listId, String claimedBy);
}

package com.techelevator.dao;

import com.techelevator.model.Group;
import com.techelevator.model.ShoppingList;

import java.util.List;

public interface GroupDao {

    void createGroup(Group group, int accountId);
    List<Group> viewGroupsByUsername(String username);

    Group getGroupByGroupId(int groupId);
    void deleteUserFromGroup(int accountId, int groupId);

    void createShoppingListInGroup(ShoppingList shoppingList, int accountId, int groupId);


}

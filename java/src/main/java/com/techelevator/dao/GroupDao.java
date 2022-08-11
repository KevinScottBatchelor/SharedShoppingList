package com.techelevator.dao;

import com.techelevator.model.Group;

import java.util.List;

public interface GroupDao {

    void createGroup(Group group, int accountId);
    List<Group> viewGroupsByUsername(String username);

    Group getGroupByGroupId(int groupId);
    void deleteUserFromGroup(int accountId, int groupId);


}

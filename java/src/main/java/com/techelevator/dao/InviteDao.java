package com.techelevator.dao;

public interface InviteDao {

    void inviteUserIntoGroup(int invitedUser, int fromUser, int groupId);

    void addUserIntoGroup(int invitedUser, int fromUser, int groupId);

    int inviteCodeGenerator();

    void updateInviteStatus(int invitedUser, int fromUser, int groupId);
}

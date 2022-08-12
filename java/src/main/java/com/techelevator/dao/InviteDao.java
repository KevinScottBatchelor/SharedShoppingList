package com.techelevator.dao;

import com.techelevator.model.Invite;

public interface InviteDao {

    void inviteUserIntoGroup(int invitedUser, int fromUser, int groupId);

    void addUserIntoGroup(int invitedUser, int fromUser, int groupId);

    int inviteCodeGenerator();

    void updateInviteStatus(int invitedUser, int fromUser, int groupId, String inviteCode);
    Invite getInvitationById(int inviteId);
}

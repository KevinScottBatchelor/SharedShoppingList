package com.techelevator.dao;

import com.techelevator.model.Invite;

import java.util.List;

public interface InviteDao {

    void inviteUserIntoGroup(int invitedUser, int fromUser, int groupId);

    void addUserIntoGroup(int invitedUser, int fromUser, int groupId);

    int inviteCodeGenerator();
    void rejectInvite(int inviteId);

    List<Invite> viewSentInvitations (int fromUserId);
    void updateInviteStatus(int invitedUser, int fromUser, int groupId, String inviteCode);
    List<Invite> viewMyInvitations (int invitedUserId);
    Invite getInvitationById(int inviteId);
}

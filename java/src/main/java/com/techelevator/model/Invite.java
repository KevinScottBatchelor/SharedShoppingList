package com.techelevator.model;

public class Invite {

    private int inviteId;
    private String inviteCode;
    private boolean isAccepted;
    private int invitedUser;
    private int fromUser;
    private int groupId;

    public Invite(){}

    public Invite(int inviteId, String inviteCode, boolean isAccepted, int invitedUser, int fromUser, int groupId) {
        this.inviteId = inviteId;
        this.inviteCode = inviteCode;
        this.isAccepted = isAccepted;
        this.invitedUser = invitedUser;
        this.fromUser = fromUser;
        this.groupId = groupId;
    }

    public int getInviteId() {
        return inviteId;
    }

    public void setInviteId(int inviteId) {
        this.inviteId = inviteId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public int getInvitedUser() {
        return invitedUser;
    }

    public void setInvitedUser(int invitedUser) {
        this.invitedUser = invitedUser;
    }

    public int getFromUser() {
        return fromUser;
    }

    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Invite{" +
                "inviteId=" + inviteId +
                ", inviteCode=" + inviteCode +
                ", isAccepted=" + isAccepted +
                ", invitedUser=" + invitedUser +
                ", fromUser=" + fromUser +
                ", groupId=" + groupId +
                '}';
    }
}
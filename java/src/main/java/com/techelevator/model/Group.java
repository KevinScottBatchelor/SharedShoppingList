package com.techelevator.model;

import java.time.LocalDate;

public class Group {

    private int groupId;
    private String groupName;
    private LocalDate joinedDate;

    private boolean isCreator;
    private int memberOfGroupId;

    public Group(){}

    public Group(int groupId, String groupName, LocalDate joinedDate, boolean isCreator, int memberOfGroupId) {

        this.groupId = groupId;
        this.groupName = groupName;
        this.joinedDate = joinedDate;
        this.isCreator = isCreator;
        this.memberOfGroupId = memberOfGroupId;

    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    public int getMemberOfGroupId() {
        return memberOfGroupId;
    }

    public void setMemberOfGroupId(int memberOfGroupId) {
        this.memberOfGroupId = memberOfGroupId;
    }

    public boolean isCreator() {
        return isCreator;
    }

    public void setCreator(boolean creator) {
        isCreator = creator;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", joinedDate=" + joinedDate +
                ", isCreator=" + isCreator +
                ", memberOfGroupId=" + memberOfGroupId +
                '}';
    }
}

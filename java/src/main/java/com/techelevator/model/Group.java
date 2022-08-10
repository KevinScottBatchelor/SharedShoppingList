package com.techelevator.model;

import java.time.LocalDate;

public class Group {

    private int groupId;
    private String groupName;

    private LocalDate joinedDate;

    private int memberOfGroupId;

    public Group(){}

    public Group(int groupId, String groupName, LocalDate joinedDate, int memberOfGroupId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.joinedDate = joinedDate;
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

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", joinedDate=" + joinedDate +
                ", memberOfGroupId=" + memberOfGroupId +
                '}';
    }
}

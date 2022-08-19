package com.techelevator.dao;

import com.techelevator.model.Invite;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class JdbcInviteDao implements InviteDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcInviteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Invite getInvitationById(int inviteId) {
        Invite invite = new Invite();

        String sql = "SELECT i.*, g.group_name FROM invite_status i JOIN groups g ON i.group_id = g.group_id WHERE invite_id = ?; ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, inviteId);
        if(result.next()) {
            invite = mapRowToInvite(result);
        }
        return invite;
    }

    @Override
    public List<Invite> viewMyInvitations (int invitedUserId) {
        List<Invite> invites = new ArrayList<>();

        String sql = "SELECT i.*, g.group_name FROM invite_status i " +
                "JOIN groups g ON g.group_id = i.group_id WHERE invited_user = ? AND is_accepted = false; ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, invitedUserId);
        while(result.next()) {
            invites.add(mapRowToInvite(result));
        }
        return invites;
    }

    @Override
    public List<Invite> viewSentInvitations (int fromUserId) {
        List<Invite> invites = new ArrayList<>();

        String sql = "SELECT i.*, g.group_name FROM invite_status i " +
                "JOIN groups g ON g.group_id = i.group_id WHERE from_user = ? AND is_accepted = false; ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, fromUserId);
        while(result.next()) {
            invites.add(mapRowToInvite(result));
        }
        return invites;
    }

    @Override
    public void inviteUserIntoGroup(int invitedUser, int fromUser, int groupId) {

        int inviteCode = inviteCodeGenerator();
        String sql = "INSERT INTO invite_status (invite_code, is_accepted, invited_user, from_user, group_id) VALUES" +
                "(?, false, ?, ?, ?);";

        jdbcTemplate.update(sql,inviteCode, invitedUser, fromUser, groupId);
    }

    @Override
    public void addUserIntoGroup(int invitedUser, int fromUser, int groupId) {
        String sql = "INSERT INTO account_groups(group_id, member_of_group_id, is_creator, joined_date) " +
                "VALUES(?, ?, false, current_date);";

        jdbcTemplate.update(sql, groupId, invitedUser);


    }

    @Override
    public void updateInviteStatus(int invitedUser, int fromUser, int groupId, String inviteCode) {
        String changeStatus = "UPDATE invite_status SET is_accepted = true WHERE invited_user = ? AND from_user = ? " +
                "AND group_id = ? AND invite_code = ?;";

        jdbcTemplate.update(changeStatus, invitedUser, fromUser, groupId, inviteCode);
    }

    @Override
    public void rejectInvite(int inviteId) {
        String sql = "DELETE FROM invite_status WHERE invite_id = ?;";

        jdbcTemplate.update(sql, inviteId);
    }

    @Override
    public int inviteCodeGenerator() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        return n;
    }

    private Invite mapRowToInvite(SqlRowSet rowSet) {
        Invite invite = new Invite();

        invite.setInviteId(rowSet.getInt("invite_id"));
        invite.setInviteCode(rowSet.getString("invite_code"));
        invite.setAccepted(rowSet.getBoolean("is_accepted"));
        invite.setInvitedUser(rowSet.getInt("invited_user"));
        invite.setFromUser(rowSet.getInt("from_user"));
        invite.setGroupId(rowSet.getInt("group_id"));
        invite.setGroupName(rowSet.getString("group_name"));

        return invite;
    }
}

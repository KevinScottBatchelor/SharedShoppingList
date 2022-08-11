package com.techelevator.dao;

import com.techelevator.model.UserNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class JdbcInviteDao implements InviteDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcInviteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void inviteUserIntoGroup(int invitedUser, int fromUser, int groupId) {

        int inviteCode = inviteCodeGenerator();
        String sql = "INSERT INTO invite_status (invite_code, is_accepted, invited_user, from_user, to_group) VALUES" +
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
    public void updateInviteStatus(int invitedUser, int fromUser, int groupId) {
        String changeStatus = "UPDATE invite_status SET is_accepted = true WHERE invited_user = ? AND from_user = ? " +
                "AND group_id = ?;";

        jdbcTemplate.update(changeStatus, invitedUser, fromUser, groupId);
    }

    @Override
    public int inviteCodeGenerator() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        return n;
    }
}

package com.techelevator.dao;

import com.techelevator.model.Group;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class JdbcGroupDao implements GroupDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcGroupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Group> viewGroupsByUsername(String username) {
        List<Group> groups = new ArrayList<>();
        String sql = "SELECT * FROM groups g JOIN account_groups ag ON ag.group_id = g.group_id " +
                "JOIN accounts a ON a.account_id = ag.member_of_group_id " +
                "JOIN users u ON u.user_id = a.user_id " +
                "WHERE username = ? ORDER BY group_name;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);

        while(results.next()) {
            groups.add(mapRowToGroup(results));
        }

        return groups;
    }

    @Override
    public void createGroup(Group group, int accountId) {

        String sql = "INSERT INTO groups(group_name) " +
                "VALUES (?) RETURNING group_id;";
        Integer groupId = jdbcTemplate.queryForObject(sql, Integer.class,group.getGroupName());

        sql = "INSERT INTO account_groups(group_id, member_of_group_id, is_creator, joined_date) " +
                "VALUES (?, ?, true, current_date);";

        jdbcTemplate.update(sql,groupId, accountId);
    }

    @Override
    public void inviteUserIntoGroup(int groupId, int memberId) {
        String sql = "INSERT INTO account_groups(group_id, member_of_group_id, is_creator, joined_date) " +
                "VALUES(?, ?, false, current_date);";

        jdbcTemplate.update(sql, groupId, memberId);

    }

    public int inviteCodeGenerator() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        return n;
    }

    private Group mapRowToGroup(SqlRowSet rowSet) {
        Group group = new Group();

        group.setGroupId(rowSet.getInt("group_id"));
        group.setGroupName(rowSet.getString("group_name"));
        group.setJoinedDate(rowSet.getDate("joined_date").toLocalDate());

        return group;
    }
}

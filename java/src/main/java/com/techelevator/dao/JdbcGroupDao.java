package com.techelevator.dao;

import com.techelevator.Exception.GroupNotFoundException;
import com.techelevator.model.Group;
import com.techelevator.model.ShoppingList;
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


    public void createShoppingListInGroup(ShoppingList shoppingList, int accountId, int groupId) {
        String sql = "INSERT INTO lists(list_name, account_id, claimed_by) VALUES (?,?,?) RETURNING list_id;";
        int listId = jdbcTemplate.queryForObject(sql,Integer.class, shoppingList.getListName(), accountId, shoppingList.getClaimedBy());

        sql ="INSERT INTO lists_in_group(group_id, list_id) VALUES(?, ?);";
        jdbcTemplate.update(sql,groupId, listId);
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

    public Group getGroupByGroupId(int groupId) {
        Group group = null;
        String sql = "SELECT group_id, group_name FROM groups WHERE group_id = ?; ";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql,groupId);

        if(result.next()) {
            group = mapRowToGroupId(result);
        } else throw new GroupNotFoundException();

        return group;

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
    public void deleteUserFromGroup(int accountId, int groupId) {
        String sql = "DELETE FROM account_groups WHERE member_of_group_id = ? AND group_id = ?;";

        jdbcTemplate.update(sql,accountId, groupId);
    }

    private Group mapRowToGroup(SqlRowSet rowSet) {
        Group group = new Group();

        group.setGroupId(rowSet.getInt("group_id"));
        group.setGroupName(rowSet.getString("group_name"));

        group.setJoinedDate(rowSet.getDate("joined_date").toLocalDate());


        group.setCreator(rowSet.getBoolean("is_creator"));



        group.setMemberOfGroupId(rowSet.getInt("member_of_group_id"));

        return group;
    }

    private Group mapRowToGroupId(SqlRowSet rowSet) {
        Group group = new Group();

        group.setGroupId(rowSet.getInt("group_id"));
        group.setGroupName(rowSet.getString("group_name"));

        return group;
    }
}

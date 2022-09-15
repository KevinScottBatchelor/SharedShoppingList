package com.techelevator.dao;

import com.techelevator.Exception.ShoppingListNotFoundException;
import com.techelevator.model.Item;
import com.techelevator.model.ShoppingList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcShoppingListDao implements ShoppingListDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcShoppingListDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ShoppingList viewShoppingListByListId(int listId) {
        ShoppingList shoppingList = null;
        String sql = "SELECT * FROM lists WHERE list_id = ? ";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, listId);

        if(result.next()) {
            shoppingList = mapRowToShoppingList(result);
        } else throw new ShoppingListNotFoundException();

        return shoppingList;
    }

    @Override
    public List<ShoppingList> viewShoppingListsByAccountId(int accountId) {
        List<ShoppingList> itemLists = new ArrayList<>();
        String sql = "SELECT * FROM lists l WHERE l.account_id = ? AND l.list_id NOT IN (SELECT lig.list_id FROM lists_in_group lig)";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);

        while(results.next()) {
            itemLists.add(mapRowToShoppingList(results));
        }

        return itemLists;
    }

    @Override
    public List<ShoppingList> viewGroupShoppingLists(int groupId) {
        List<ShoppingList> shoppingLists = new ArrayList<>();
        String sql = "SELECT * FROM lists l JOIN lists_in_group lig ON l.list_id = lig.list_id WHERE lig.group_id = ? " +
                "ORDER BY l.list_name;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, groupId);
        while(results.next()) {
            shoppingLists.add(mapRowToShoppingList(results));
        }

        return shoppingLists;
    }

    @Override
    public void createShoppingList(ShoppingList shoppingList, int accountId) {
        String sql = "INSERT INTO lists(list_name, account_id, claimed_by) VALUES (?,?,?);";
        jdbcTemplate.update(sql,shoppingList.getListName(), accountId, shoppingList.getClaimedBy());
    }

    public void claimShoppingList(int listId, String claimedBy) {
        String sql = "UPDATE lists SET claimed_by = ? WHERE list_id = ?; ";
        jdbcTemplate.update(sql, claimedBy, listId);
    }

    //UPDATE LIST NAME

    @Override
    public void removeShoppingList(int listId, int accountId) {
        String sql = "DELETE FROM items i " +
                "WHERE i.list_id = ? AND i.list_id IN (SELECT i.list_id FROM items i " +
                "JOIN lists l ON i.list_id = l.list_id JOIN accounts a ON l.account_id = ?); " +
                "DELETE FROM lists_in_group lg " +
                "WHERE lg.list_id = ? AND lg.list_id IN  (SELECT lg.list_id FROM lists_in_group lg " +
                "JOIN lists l ON lg.list_id = l.list_id JOIN accounts a ON l.account_id = ?); " +
                "DELETE FROM lists l " +
                "WHERE l.list_id = ? AND l.list_id IN (SELECT l.list_id FROM lists l " +
                "JOIN accounts a ON l.account_id = ? );";

        jdbcTemplate.update(sql, listId, accountId, listId, accountId , listId, accountId);
    }

    @Override
    public void clearListWithoutGroup(int listId) {
        String sql = "DELETE FROM items i WHERE i.list_id = (SELECT l.list_id FROM lists l " +
                "WHERE l.list_id = ?);";

        jdbcTemplate.update(sql, listId);

        sql = "UPDATE lists SET claimed_by = null WHERE list_id = ?;";

        jdbcTemplate.update(sql,listId);
    }

    @Override
    public void clearListInGroup(int listId) {
        String sql = "DELETE FROM items i WHERE i.list_id = (SELECT l.list_id FROM lists l " +
                "WHERE l.list_id = ?);";

        jdbcTemplate.update(sql, listId);

        sql = "UPDATE lists SET claimed_by = null WHERE list_id = ?;";

        jdbcTemplate.update(sql,listId);
    }

    private ShoppingList mapRowToShoppingList(SqlRowSet rowSet) {
        ShoppingList shoppingList = new ShoppingList();

        shoppingList.setListId(rowSet.getInt("list_id"));
        shoppingList.setListName(rowSet.getString("list_name"));
        shoppingList.setAccountId(rowSet.getInt("account_id"));
        shoppingList.setClaimedBy(rowSet.getString("claimed_by"));

        return shoppingList;
    }
}

package com.techelevator.dao;

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
    public List<ShoppingList> viewShoppingListsByAccountId(int accountId) {
        List<ShoppingList> itemLists = new ArrayList<>();
        String sql = "SELECT * FROM lists WHERE account_id = ? ORDER BY list_name;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);

        while(results.next()) {
            itemLists.add(mapRowToShoppingList(results));
        }

        return itemLists;
    }

    @Override
    public List<ShoppingList> viewGroupShoppingLists(int groupId) {
        List<ShoppingList> shoppingLists = new ArrayList<>();
        String sql = "SELECT * FROM lists l JOIN lists_in_group lig ON l.list_id = lig.list_id WHERE group_id = ? " +
                "ORDER BY list_name ;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, groupId);
        while(results.next()) {
            shoppingLists.add(mapRowToShoppingList(results));
        }
        return shoppingLists;
    }

    @Override
    public void createShoppingList(ShoppingList shoppingList) {
        String sql = "INSERT INTO lists(list_name, account_id, claimed_by) VALUES (?,?,?);";
        jdbcTemplate.update(sql,shoppingList.getListName(), shoppingList.getAccountId(), shoppingList.getClaimedBy());
    }

    @Override
    public void removeShoppingList(int listId, int accountId) {
        String sql = "DELETE FROM lists l WHERE l.list_id = ? AND account_id = ?";

        jdbcTemplate.update(sql, listId, accountId);
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

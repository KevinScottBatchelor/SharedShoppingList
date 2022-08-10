package com.techelevator.dao;

import com.techelevator.model.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcItemDao implements ItemDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Item getItemByItemId(int itemId) {
        Item item = null;
        String sql = "SELECT * FROM items WHERE item_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, itemId);
        if(results.next()) {
            item = mapRowToItem(results);
        }
        return item;
    }

    @Override
    public List<Item> listAllItemsByListId(int listId, String username) {
        List<Item> itemLists = new ArrayList<>();
        String sql = "SELECT item_id, i.list_id,  item_name, quantity, date_added, created_by " +
                "FROM items i JOIN lists l ON  l.list_id = i.list_id JOIN accounts a " +
                "ON a.account_id = l.account_id JOIN users u ON u.user_id = a.user_id WHERE i.list_id = ? " +
                "AND username = ? ORDER BY item_name;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, listId, username);
        while(results.next()) {
            Item itemResult = mapRowToItem(results);
            itemLists.add(itemResult);
        }
        return itemLists;
    }

    @Override
    public void createItem(String itemName, int listId, int quantity, LocalDate dateAdded, String createdBy) {
        String sql = "INSERT INTO items(item_name, list_id, quantity, date_added, created_by)" +
                "VALUES (?,?,?,?,?);";
        jdbcTemplate.update(sql, itemName,listId,quantity,dateAdded,createdBy);
    }

    @Override
    public void removeItem(int itemId) {
        String sql = "DELETE FROM items WHERE item_id = ?;";
        jdbcTemplate.update(sql, itemId);
    }

    private Item mapRowToItem(SqlRowSet rowSet) {
        Item item = new Item();

        item.setItemId(rowSet.getInt("item_id"));
        item.setListId(rowSet.getInt("list_id"));
        item.setItemName(rowSet.getString("item_name"));
        item.setQuantity(rowSet.getInt("quantity"));
        item.setDateAdded(rowSet.getDate("date_added").toLocalDate());
        item.setCreatedBy(rowSet.getString("created_by"));

        return item;
    }
}

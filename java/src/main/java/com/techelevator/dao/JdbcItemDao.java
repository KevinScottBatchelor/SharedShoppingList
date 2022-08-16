package com.techelevator.dao;

import com.techelevator.Exception.ItemNotFoundException;
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
        String sql = "SELECT i.item_id, i.list_id, i.item_name, i.quantity, i.date_added, i.created_by," +
                " i.date_modified, i.modified_by FROM items i WHERE i.item_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, itemId);
        if(results.next()) {
            item = mapRowToItem(results);
        }
        return item;
    }

    @Override
    public List<Item> listAllItemsByListId(int listId, String username) {
        List<Item> itemLists = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM items i JOIN lists l ON  l.list_id = i.list_id " +
                "WHERE i.list_id = ? " +
                "AND i.created_by = ? ORDER BY i.item_name;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, listId, username);
        while(results.next()) {
            Item itemResult = mapRowToItem(results);
            itemLists.add(itemResult);
        }
        return itemLists;
    }

    @Override
    public Item createItem(Item item, String username) {

        String sql = "INSERT INTO items(item_name, list_id, quantity, date_added, created_by, date_modified, modified_by)" +
                "VALUES (?,?,?,current_date,?,null,null) RETURNING item_id;";
        Integer itemId = jdbcTemplate.queryForObject(sql,Integer.class, item.getItemName(), item.getListId(), item.getQuantity(), username);

        return getItemByItemId(itemId);
    }

    @Override
    public void removeItem(int itemId) {
        String sql = "DELETE FROM items WHERE item_id = ?;";
        jdbcTemplate.update(sql, itemId);
    }
    @Override
    public void updateItem(int itemId, String itemName, int quantity, String modifiedBy) {
        String sql = "UPDATE items SET item_name = ? , quantity = ?, date_modified = current_Date, modified_by = ?" +
                " WHERE item_id = ?; ";

        jdbcTemplate.update(sql, itemName, quantity, modifiedBy, itemId);
    }

    private Item mapRowToItem(SqlRowSet rowSet) {
        Item item = new Item();

        item.setItemId(rowSet.getInt("item_id"));
        item.setListId(rowSet.getInt("list_id"));
        item.setItemName(rowSet.getString("item_name"));
        item.setQuantity(rowSet.getInt("quantity"));
        item.setDateAdded(rowSet.getDate("date_added").toLocalDate());
        item.setCreatedBy(rowSet.getString("created_by"));

        if(rowSet.getDate("date_modified") != null) {
            item.setDateModified(rowSet.getDate("date_modified").toLocalDate());
        }

        if(rowSet.getString("modified_By") != null) {
            item.setModifiedBy(rowSet.getString("modified_By"));
        }


        return item;
    }
}

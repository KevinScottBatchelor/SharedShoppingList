package com.techelevator.controller;

import com.techelevator.dao.ItemDao;
import com.techelevator.model.Item;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "lists/")
public class ItemController {

    private final ItemDao itemDao;

    public ItemController(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @RequestMapping(path = "items/{id}")
    public Item get(@PathVariable int id) {
        return itemDao.getItemByItemId(id);
    }

    @RequestMapping(path = "{id}")
    public List<Item> getListByListId(@PathVariable int id, Principal principal) {
        return itemDao.listAllItemsByListId(id, principal.getName());

    }
}

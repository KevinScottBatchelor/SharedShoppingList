package com.techelevator.controller;

import com.techelevator.dao.AccountDao;
import com.techelevator.dao.ItemDao;
import com.techelevator.dao.ShoppingListDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Item;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "lists/")
public class ItemController {

    private final ItemDao itemDao;
    private final AccountDao accountDao;

    private final UserDao userDao;
    private final ShoppingListDao shoppingListDao;

    public ItemController(ItemDao itemDao, AccountDao accountDao, ShoppingListDao shoppingListDao, UserDao userDao) {
        this.itemDao = itemDao;
        this.accountDao = accountDao;
        this.shoppingListDao = shoppingListDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "items/{id}")
    public Item get(@PathVariable int id) {
        return itemDao.getItemByItemId(id);
    }

    @RequestMapping(path = "{id}")
    public List<Item> getListByListId(@PathVariable int id, Principal principal) {
        return itemDao.listAllItemsByListId(id, principal.getName());

    }

    @ResponseStatus(HttpStatus.GONE)
    @RequestMapping(path = "deleteItem", method = RequestMethod.DELETE)
    public void removeItemById(@RequestParam int itemId, Principal principal) {
        itemDao.removeItem(itemId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "items", method = RequestMethod.POST)
    public Item createItem(@RequestBody @Valid Item item, Principal principal) {
        item = itemDao.createItem(item, principal.getName());
        return item;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "items/{itemId}", method = RequestMethod.PUT)
    public void updateItem(@RequestBody @Valid Item item, @PathVariable int itemId, Principal principal) {
        if(item.getCreatedBy().equals(principal.getName()) ||
                accountDao.getAccountIdByUsername(principal.getName()).getAccountId() == item.getMemberOfGroupId())
        itemDao.updateItem(itemId, item.getItemName(), item.getQuantity(), principal.getName());
    }
}

package com.techelevator.controller;

import com.techelevator.dao.JdbcShoppingListDao;
import com.techelevator.dao.ShoppingListDao;
import com.techelevator.model.ShoppingList;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "lists/")
public class ShoppingListController {

    private final ShoppingListDao shoppingListDao;

    public ShoppingListController(ShoppingListDao shoppingListDao) {
        this.shoppingListDao = shoppingListDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void createShoppingList(@RequestBody @Valid ShoppingList shoppingList) {
        shoppingListDao.createShoppingList(shoppingList);
    }

    @RequestMapping(path = "account/{accountId}")
    public List<ShoppingList> viewShoppingLists(@PathVariable int accountId) {
        return shoppingListDao.viewShoppingListsByAccountId(accountId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path ="{listId}", method = RequestMethod.DELETE)
    public void removeShoppingList(@PathVariable int listId, @RequestParam int accountId) {
        shoppingListDao.removeShoppingList(listId, accountId);
    }

    @RequestMapping
    public List<ShoppingList> viewShoppingListsByGroupId(@RequestParam int groupId) {
        return shoppingListDao.viewGroupShoppingLists(groupId);
    }

}

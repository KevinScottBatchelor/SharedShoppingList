package com.techelevator.controller;

import com.techelevator.dao.AccountDao;
import com.techelevator.dao.JdbcShoppingListDao;
import com.techelevator.dao.ShoppingListDao;
import com.techelevator.model.Item;
import com.techelevator.model.ShoppingList;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.method.P;
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
    private final AccountDao accountDao;

    public ShoppingListController(ShoppingListDao shoppingListDao, AccountDao accountDao) {
        this.shoppingListDao = shoppingListDao;
        this.accountDao = accountDao;
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

    @RequestMapping(path = "delete", method = RequestMethod.DELETE)
    public void clearList(@RequestParam int listId, ShoppingList shoppingList,Principal principal) {
        if(accountDao.getAccountIdByUsername(principal.getName()).getAccountId() == shoppingList.getAccountId() ) {  // NEED TO ADD SECOND PART OF VALIDATION
            shoppingListDao.clearList(listId);
        }
    }
}

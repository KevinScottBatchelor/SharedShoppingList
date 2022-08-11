package com.techelevator.controller;

import com.techelevator.Exception.GroupNotFoundException;
import com.techelevator.dao.AccountDao;
import com.techelevator.dao.GroupDao;
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
    private final GroupDao groupDao;

    public ShoppingListController(ShoppingListDao shoppingListDao, AccountDao accountDao, GroupDao groupDao) {
        this.shoppingListDao = shoppingListDao;
        this.accountDao = accountDao;
        this.groupDao = groupDao;
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

    @ResponseStatus(HttpStatus.GONE)
    @RequestMapping(path ="{listId}", method = RequestMethod.DELETE)
    public void removeShoppingList(@PathVariable int listId, @RequestParam int accountId) {
        shoppingListDao.removeShoppingList(listId, accountId);
    }

    @RequestMapping
    public List<ShoppingList> viewShoppingListsByGroupId(@RequestParam int groupId) {
        if(groupDao.getGroupByGroupId(groupId) == null) throw new GroupNotFoundException();
        return shoppingListDao.viewGroupShoppingLists(groupId);
    }

    @ResponseStatus(HttpStatus.GONE)
    @RequestMapping(path = "delete", method = RequestMethod.DELETE)
    public void clearList(@RequestParam int listId, @RequestBody @Valid ShoppingList shoppingList,Principal principal) {
        if(accountDao.getAccountIdByUsername(principal.getName()).getAccountId() == shoppingList.getAccountId()) {
            shoppingListDao.clearListWithoutGroup(listId, accountDao.getAccountIdByUsername(principal.getName()).getAccountId());
        } else {
            shoppingListDao.clearListInGroup(listId, accountDao.getAccountIdByUsername(principal.getName()).getAccountId());
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "{listId}/claim", method = RequestMethod.PUT)
    public void claimShoppingList(@PathVariable int listId, @RequestBody @Valid ShoppingList shoppingList, Principal principal) {


        if(shoppingList.getClaimedBy()!= null && shoppingList.getClaimedBy().equals(principal.getName())) {
            shoppingListDao.claimShoppingList(listId, null);
            System.out.println("im the owner");
        } else if (shoppingList.getClaimedBy()!= null && !shoppingList.getClaimedBy().equals(principal.getName())) {
            System.out.println("Shopping list has been claimed");
        } else if (shoppingList.getClaimedBy()== null ){
            shoppingListDao.claimShoppingList(listId, principal.getName());
            System.out.printf("nobody");
        }
    }
}

package com.techelevator.controller;

import com.techelevator.Exception.GroupNotFoundException;
import com.techelevator.dao.AccountDao;
import com.techelevator.dao.GroupDao;
import com.techelevator.dao.JdbcShoppingListDao;
import com.techelevator.dao.ShoppingListDao;
import com.techelevator.model.Item;
import com.techelevator.model.ShoppingList;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
//@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "lists/")
@CrossOrigin
public class ShoppingListController {

    private final ShoppingListDao shoppingListDao;
    private final AccountDao accountDao;
    private final GroupDao groupDao;

    public ShoppingListController(ShoppingListDao shoppingListDao, AccountDao accountDao, GroupDao groupDao) {
        this.shoppingListDao = shoppingListDao;
        this.accountDao = accountDao;
        this.groupDao = groupDao;
    }

    @RequestMapping(path = "list/{listId}")
    public ShoppingList viewShoppingListByListId(@PathVariable int listId) {

        return shoppingListDao.viewShoppingListByListId(listId);
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void createShoppingList(@RequestBody @Valid ShoppingList shoppingList, Principal principal) {
        int accountId = accountDao.getAccountIdByUsername(principal.getName()).getAccountId();
        shoppingListDao.createShoppingList(shoppingList,accountId );
    }

    @RequestMapping(path = "account/mylists")
    public List<ShoppingList> viewShoppingListsByAccountId(Principal principal) {
        int accountId;
        accountId = accountDao.getAccountIdByUsername(principal.getName()).getAccountId();

        return shoppingListDao.viewShoppingListsByAccountId(accountId);
    }

    @RequestMapping(path = "group{groupId}")
    public List<ShoppingList> viewShoppingListsByGroupId(@RequestParam int groupId) {

        List<ShoppingList> newList = shoppingListDao.viewGroupShoppingLists(groupId);
        if(newList == null) throw new GroupNotFoundException();

        return newList;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path ="delete/{listId}", method = RequestMethod.DELETE)
    public void removeShoppingList(@PathVariable int listId, @RequestParam int accountId, Principal principal) {
        accountId = accountDao.getAccountIdByUsername(principal.getName()).getAccountId();
        shoppingListDao.removeShoppingList(listId, accountId);
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "delete", method = RequestMethod.DELETE)
    public void clearList(@RequestParam int listId) {

        shoppingListDao.clearListInGroup(listId);


    }

    @ResponseStatus(HttpStatus.OK)
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

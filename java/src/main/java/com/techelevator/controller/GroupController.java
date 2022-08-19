package com.techelevator.controller;

import com.techelevator.dao.AccountDao;
import com.techelevator.dao.GroupDao;
import com.techelevator.dao.InviteDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Path;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "groups/")
@CrossOrigin
public class GroupController {

    private final GroupDao groupDao;
    private final AccountDao accountDao;
    private final InviteDao inviteDao;

    private final UserDao userDao;



    public GroupController(GroupDao groupDao, AccountDao accountDao, InviteDao inviteDao, UserDao userDao) {
        this.groupDao = groupDao;
        this.accountDao = accountDao;
        this.inviteDao = inviteDao;
        this.userDao = userDao;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path="getId/{username}")
    public int getUserIdByUsername(@PathVariable String username) {
        return userDao.findIdByUsername(username);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path="getUsername/{userId}")
    public String getUsernameByUserId(@PathVariable int userId) {
        return userDao.getUserById(userId).getUsername();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "{groupId}", method = RequestMethod.GET)
    public Group getGroupByGroupId(@PathVariable int groupId) {
        return groupDao.getGroupByGroupId(groupId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void createGroup(@RequestBody @Valid Group group, Principal principal) {
        Account account = accountDao.getAccountIdByUsername(principal.getName());
        groupDao.createGroup(group, account.getAccountId());
    }
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "list/{groupId}", method=RequestMethod.POST)
    public void createShoppingListInGroup(@RequestBody @Valid ShoppingList shoppingList, Principal principal,@PathVariable int groupId) {
        int accountId = accountDao.getAccountIdByUsername(principal.getName()).getAccountId();
        groupDao.createShoppingListInGroup(shoppingList,accountId, groupId);
    }

    @RequestMapping
    public List<Group> viewAllGroupsByUsername(@RequestParam String username, Principal principal){
        username = principal.getName();
        return groupDao.viewGroupsByUsername(username);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "invitation/{accountId}", method = RequestMethod.GET)
    public List<Invite> viewMyInvitations(@PathVariable int accountId, Principal principal) {
        accountId = userDao.findIdByUsername(principal.getName());

        return inviteDao.viewMyInvitations(accountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "sentInvitation/{accountId}", method = RequestMethod.GET)
    public List<Invite> viewSentInvitations (@PathVariable int accountId, Principal principal) {
        accountId = userDao.findIdByUsername(principal.getName());

        return inviteDao.viewSentInvitations(accountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "invitation/" ,method = RequestMethod.POST)
    public void inviteUserToGroup(@RequestBody Invite invite) {

        inviteDao.inviteUserIntoGroup(invite.getInvitedUser(), invite.getFromUser(), invite.getGroupId());

    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "{groupId}" , method = RequestMethod.POST)
    public void addUserIntoGroup(@RequestBody @Valid Invite invite, @PathVariable int groupId ) {

        inviteDao.addUserIntoGroup(invite.getInvitedUser(), invite.getFromUser(), invite.getGroupId());
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "invitation/", method = RequestMethod.PUT)
    public void updateInviteStatus(@RequestBody @Valid Invite invite) {

        Invite newInvite = inviteDao.getInvitationById(invite.getInviteId());

        if (newInvite.getInviteCode().equals( invite.getInviteCode())) {
            inviteDao.updateInviteStatus(invite.getInvitedUser(), invite.getFromUser(), invite.getGroupId(), invite.getInviteCode());
            System.out.print("Welcome!");

        } else {
            System.out.println("WRONG INVITE CODE");
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "leave/", method = RequestMethod.DELETE)
    public void deleteUserFromGroup(@RequestParam int accountId, @RequestParam int groupId) {

            groupDao.deleteUserFromGroup(accountId, groupId);

    }

    @RequestMapping(path ="users/{username}")
    public List<User> findAllUsers(@PathVariable String username, Principal principal) {
        username = principal.getName();

        return userDao.findAll();
    }


    @RequestMapping(path = "reject/{inviteId}", method = RequestMethod.DELETE)
    public void rejectInvite(@PathVariable int inviteId) {
        inviteDao.rejectInvite(inviteId);
    }

}

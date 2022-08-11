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

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void createGroup(@RequestBody @Valid Group group, Principal principal) {
        Account account = accountDao.getAccountIdByUsername(principal.getName());
        groupDao.createGroup(group, account.getAccountId());
    }

    @RequestMapping
    public List<Group> viewAllGroupsByUsername(Principal principal){
        return groupDao.viewGroupsByUsername(principal.getName());
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "invitation/" ,method = RequestMethod.POST)
    public void inviteUserToGroup(@RequestBody Invite invite) {

        String username = accountDao.getUsernameByAccountId(invite.getInvitedUser());
        if (userDao.findByUsername(username) == null) throw new UserNotFoundException();

        inviteDao.inviteUserIntoGroup(invite.getInvitedUser(), invite.getFromUser(), invite.getGroupId());

    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "{groupId}" , method = RequestMethod.POST)
    public void addUserIntoGroup(@RequestBody @Valid Invite invite, @PathVariable int groupId ) {
        String username = accountDao.getUsernameByAccountId(invite.getInvitedUser());
        if (userDao.findByUsername(username) == null) throw new UserNotFoundException();

        inviteDao.addUserIntoGroup(invite.getInvitedUser(), invite.getFromUser(), invite.getGroupId());
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "invitation/", method = RequestMethod.PUT)
    public void updateInviteStatus(@RequestBody @Valid Invite invite) {
        inviteDao.updateInviteStatus(invite.getInvitedUser(), invite.getFromUser(), invite.getGroupId());
    }

    @ResponseStatus(HttpStatus.GONE)
    @RequestMapping(path = "leave/", method = RequestMethod.DELETE)
    public void deleteUserFromGroup(@RequestParam int accountId, @RequestParam int groupId, Principal principal,@RequestBody @Valid Group group) {

        if(accountId == accountDao.getAccountIdByUsername(principal.getName()).getAccountId() || group.isCreator()) {
            groupDao.deleteUserFromGroup(accountId, groupId);
        }
    }

}

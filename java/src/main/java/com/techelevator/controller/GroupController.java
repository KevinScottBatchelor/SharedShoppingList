package com.techelevator.controller;

import com.techelevator.dao.AccountDao;
import com.techelevator.dao.GroupDao;
import com.techelevator.dao.InviteDao;
import com.techelevator.model.Account;
import com.techelevator.model.Group;
import com.techelevator.model.Invite;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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



    public GroupController(GroupDao groupDao, AccountDao accountDao, InviteDao inviteDao) {
        this.groupDao = groupDao;
        this.accountDao = accountDao;
        this.inviteDao = inviteDao;
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

    @RequestMapping(path = "invitation/" ,method = RequestMethod.POST)
    public void inviteUserToGroup(@RequestBody Invite invite) {
        inviteDao.inviteUserIntoGroup(invite.getInvitedUser(), invite.getFromUser(), invite.getGroupId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "{groupId}" , method = RequestMethod.POST)
    public void addUserIntoGroup(@RequestBody Invite invite, @PathVariable int groupId ) {
        inviteDao.addUserIntoGroup(invite.getInvitedUser(), invite.getFromUser(), invite.getGroupId());
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "invitation/", method = RequestMethod.PUT)
    public void updateInviteStatus(@RequestBody Invite invite) {
        inviteDao.updateInviteStatus(invite.getInvitedUser(), invite.getFromUser(), invite.getGroupId());
    }

    @ResponseStatus(HttpStatus.GONE)
    @RequestMapping(path = "leave/", method = RequestMethod.DELETE)
    public void deleteUserFromGroup(@RequestParam int accountId, @RequestParam int groupId, Principal principal, Group group) {
        group = groupDao.getGroupByGroupId(groupId);
        if(accountId == accountDao.getAccountIdByUsername(principal.getName()).getAccountId() || group.isCreator()) {
            groupDao.deleteUserFromGroup(accountId, groupId);
        }
    }

}

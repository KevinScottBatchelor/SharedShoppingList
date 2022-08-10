package com.techelevator.controller;

import com.techelevator.dao.AccountDao;
import com.techelevator.dao.GroupDao;
import com.techelevator.model.Account;
import com.techelevator.model.Group;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "groups/")
public class GroupController {

    private final GroupDao groupDao;
    private final AccountDao accountDao;
    private int inviteCode;



    public GroupController(GroupDao groupDao, AccountDao accountDao) {
        this.groupDao = groupDao;
        this.accountDao = accountDao;

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
    public void inviteUserToGroup(@RequestBody Group group, int inviteCode) {

        if(inviteCode == groupDao.inviteCodeGenerator()) {
            groupDao.inviteUserIntoGroup(group.getGroupId(), group.getMemberOfGroupId());
        } else {
            System.out.println("Invalid invite code!!");
        }
    }
}

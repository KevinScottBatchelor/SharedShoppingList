import axios from 'axios';
import store from '../store/index';

const http = axios.create({
    baseURL: "http://localhost:9000/"
});

export default {

    getGroupByGroupId(id) {
        this.state = store.state;
        return http.get(`groups/${id}`)
    },

    createGroup(group) {
        this.state = store.state;
        return http.post(`groups/`, group)
    },

    createShoppingListInGroup(shoppingList, groupId) {
        this.state = store.state;
        return http.post(`groups/list/${groupId}`, shoppingList)
    },

    viewAllGroupsByUsername(username) {
        this.state = store.state;
        return http.get(`groups/?username=${username}`)
    },


    viewMyInvitations(accountId) {
        this.state = store.state;
        return http.get(`groups/invitation/${accountId}`)
    },

    inviteUserToGroup(invite) {
        this.state = store.state;
        return http.post(`groups/invitation`, invite)
    },

    addUserToGroup(groupId, invite) {
        this.state = store.state;
        return http.post(`groups/${groupId}`, invite)
    },

    updateInviteStatus(invite) {
        this.state = store.state;
        return http.put(`groups/invitation/`, invite)
    },

    deleteUserFromGroup(accountId, groupId) {
        this.state = store.state;
        return http.delete(`groups/leave/?accountId=${accountId}&groupId=${groupId}`)
    }
}
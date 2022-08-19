import axios from 'axios';
import store from '../store/index';

const http = axios.create({
    baseURL: "http://localhost:9000/"
});

export default {

    createShoppingList(shoppingList) {
        this.state = store.state;
        return http.post(`lists/`, shoppingList);
    },

    viewAllItemsByListId(listId) {
        
        return http.get(`lists/${listId}`);
    },

    viewShoppingListByListId(listId) {
        this.state = store.state;
        return http.get(`lists/list/${listId}`);
    },

    viewShoppingListsByAccountId() {
        this.state = store.state;
        return http.get(`lists/account/mylists`);
    },

    viewShoppingListsByGroupId(groupId) {
        this.state = store.state;
        return http.get(`lists/group?groupId=${groupId}`);
    },

    removeShoppingList(listId) {
        this.state = store.state;
        const id = this.state.user.id;
        return http.delete(`lists/delete/${listId}?accountId=${id}`);
    },

    clearList(listId) {
        this.state = store.state;
        return http.delete(`lists/delete?listId=${listId}`);
    },

    claimShoppingList(listId,shoppingList) {
        this.state = store.state;
        return http.put(`lists/${listId}/claim`,shoppingList);
    }

}
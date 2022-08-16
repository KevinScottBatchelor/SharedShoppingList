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

    viewShoppingListByListId(listId) {
        this.state = store.state;
        return http.get(`lists/${listId}`);
    },

    viewShoppingListsByAccountId() {
        this.state = store.state;
        return http.get(`lists/account/mylists`);
    },

    viewShoppingListsByGroupId(groupId) {
        this.state = store.state;
        return http.get(`lists/group${groupId}`);
    },

    removeShoppingList(listId, shoppingList) {
        this.state = store.state;
        return http.delete(`lists/delete/${listId}`, shoppingList);
    },

    clearList(shoppingList) {
        this.state = store.state;
        return http.delete(`lists/delete`, shoppingList);
    },

    claimShoppingList(listId) {
        this.state = store.state;
        return http.put(`lists/${listId}/claim`);
    }

}
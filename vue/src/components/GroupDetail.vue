<template>
    <div>
        <div id="main-container">
            <div class="shopping-lists">
                <h3 id="shoppinglist-title">Group Shopping Lists</h3>
                    <div class="loading" v-if="isLoading">
                        <img src="../assets/Loading.gif" />
                    </div>
                <div
                    class="shopping-list"
                    v-for="groupShoppingList in groupShoppingLists"
                    v-bind:key="groupShoppingList.listId"
                    
                >
                <router-link v-bind:to="{ name: 'ShoppingListDetail', params: { id:groupShoppingList.listId } }">
                    {{ groupShoppingList.listName }}             
                </router-link>
                <button class="modify-button" v-on:click="removeShoppingList(groupShoppingList.listId)">Delete</button>
                </div>  
            </div>
            <div id="create-shopping-list">
              <button
              id="create-shopping-list-button"
              v-on:click.prevent="shoppingListForm = true"
              href="#"
              >Create a shopping list</button>
              <form v-on:submit.prevent="createShoppingListInGroup" v-if="shoppingListForm === true">
              <div class="form-element">
                  <label for="listName">List Name:</label>
                  <input id="listName" type="text" v-model="newGroupShoppingList.listName" />
              </div>
              <input type="submit" value="Save" />
              <input type="button" value="Cancel" v-on:click.prevent="resetShoppingListForm" />
              </form>
          </div>            


        </div>
    </div>
</template>

<script>
import ShoppingListService from '../services/ShoppingListService';
import GroupService from '../services/GroupService';


export default {
    data() {
        return {
            shoppingListForm: false,
            groupShoppingLists: [],
            newGroupShoppingList: {},
            isLoading: true
        }
    },
    created() {
        ShoppingListService.viewShoppingListsByGroupId(this.$route.params.id).then(response => {
            this.groupShoppingLists = response.data;
            this.isLoading = false;
        })
    },
    methods: {
    removeShoppingList(id) {
        ShoppingListService.removeShoppingList(id)
            .then(response =>{
                if(response.status === 200) {
                    window.location.reload();
                }
            })
    },
    createShoppingListInGroup() {
    GroupService.createShoppingListInGroup(this.newGroupShoppingList, this.$route.params.id)
      .then(response =>{
                if(response.status === 200) {
                    window.location.reload();
                }
            })
    },
    resetShoppingListForm() {
    this.shoppingListForm = false;
    this.newShoppingList = {};
        
   }

    }
}


</script>
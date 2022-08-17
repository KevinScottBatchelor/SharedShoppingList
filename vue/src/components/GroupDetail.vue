<template>
    <div>
        <div id="main-container">
            <div id="container">
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
                    {{ groupShoppingList.listName  }}
                    <div></div>        
                    Claimed By: {{ groupShoppingList.claimedBy == null ? "No one" : groupShoppingList.claimedBy }}      
                </router-link>
                <button id="claim-button" class="modify-button"
                v-if="groupShoppingList.claimedBy === null || groupShoppingList.claimedBy === currentUsername"  
                v-on:click="setGroupShoppingList(groupShoppingList), 
                claimShoppingList(newGroupShoppingList.listId, newGroupShoppingList)">
                {{groupShoppingList.claimedBy != null ? "Unclaim": "Claim" }}</button>

                <button class="modify-button"
                v-show="groupShoppingList.accountId === currentUserId" 
                v-on:click="removeShoppingList(groupShoppingList.listId)">Delete</button>
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


        <div class="member-lists">
            <h3 id="member-title">Members in group</h3>
                <div class="loading" v-if="isLoading">
                    <img src="../assets/Loading.gif" />
                </div>
            <div
                class="member-list"
                v-for="groupShoppingList in groupShoppingLists"
                v-bind:key="groupShoppingList.listId"
                
            >
               test member             
            </div>  
        </div>
        <div id="invite-user">
            <button
            id="invite-user-button"
            v-on:click.prevent="userForm = true"
            href="#"
            >Invite User</button>
            <form v-on:submit.prevent="createShoppingListInGroup" v-if="userForm === true">
            <div class="form-element">
                <label for="username">username:</label>
                <input id="username" type="text"  />
            </div>
            <input type="submit" value="Save" />
            <input type="button" value="Cancel" v-on:click.prevent="resetUserForm" />
            </form>
        </div>                     

        </div>
        </div>
    </div>
</template>

<script>
import ShoppingListService from '../services/ShoppingListService';
import GroupService from '../services/GroupService';
import store from '../store/index'; 

export default {
    data() {
        return {
            shoppingListForm: false,
            groupShoppingLists: [],
            newGroupShoppingList: {},
            isLoading: true,
            isClaimed: false,
            userForm: false,
            users: [],
            newUser: {},
            currentUsername: store.state.user.username,
            currentUserId: store.state.user.id
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
        
   },
    resetUserForm() {
    this.userForm = false;
    this.newUser = {};
        
   },
   claimShoppingList(id, groupShoppingList) {
    ShoppingListService.claimShoppingList(id, groupShoppingList)
          .then(response =>{
                if(response.status === 200) {
                    if(groupShoppingList.claimedBy != null) {
                        this.isClaimed = true;
                        window.location.reload();
                    } else {
                        this.isClaimed = false;
                        window.location.reload();
                    }
                    
                }
            })
   },
   setGroupShoppingList(groupShoppingList) {
     this.newGroupShoppingList = groupShoppingList;
   },

}
}


</script>


<style scoped>
#container {
    display:grid;
    grid-template-columns: 1fr 1fr 1fr 1fr;
    grid-template-areas: 
                        ". shoppinglist-title  member-title  . "
                        ". shopping-lists member-lists  . "
                        ". create-shopping-list invite-user . ";

}

#shoppinglist-title {
    grid-area: shoppinglist-title;
    text-align: center;
}

#member-title {
    grid-area: member-title;
    text-align: center;
}

.shopping-lists {
  grid-area: shopping-lists;
  display: flex;
  flex-direction: column;
  grid-gap: 20px;
  align-items: center;
}

.member-lists {
  grid-area: member-lists;
  display: flex;
  flex-direction: column;
  grid-gap: 20px;
  align-items: center;
}

#create-shopping-list {
    grid-area: create-shopping-list;
    text-align: center;
}

#invite-user {
    grid-area: invite-user;
    text-align: center;
}


</style>
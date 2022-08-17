<template>
  <div>
    <div id="main-container">
        <div id="container">
            <div class="shopping-lists">
                <h3 id="shoppinglist-title">My Shopping Lists</h3>
                    <div class="loading" v-if="isLoading">
                        <img src="../assets/Loading.gif" />
                    </div>
                <div
                    class="shopping-list"
                    v-for="shoppingList in shoppingLists"
                    v-bind:key="shoppingList.listId"
                    
                >
                <router-link v-bind:to="{ name: 'ShoppingListDetail', params: { id:shoppingList.listId } }">
                    {{ shoppingList.listName }}             
                </router-link>
                <button class="modify-button" v-on:click="removeShoppingList(shoppingList.listId)">Delete</button>
                </div>  
            </div>


            <div class="group-lists">
                <h3 id="group-title">My Groups</h3>            
              <div
                  class="group-list"
                  v-for="group in groups"
                  v-bind:key="group.groupId"
                  
              >
                <router-link v-bind:to="{ name: 'Group', params: { id:group.groupId } }">
                    {{ group.groupName }}             
                </router-link>
                <button class="modify-button" v-on:click="leaveGroup(group.groupId)">Leave group</button>
              </div>
            </div>

            <div class="invite-lists">
                <h3 id="invite-title">My Group Invitations</h3>            
              <div
                  class="invite-list"
                  v-for="invite in invites"
                  v-bind:key="invite.inviteId"               
              >        
                    {{invite.groupName}}
                    <div></div>                                 
                <button 
                href="#"                   
                v-bind:key="invite.inviteId"  
                v-on:click.prevent="inviteCodeForm = true, setNewInvite(invite)">
                
                Accept</button>
                <button v-on:click="reject(group.groupId)">reject</button>
                <div v-if="Correct">{{ CMsg }}</div>
                <div v-if="Error"> {{ EMsg }}</div>
                <div id="invite-code-form">
                  <form v-if="inviteCodeForm === true">
                  <div class="form-element">
                      <label for="inviteCode">Invite Code:</label>
                      <input id="inviteCode" type="text" @input = "inviteCodeInput" v-model="compareCode.compareCode"/>
                  </div>
                  
                  <input type="button" value="Cancel" v-on:click.prevent="resetInviteCodeForm" />
                  </form>
                </div> 

              </div>
            </div>

          <div id="create-shopping-list">
              <button
              id="create-shopping-list-button"
              v-on:click.prevent="shoppingListForm = true"
              href="#"
              >Create a shopping list</button>
              <form v-on:submit.prevent="createShoppingList" v-if="shoppingListForm === true">
              <div class="form-element">
                  <label for="listName">List Name:</label>
                  <input id="listName" type="text" v-model="newShoppingList.listName" />
              </div>
              <input type="submit" value="Save" />
              <input type="button" value="Cancel" v-on:click.prevent="resetShoppingListForm" />
              </form>
          </div>

          <div id="create-group">
              <button
              id="create-group-button"
              v-on:click.prevent="createGroupForm = true"
              href="#"
              >Create a group</button>
              <form v-on:submit.prevent="createGroup" v-if="createGroupForm === true">
              <div class="form-element">
                  <label for="listName">Group Name:</label>
                  <input id="listName" type="text" v-model="newGroup.groupName" />
              </div>
              <input type="submit" value="Save" />
              <input type="button" value="Cancel" v-on:click.prevent="resetCreateGroupForm" />
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
      shoppingLists: [],
      isLoading: true,
      newShoppingList: {},
      shoppingListForm: false,
      groups:[],
      newGroup: {},
      createGroupForm: false,
      invites:[],
      inviteCodeForm: false,
      newInvite: {
        inviteCode: ""
      },
      compareCode:{compareCode:""},
      CMsg: '',
      EMsg: '',
      Error: false,
      Correct: false
      
    };
  },
  created() {
    ShoppingListService.viewShoppingListsByAccountId().then(response => {
        this.shoppingLists = response.data;
        this.isLoading = false;
    }),
    GroupService.viewAllGroupsByUsername(store.state.user.username).then(response => {
    this.groups = response.data;
    this.isLoading = false;
    }),
    GroupService.viewMyInvitations(store.state.user.id).then(response => {
      this.invites = response.data;
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
    viewShoppingListsByAccountId(id) {
        ShoppingListService.viewShoppingListsByAccountId(id).then(response => {
        this.shoppingLists = response.data;
        })
    },
    resetShoppingListForm() {
    this.shoppingListForm = false;
    this.newShoppingList = {};
        
   },
    resetCreateGroupForm() {
    this.createGroupForm = false;
    this.newGroup = {};
        
   },
    resetInviteCodeForm() {
    this.inviteCodeForm = false;
    this.newInviteCode = {};
        
   },
   createShoppingList() {
    ShoppingListService.createShoppingList(this.newShoppingList)
      .then(response =>{
                if(response.status === 200) {
                    window.location.reload();
                }
            })
    },

    createGroup() {
      GroupService.createGroup(this.newGroup)
            .then(response =>{
                if(response.status === 200) {
                    window.location.reload();
                }
            })
    },
    leaveGroup(groupId) {
      GroupService.deleteUserFromGroup(store.state.user.id, groupId)
      .then(response =>{
                if(response.status === 200) {
                    window.location.reload();
                }
            })
    },
    getGroupById(id) {
      let newGroup = GroupService.getGroupByGroupId(id)
            .then(response =>{
                if(response.status === 200) {
                    return newGroup;
                }
            })
    },
    addUserToGroup(groupId, newInvite) {
      
      GroupService.addUserToGroup(groupId, newInvite)
    },
    setNewInvite(invite) {
      this.newInvite = invite;
    },
    updateInvite(newInvite) {
      
      GroupService.updateInviteStatus(newInvite)
    },

    inviteCodeInput: function() {
      if(this.compareCode.compareCode == "") {
        this.Error = false;
        this.Correct = false;
      }
      else if(this.compareCode.compareCode === this.newInvite.inviteCode) {        
        this.updateInvite(this.newInvite) 
        this.addUserToGroup(this.newInvite.groupId, this.newInvite)        
        this.Correct = true;
        this.Error = false;
        this.CMsg = 'Welcome!!!! You will be redirected in 3 seconds'
        console.log("CORRECT")
        setTimeout(() => window.location.reload(), 3000); 
      } else {
        this.Error = true;
        this.EMsg = 'WRONG CODE!!!!'
        console.log("wrong code");
      }
    }

   }    
};
</script>



<style scoped>
#nav {

  display: block;
  position: fixed;
  grid-area: nav;

} 

#container {
    display:grid;
    grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
    grid-template-areas: 
                        ". shoppinglist-title  group-title invite-title . "
                        ". shopping-lists group-lists invite-lists . "
                        ". create-shopping-list create-group . . ";

}

#shoppinglist-title {
    grid-area: shoppinglist-title;
    text-align: center;
}

#group-title {
    grid-area: group-title;
    text-align: center;
}

#invite-title {
    grid-area: invite-title;
    text-align: center;
}

.invite-lists {
  grid-area: invite-lists;
  display: flex;
  flex-direction: column;
  grid-gap: 20px;
  
  align-items: center;
}
.invite-list {
  background-color: #f7fafc;
  border-radius: 10px;
  padding: 40px; 
  margin: 0 20px;
  text-align: center;
}



.group-lists {
  display:flex;
  flex-direction: column;
  grid-area: group-lists;
  grid-gap: 20px;
  
  align-items: center;
    
}

.shopping-lists {
  grid-area: shopping-lists;
  display: flex;
  flex-direction: column;
  grid-gap: 20px;
  
  align-items: center;
}
.shopping-list {
  background-color: #f7fafc;
  border-radius: 10px;
  padding: 40px;
  
  margin: 0 20px;
  text-align: center;
}

.group-list {
  background-color: #f7fafc;
  border-radius: 10px;
  padding: 40px;
  
  margin: 0 20px;
  text-align: center;
}

.loading {
  flex: 3;
}
.board a:link,
.board a:visited {
  color: rgb(243, 243, 243);
  text-decoration: none;
}

#create-shopping-list {
  grid-area: create-shopping-list;
  padding-top: 50px;
  padding-bottom: 50px;
  text-align: center;
}

#create-group {
  grid-area: create-group;
  padding-top: 50px;
  padding-bottom: 50px;
  text-align: center;
}



</style>

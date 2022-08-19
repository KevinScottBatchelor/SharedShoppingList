<template>
  <div>
    <div id="main-container">
        <div id="container">

            <h3 id="shoppinglist-title">My Shopping Lists</h3>
            <div class="shopping-lists">
                
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

            <h3 id="group-title">My Groups</h3>
            <div class="group-lists" >                            
              <div
                  class="group-list"
                  v-for="group in groups"
                  v-bind:key="group.groupId"
                  
              >
                <router-link v-bind:to="{ name: 'Group', params: { id:group.groupId } }">
                    {{ group.groupName }}             
                </router-link>
                <div></div>
                <button class="modify-button" v-on:click="leaveGroup(group.groupId)">Leave group</button>
              </div>
            </div>

            <h3 id="invite-title">My Group Invitations</h3>
            <div class="invite-lists">
                
                <div class="invite-list" v-for="sentInvite in sentInvites"
                  v-bind:key="sentInvite.inviteId"  
                             
                  >  
                   Group Name: {{ sentInvite.groupName }} 
                   <div></div>
                   Invited User: {{ sentInvite.invitedUser }}
                   <div></div>
                   Invitation Code: {{ sentInvite.inviteCode }}
                  </div>     

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
                <button v-on:click="rejectInvite(invite)">Decline</button>
                <div class="cmsg" v-if="invite.inviteId === newInvite.inviteId && Correct">{{ CMsg }}</div>
                <div class="msg" v-if="invite.inviteId === newInvite.inviteId && Error"> {{ EMsg }}</div>
                <div id="invite-code-form">
                  <form v-if="invite.inviteId === newInvite.inviteId && inviteCodeForm === true">
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
              <div class="msg" v-if="tooLong"> {{ LMsg }} </div>
              <form v-on:submit.prevent="createShoppingList" v-if="shoppingListForm === true">
              <div class="form-element">
                  <label for="listName">List Name:</label>
                  <input id="listName" type="text" v-model="newShoppingList.listName" />
              </div>
              <button type="submit" value="Save">Save</button>
              <button type="button" value="Cancel" v-on:click.prevent="resetShoppingListForm">Cancel</button>
              </form>
          </div>

          <div id="create-group">
              
              <button
              id="create-group-button"
              v-on:click.prevent="createGroupForm = true"
              href="#"
              >Create a group</button>
              <div class="msg" v-if="tooLong"> {{ GMsg }} </div>
              <form v-on:submit.prevent="createGroup" v-if="createGroupForm === true">
              <div class="form-element">
                  <label for="listName">Group Name:</label>
                  <input id="listName" type="text" v-model="newGroup.groupName" />
              </div>
              <button type="submit" value="Save">Save</button>
              <button type="button" value="Cancel" v-on:click.prevent="resetCreateGroupForm">Cancel</button>
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
      sentInvites:[],
      inviteCodeForm: false,
      newInvite: {
        inviteCode: ""
      },
      compareCode:{compareCode:""},
      CMsg: '',
      EMsg: '',
      Error: false,
      Correct: false,
      tooLong: false,
      GMsg: "",
      LMsg:"",
      InvitedUsername: []
      
      
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
    }),
    GroupService.viewSentInvitations(store.state.user.id).then(response => {
      this.sentInvites = response.data;
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
    this.tooLong = false;
    this.newShoppingList = {};
        
   },
    resetCreateGroupForm() {
    this.createGroupForm = false;
    this.tooLong = false;
    this.newGroup = {};
        
   },
    resetInviteCodeForm() {
    this.inviteCodeForm = false;
    this.newInviteCode = {};
        
   },
   createShoppingList() {
          if(this.newShoppingList.listName.length > 24) {
            this.tooLong = true;
            this.GMsg=""
            this.LMsg= "List name must be under 25 characters"
          } else  
    ShoppingListService.createShoppingList(this.newShoppingList)
      .then(response =>{
                if(response.status === 200) {
                    window.location.reload();
                }
            })
    },

    createGroup() {
          if(this.newGroup.groupName.length > 24) {
            this.tooLong = true;
            this.LMsg=""
            this.GMsg= "Group name must be under 25 characters"
          } else
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
      } else if(this.compareCode.compareCode.length === 6 && this.compareCode.compareCode != this.newInvite.inviteCode) {
        this.Error = true;
        this.EMsg = 'WRONG CODE!!!!'
        console.log("wrong code");
      }
    },
    rejectInvite(newInvite) {
      
      GroupService.rejectInvite(newInvite.inviteId);
      window.location.reload();
    },
    getUsernameById(id) {

      GroupService.getUsernameByUserId(id)
        .then(response => {
          console.log('test5')
          this.InvitedUsername = response.data;
        })
      
    },
  


   }    
};
</script>



<style scoped>
*{

  font-family: Tahoma, Verdana, Segoe, sans-serif;
  box-sizing: border-box;

}

h3 {
  color: rgb(54, 52, 52);
  text-transform: uppercase;
}

#container {
  padding-top:75px;
  --bg: hsl(0, 100%, 10%);
  --color: rgb(54, 53, 52);
  --underline-width: 100%;
  --underline-block-width: 20px;
  --underline-color: rgba(162, 146, 115, 0);
  --underline-color-hover: rgb(217, 208, 184);
  --underline-transition: 0.5s;
    display:grid;
    grid-column-gap: 40px;
    grid-template-columns: 1fr 2fr 2fr 2fr 1fr;
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
  
  grid-area: shopping-lists;
  grid-area: invite-lists;
  display: flex;
  flex-direction: column;
  grid-gap: 20px;
  border: 1px solid rgb(96, 96, 96);
  padding: 10px;
  border-radius: 12px;
  align-items: center;
  
  border:  none;
  
}
.invite-list {
  background:  linear-gradient( 45deg, rgba(132, 117, 76, 0.6), rgba(255, 255, 255));
  border-radius: 10px;
  padding: 20px; 
  margin: 0 20px;
  width: 90%;
  text-align: center;
  box-shadow:  0px 2px 6px -1px rgba(109, 103, 103, 0.712); 
  border:  none;
  color: rgb(54, 52, 52);
}




.group-lists {

  grid-area: shopping-lists;
  display:flex;
  flex-direction: column;
  grid-area: group-lists;
  grid-gap: 20px;
  border-top: 1px solid rgb(96, 96, 96);
  border-bottom: 1px solid rgb(96, 96, 96);
  padding: 10px;
  border-radius: 12px;
  align-items: center;
  
  border:  none;
    
}

.shopping-lists {

  grid-area: shopping-lists;
  display: flex;
  flex-direction: column;
  grid-gap: 20px;
  border: 1px solid rgb(96, 96, 96);
  padding: 10px;
  border-radius: 12px;
  align-items: center;
  border:  none;
}

.shopping-list {
  background:  linear-gradient( 45deg, rgba(132, 117, 76, 0.6), rgba(255, 255, 255));
  border-radius: 10px;
  padding: 20px;
  margin: 0 20px;
  width: 90%;
  text-align: center;
  box-shadow:  0px 2px 6px -1px rgba(109, 103, 103, 0.712); 
  border:  none;
  
}

.group-list {
  background:  linear-gradient( 45deg, rgba(132, 117, 76, 0.6), rgba(255, 255, 255));
  border-radius: 10px;
  padding: 20px;
  width: 90%;
  margin: 0 20px;
  text-align: center;
  box-shadow:  0px 2px 6px -1px rgba(109, 103, 103, 0.712); 
  border:  none;
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

button {
  width:  auto;
  min-width:  50px;
  border-radius:  12px; 
  text-align:  center;
  margin-top:10px; 
  padding:  5px 20px;  
  color:  rgb(80, 80, 80); 
  background-color: rgba(135, 122, 88, 0.45);
  font-size:  14px; 
  box-shadow:  0px 2px 4px -1px rgba(109, 103, 103, 0.712); 
  border:  none; 
  
}

button:active {
  background-color: rgb(128, 128, 128);
  border: 4px solid rgb(53, 53, 53);
  transition: 0.1s;
  
}

button:hover {
  background-color:  rgb(60, 60, 60);
  color: rgb(217, 217, 217);
  transition: 0.6s;
}

input {

  font-size:  16px; 
  padding:  10px 10px 10px 10px;
  margin: 10px; 
  height:  36px; 
  border:  none; 
  border-radius: 12px;
  border-bottom:  solid 1px rgba(0,0,0,.1); 
  background:  #fff; 
  width:  auto; 
  box-sizing:  border-box; 
  transition:  all .3s linear; 
  color:  #000; 
  font-weight:  400;
  
}

.msg {
  color: rgb(186, 81, 81);
}
.cmsg{
  color: rgb(105, 143, 105);
}



a {
  display:block;
  padding: 5px;
  border-radius: 5px;
  font-size: 16px;
  color: var(--color);
  text-decoration: none;
  background-image: linear-gradient(90deg, var(--bg), var(--bg)),
    linear-gradient(90deg, var(--underline-color), var(--underline-color));
  background-size: var(--underline-block-width) var(--underline-width),
    100% var(--underline-width);
  background-repeat: no-repeat;
  background-position-x: calc(var(--underline-block-width) * -1), 0;
  background-position-y: 100%;
  transition: background-position-x var(--underline-transition);
}

a:hover {
  background-image: linear-gradient(90deg, var(--bg), var(--bg)),
    linear-gradient(
      90deg,
      var(--underline-color-hover),
      var(--underline-color-hover)
    );
  background-position-x: calc(100% + var(--underline-block-width)), 0;
}


</style>

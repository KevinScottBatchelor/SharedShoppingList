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
            <router-link v-bind:to="`/list/${groupShoppingList.listId}`">
              {{ groupShoppingList.listName }}
              <div></div>
              Claimed By: {{ groupShoppingList.claimedBy == null ? "No one" : groupShoppingList.claimedBy }}
            </router-link>
            <button
              id="claim-button"
              class="modify-button"
              v-if="groupShoppingList.claimedBy === null || groupShoppingList.claimedBy === currentUsername"
              v-on:click="setGroupShoppingList(groupShoppingList), 
                claimShoppingList(newGroupShoppingList.listId, newGroupShoppingList)"
            >{{groupShoppingList.claimedBy != null ? "Unclaim": "Claim" }}</button>

            <button
              class="modify-button"
              v-show="groupShoppingList.accountId === currentUserId"
              v-on:click="removeShoppingList(groupShoppingList.listId)"
            >Delete</button>
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
            <button type="submit" value="Save">Save</button>
            <button type="button" value="Cancel" v-on:click.prevent="resetShoppingListForm">Cancel</button>
          </form>
        </div>

        <div class="member-lists">
          <h3 id="member-title"></h3>
          <div class="loading" v-if="isLoading">
            <img src="../assets/Loading.gif" />
          </div>
          <div class="member-list" v-for="user in users" v-bind:key="user.id">{{ user.userId }}</div>
        </div>

        <div id="invite-user">
          <div v-if="inviteStatus">{{ Msg }}</div>
          <button id="invite-user-button" v-on:click.prevent="userForm = true" href="#">Invite User</button>
          <form v-on:submit.prevent="inviteUser" v-if="userForm === true">
            <div class="form-element">
              <label for="username">username:</label>
              <input id="username" type="text" v-model="capturedUsername" />
            </div>
            <button type="submit" value="Send">Send</button>
            <button type="button" value="Cancel" v-on:click.prevent="resetUserForm">Cancel</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ShoppingListService from "../services/ShoppingListService";
import GroupService from "../services/GroupService";
import store from "../store/index";

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
      invite: {
        invitedUser: "",
        fromUser: store.state.user.id,
        groupId: this.$route.params.id
      },
      currentUsername: store.state.user.username,
      currentUserId: store.state.user.id,
      capturedUsername: "",
      Msg: "",
      inviteStatus: false
    };
  },
  created() {
    ShoppingListService.viewShoppingListsByGroupId(this.$route.params.id).then(
      response => {
        this.groupShoppingLists = response.data;
        this.isLoading = false;
      }
    ),
      GroupService.findAllUsers(this.currentUsername).then(response => {
        this.users = response.data;
        this.isLoading = false;
      });
  },
  methods: {
    removeShoppingList(id) {
      ShoppingListService.removeShoppingList(id).then(response => {
        if (response.status === 200) {
          window.location.reload();
        }
      });
    },
    createShoppingListInGroup() {
      GroupService.createShoppingListInGroup(
        this.newGroupShoppingList,
        this.$route.params.id
      ).then(response => {
        if (response.status === 200) {
          window.location.reload();
        }
      });
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
      ShoppingListService.claimShoppingList(id, groupShoppingList).then(
        response => {
          if (response.status === 200) {
            if (groupShoppingList.claimedBy != null) {
              this.isClaimed = true;
              window.location.reload();
            } else {
              this.isClaimed = false;
              window.location.reload();
            }
          }
        }
      );
    },
    setGroupShoppingList(groupShoppingList) {
      this.newGroupShoppingList = groupShoppingList;
    },
    inviteUser() {
      GroupService.getUserIdByUsername(this.capturedUsername).then(response => {
        if (response.status === 200) this.invite.invitedUser = response.data;
        console.log("OK");
        this.$forceUpdate();
        GroupService.inviteUserToGroup(this.invite);
        console.log("FEWJIFJEWIOFJWIEOJF");
        this.inviteStatus = true;
        this.Msg = "Invitation Sent!";
        setTimeout(() => window.location.reload(), 1000);
        window.location.reload();
      });
    },
    getUsernameById() {
      GroupService.getUsernameByUserId();
    }
  }
};
</script>

<style scoped>
* {
  font-family: Tahoma, Verdana, Segoe, sans-serif;
}
h3 {
  color: rgb(54, 52, 52);
  text-transform: uppercase;
}

#container {
  padding-top: 100px;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-template-areas:
    ". shoppinglist-title  . "
    ". shopping-lists  . "
    ". create-shopping-list  . "
    ". invite-user  . ";
  --bg: hsl(0, 100%, 10%);
  --color: rgb(54, 52, 52);
  --underline-width: 100%;
  --underline-block-width: 20px;
  --underline-color: rgba(255, 255, 255, 0);
  --underline-color-hover: rgb(217, 208, 184);
  --underline-transition: 0.5s;
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
  padding-bottom: 20px;
}

.shopping-list {
  background: linear-gradient(
    45deg,
    rgba(132, 117, 76, 0.6),
    rgba(255, 255, 255)
  );
  background-color: linear-gradient(#ad8cea, #50dfb2);
  border-radius: 10px;
  padding: 20px;

  margin: 0 20px;
  width: 60%;
  text-align: center;
  box-shadow: 0px 2px 6px -1px rgba(109, 103, 103, 0.712);
  border: none;
  color: rgb(54, 52, 52);
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

a {
  display: block;
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
<template>
  <div id="main-container">
    <div id="container">
      <div class="loading" v-if="isLoading">
        <img src="../assets/Loading.gif" />
      </div>
      <h3>All ITEMS IN LIST</h3>
      <table id="table" cellspacing="0" cellpadding="0">
        <thead>
          <tr>
            <th>Item Name</th>
            <th>Quantity</th>
            <th>Date Added</th>
            <th>Created By</th>
            <th>Date Modified</th>
            <th>Modified By</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in items" v-bind:key="item.itemId">
            <td>{{ item.itemName }}</td>
            <td>{{ item.quantity }}</td>
            <td>{{ item.dateAdded }}</td>
            <td>{{ item.createdBy }}</td>
            <td>{{ item.dateModified }}</td>
            <td>{{ item.modifiedBy }}</td>

            <div id="modify-form">
              <button
                id="modify-form-button"
                href="#"
                v-bind:key="item.itemId"
                v-on:click.prevent="modifyForm = true, setModifiedItem(item)"
              >Modify</button>
              <button id="delete" class="modify-button" v-on:click="deleteItem(item.itemId)">Delete</button>
              <div id="modify-form">
                <form
                  v-on:submit.prevent="modifyItem"
                  v-if="item.itemId === modifiedItem.itemId && modifyForm === true"
                >
                  <div class="form-element">
                    <label for="Item Name">Item Name:</label>
                    <input id="Item Name" type="text" v-model="modifiedItem.itemName" />
                  </div>
                  <div class="form-element">
                    <label for="Quantity">Quantity:</label>
                    <input id="Quantity" type="number" v-model="modifiedItem.quantity" />
                  </div>
                  <button type="submit" value="Confirm">Confirm</button>
                  <button type="button" value="Cancel" v-on:click.prevent="resetModifyForm">Cancel</button>
                </form>
              </div>
            </div>
          </tr>
        </tbody>
      </table>
      <div id="add-form">
        <button id="add-form-button" href="#" v-on:click.prevent="addForm = true">Add Item</button>
        <form v-on:submit.prevent="addItem" v-if="addForm === true">
          <div class="form-element">
            <label for="Item Name">Item Name:</label>
            <input id="Item Name" type="text" v-model="newItem.itemName" />
          </div>
          <div class="form-element">
            <label for="Quantity">Quantity:</label>
            <input id="Quantity" type="number" v-model="newItem.quantity" />
          </div>
          <button type="submit" value="Save">Save</button>
          <button type="button" value="Cancel" v-on:click.prevent="resetAddForm">Cancel</button>
        </form>
      </div>
      <div id="clear-list" v-bind:key="shoppingList.listId">
        <button class="clear-list-button" v-on:click="clearAList">Clear list</button>
      </div>
    </div>
  </div>
</template>

<script>
import ShoppingListService from "../services/ShoppingListService";
import ItemService from "../services/ItemService";

export default {
  data() {
    return {
      items: [],
      isLoading: true,
      modifyForm: false,
      modifiedItem: {
        itemId: "",
        listId: "",
        itemName: "",
        quantity: "",
        dateAdded: "",
        createdBy: "",
        dateModified: "",
        modifiedBy: ""
      },
      newItem: {
        listId: this.$route.params.id,
        itemName: "",
        quantity: ""
      },
      shoppingList: {
        listId: "",
        listName: "",
        accountId: "",
        claimedBy: ""
      },
      addForm: false
    };
  },
  created() {
    ShoppingListService.viewAllItemsByListId(this.$route.params.id).then(
      response => {
        this.items = response.data;
        this.isLoading = false;
      }
    ),
      ShoppingListService.viewShoppingListByListId(this.$route.params.id).then(
        response => {
          this.shoppingList = response.data;
        }
      );
  },
  methods: {
    deleteItem(id) {
      ItemService.deleteItem(id).then(response => {
        if (response.status === 200) {
          window.location.reload();
        }
      });
    },
    addItem() {
      ItemService.addItem(this.newItem).then(response => {
        if (response.status === 200) {
          window.location.reload();
        }
      });
    },
    resetAddForm() {
      this.addForm = false;
      this.newItem = {};
    },
    resetModifyForm() {
      this.modifyForm = false;
      this.newItem = {};
    },
    modifyItem() {
      ItemService.modifyItem(this.modifiedItem.itemId, this.modifiedItem).then(
        response => {
          if (response.status === 200) {
            window.location.reload();
          }
        }
      );
    },
    setModifiedItem(item) {
      this.modifiedItem = item;
    },
    clearAList() {
      ShoppingListService.clearList(this.$route.params.id).then(response => {
        if (response.status === 200) {
          window.location.reload();
        }
      });
    }
  }
};
</script>

<style>
* {
  font-family: Tahoma, Verdana, Segoe, sans-serif;
}
div#modify-form {
  padding-bottom: 10px;
}

tr {
  color: rgb(59, 55, 42);
  text-transform: uppercase;
}
td:hover {
  background-color: rgba(125, 118, 96, 0.6);
  transition: 0.7s;
  border-radius: 12px;
}

th,
td {
  border-bottom: 1.25px solid rgb(59, 55, 42);
}

table {
  grid-area: table-grid;
  text-align: center;
  padding: 20px;
  margin-bottom: 10px;
  background: linear-gradient(
    45deg,
    rgba(132, 117, 76, 0.6),
    rgba(255, 255, 255)
  );
  border-radius: 12px;
}

.board-actions {
  text-align: center;
  padding: 20px 0;
}
.board-actions a:link,
.board-actions a:visited {
  color: blue;
  text-decoration: none;
}
.board-actions a:hover {
  text-decoration: underline;
}

#container {
  display: grid;
  padding-top: 75px;
  grid-template-columns: 1fr 2fr 2fr 1fr;
  grid-template-areas:
    ". h3 h3 ."
    ". table-grid table-grid ."
    ". add-form . . "
    ". clear-list . .";
}

h3 {
  color: rgb(59, 55, 42);
  grid-area: h3;
}

#add-form {
  grid-area: add-form;
}

#clear-list {
  grid-area: clear-list;
}

button {
  width: auto;
  min-width: 50px;
  border-radius: 12px;
  text-align: center;
  margin-top: 10px;
  margin-right: 5px;
  padding: 5px 20px;
  color: rgb(80, 80, 80);
  background-color: rgba(135, 122, 88, 0.45);
  font-size: 14px;
  box-shadow: 0px 2px 4px -1px rgba(109, 103, 103, 0.712);
  border: none;
}

button:active {
  background-color: rgb(128, 128, 128);
  border: 4px solid rgb(53, 53, 53);
  transition: 0.1s;
}

button:hover {
  background-color: rgb(60, 60, 60);
  color: rgb(217, 217, 217);
  transition: 0.6s;
}

input {
  font-size: 16px;
  padding: 10px 10px 10px 10px;
  margin: 10px;
  height: 26px;
  border: none;
  border-radius: 12px;
  border-bottom: solid 1px rgba(0, 0, 0, 0.1);
  background: #fff;
  width: auto;
  box-sizing: border-box;
  transition: all 0.3s linear;
  color: rgb(48, 48, 48);
  font-weight: 400;
}
</style>
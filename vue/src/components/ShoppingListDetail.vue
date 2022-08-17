<template>
    <div>
        <div class="loading" v-if="isLoading">
            <img src="../assets/Loading.gif" />
        </div>
        <table id="table">
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
                <tr v-for="item in items"
                    v-bind:key="item.itemId">
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
                    </div>
                    <button class="modify-button" v-on:click="deleteItem(item.itemId)">Delete</button>
                </tr>
            </tbody>
        </table>
        <div id="add-form">
            <button
            id="add-form-button"
            href="#"
            v-on:click.prevent="addForm = true"
            >Add Item</button>
            <form v-on:submit.prevent="addItem" v-if="addForm === true">
            <div class="form-element">
                <label for="Item Name">Item Name:</label>
                <input id="Item Name" type="text" v-model="newItem.itemName" />
            </div>
            <div class="form-element">
                <label for="Quantity">Quantity:</label>
                <input id="Quantity" type="text" v-model="newItem.quantity" />
            </div>
            <input type="submit" value="Save" />
            <input type="button" value="Cancel" v-on:click.prevent="resetAddForm" />
            </form>
        </div>

        <div id="modify-form">
        <form v-on:submit.prevent="modifyItem" v-if="modifyForm === true">
        <div class="form-element">
            <label for="Item Name">Item Name:</label>
            <input id="Item Name" type="text" v-model="modifiedItem.itemName" />
        </div>
        <div class="form-element">
            <label for="Quantity">Quantity:</label>
            <input id="Quantity" type="text" v-model="modifiedItem.quantity" />
        </div>
        <input type="submit" value="Confirm" />
        <input type="button" value="Cancel" v-on:click.prevent="resetModifyForm" />
        </form>
        </div>

        <div id="clear-list" v-bind:key="shoppingList.listId">
        <button class="clear-list-button" v-on:click="clearAList">Clear list</button>
        </div>
    </div>
</template>

<script>
import ShoppingListService from '../services/ShoppingListService';
import ItemService from '../services/ItemService';

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
            newItem:{
                listId: this.$route.params.id,
                itemName: "",
                quantity: ""
            },
            shoppingList: {
                listId: "",
                listName: "",
                accountId: ""
            },
            addForm: false
        }

    },
    created() {
        ShoppingListService.viewAllItemsByListId(this.$route.params.id).then(response => {
            this.items = response.data;
            this.isLoading = false;
        }),
        ShoppingListService.viewShoppingListByListId(this.$route.params.id).then(response => {
                this.shoppingList = response.data;
        });
    },
    methods: {
        deleteItem(id) {
            ItemService.deleteItem(id)
                .then(response => {
                    if(response.status === 200) {
                       window.location.reload();
                    }
                })
        },
        addItem() {
            ItemService.addItem(this.newItem).then(response => {
                if (response.status === 200) {
                    window.location.reload();
                }
            }) 
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
            ItemService.modifyItem(this.modifiedItem.itemId, this.modifiedItem).then(response => {
                if(response.status === 200) {
                    window.location.reload();
                }
            })
        },
        setModifiedItem(item) {
            this.modifiedItem = item;
        },
        clearAList() {
            ShoppingListService.clearList(this.$route.params.id, 0).then(response => {
                if(response.status === 200) {
                    window.location.reload();
                }
            })
        }

    },


    
}


</script>


<style>
table {
    padding: 100px;
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
</style>
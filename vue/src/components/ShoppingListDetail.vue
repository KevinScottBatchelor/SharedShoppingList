<template>
    <div>
        <div class="loading" v-if="isLoading">
            <img src="../assets/Loading.gif" />
        </div>
        <table>
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
                </tr>
            </tbody>
        </table>

    </div>
</template>

<script>
import ShoppingListService from '../services/ShoppingListService';

export default {
    data() {
        return {
            items: [],
            isLoading: true
        };
    },
    created() {
        ShoppingListService.viewShoppingListByListId(this.$route.params.id).then(response => {
            this.items = response.data;
            this.isLoading = false;
        });
    }
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
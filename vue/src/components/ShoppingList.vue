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
                </div>  
            </div>
            <div id="group-lists">
                <h3 id="group-title">My Groups</h3>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
import ShoppingListService from '../services/ShoppingListService';


export default {
  data() {
    return {
      shoppingLists: [],
      isLoading: true
    };
  },
  created() {
    ShoppingListService.viewShoppingListsByAccountId().then(response => {
        this.shoppingLists = response.data;
        this.isLoading = false;
    });
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
    grid-template-columns: 1fr 1fr 1fr 1fr;
    grid-template-areas: 
                        ". shoppinglist-title  group-title . "
                        ". shopping-lists group-lists .";

}

#shoppinglist-title {
    grid-area: shoppinglist-title;
}

#group-title {
    grid-area: group-title;
}

#group-lists {
    
    display:flex;
    grid-area: group-lists;
}

.shopping-lists {
  grid-area: shopping-lists;
  display: flex;
  flex-direction: column;
  grid-gap: 20px;
  justify-content: space-between;
  align-items: center;
}
.shopping-list {
  background-color: #f7fafc;
  border-radius: 10px;
  padding: 40px;
  flex: 1;
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
</style>

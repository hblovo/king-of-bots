<template>
  <NavBar/>
  <router-view></router-view>
</template>
<script>
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
import NavBar from "./components/NavBar.vue"
// import $ from "jquery"
//import { useStore } from "vuex"
export default {
  components:{
    NavBar
  },
  setup(){
    //const store = useStore();
        // $.ajax({
        //     url: "http://127.0.0.1:3000/user/bot/add/",
        //     type: "post",
        //     data: {
        //         title: "Bot的标题",
        //         description : "Bot的描述",
        //         content: "Bot的代码",
        //     },
        //     headers: {
        //         Authorization: "Bearer " + store.state.user.token,
        //     },
        //     success(resp) {
        //         console.log(resp);
        //     },
        //     error(resp) {
        //         console.log(resp);
        //     }
        // })
  }
}
</script>
<style>
body{
  /* background-image : url("~@/assets/images/background.png"); */
  background-image:url("~@/assets/images/preview.jpg");
  background-size: cover;
  background-repeat: no-repeat;
}
</style>

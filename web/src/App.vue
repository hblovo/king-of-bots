<template>
  <NavBar/>
  <router-view></router-view>
</template>
<script>
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
import NavBar from "./components/NavBar.vue"
import $ from "jquery"
export default {
  components:{
    NavBar
  },
  setup(){
    $.ajax({
      url:"http://127.0.0.1:3000/user/account/token/",
      type:"post",
      data:{
        username : "hblovo",
        password : "114514",
      },
      success(resp){
        console.log(resp);
      },
      error(resp){
        console.log(resp);
      }
    });
    $.ajax({
      url:"http://127.0.0.1:3000/user/account/info/",
      type:"get",
      headers:{
        Authorization:"Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1NTk3NWRjMTZjYmQ0YWRlYjExOWZkMzk2ZDdiYjYwMSIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTcxNjI4NzM2NiwiZXhwIjoxNzE3NDk2OTY2fQ.xWPi8xwtOA_Wji9L-J8_jkAYQsGr5a9-vyDo16ONbAI"
      },
      success(resp){
        console.log(resp);
      },
      error(resp){
        console.log(resp);
      }
    })
    
  }
}
</script>
<style>
body{
  background-image : url("~@/assets/images/background.png");
  background-size: cover;
}
</style>

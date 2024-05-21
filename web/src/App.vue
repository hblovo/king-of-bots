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
        username : "hblTuT",
        password : "123456",
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
        Authorization:"Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwNzczNjQ2MWNlNzM0YWNhYjY0NmY3ZjYyYTlkM2VlOCIsInN1YiI6IjIiLCJpc3MiOiJzZyIsImlhdCI6MTcxNjI5MzE2NSwiZXhwIjoxNzE3NTAyNzY1fQ.DXBcdNLbAep8UBuTyhFcBI15PBLoAxrFGT-22dssE20"
      },
      success(resp){
        console.log(resp);
      },
      error(resp){
        console.log(resp);
      }
    });
    $.ajax({
      url:"http://127.0.0.1:3000/user/account/register/",
      type:"post",
      data:{
        username : "hblTuT23",
        password : "123456",
        confirmed_password: "123456",
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

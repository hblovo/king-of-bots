<template>
  <NavBar/>
  <router-view></router-view>
</template>
<script>
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
import NavBar from "./components/NavBar.vue"
//import $ from "jquery"
export default {
  components:{
    NavBar
  },
  setup(){
    
    
    // $.ajax({
    //   url:"http://127.0.0.1:3000/user/account/register/",
    //   type:"post",
    //   data:{
    //     username : "hblTuT23",
    //     password : "123456",
    //     confirmed_password: "123456",
    //   },
    //   success(resp){
    //     console.log(resp);
    //   },
    //   error(resp){
    //     console.log(resp);
    //   }
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

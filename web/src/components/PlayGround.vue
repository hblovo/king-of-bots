<template>
    <div class="playground">
       <GameMap></GameMap>
    </div>
    <div class="button-refresh">
      <button type="button" class="btn btn-primary btn-lg" id="refresh-button">刷新</button>
    </div>
  
  </template>
  
  <script>
  import GameMap from "../components/GameMap.vue"
  export default {
        components:{
          GameMap
      }
  }
  document.addEventListener("DOMContentLoaded", function() {
      // 获取刷新按钮元素
      var refreshButton = document.getElementById("refresh-button");
  
      // 添加点击事件监听器
      refreshButton.addEventListener("click", function() {
          // 刷新页面
          location.reload();
      });
  });
  </script>
  
  <style scoped>
  div.playground{
    width:50vw;
    height: 70vh;
    /* background:beige; */
    margin: 40px auto;
  }
  div.button-refresh{
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 40px; 
  }
  </style>
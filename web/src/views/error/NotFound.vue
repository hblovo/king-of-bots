<template>
    <div class="page-404">
      <div class="main-content">
        <router-link to="/pk/" class="btn btn-primary go-home">返回首页</router-link>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: "PageNotFound"
  };
  </script>
  
  <style scoped>
  .page-404 {
    background: url("https://img.lovepik.com/element/40021/7866.png_1200.png") no-repeat center center;
    background-size: cover;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    color: white;
  }
  
  .main-content {
    margin-bottom: 0px;
    border-radius: 10px;
  }
  .go-home {
  position: absolute;
  bottom: 60px; /* 距离底部 60px */
  left: 50%;
  transform: translateX(-50%); /* 水平居中 */
  padding: 15px 30px; /* 增大按钮 */
  font-size: 1.25rem; /* 增大按钮文字 */
}
  </style>
  
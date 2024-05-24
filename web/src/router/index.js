import { createRouter, createWebHistory } from 'vue-router'
import PkIndexView from '../views/pk/PkIndexView'
import RecordIndexView from '../views/record/RecordIndexView'
import RanklistIndexView from '../views/ranklist/RanklistIndexView'
import UserBotIndexView from '../views/user/bot/UserBotIndexView'
import NotFound from "../views/error/NotFound";
import GameOver from "../views/GameOver";
import UserAccountLoginView from "../views/user/account/UserAccountLoginView"
import UserAccountRegisterView from "../views/user/account/UserAccountRegisterView"
import store from "../store/index"
import test from "../views/test"
const routes = [
  {
    path : "/",
    name : "home",
    redirect: "/pk/",
    meta:{
      requestAuth:true,
    }
  },
  {
    path:"/test/",
    name:"test",
    component:test,
  },
  {
    path : "/pk/",
    name : "pk_index",
    component : PkIndexView,
    meta:{
      requestAuth:true,
    }
  },
  {
    path : "/record/",
    name : "record_index",
    component : RecordIndexView,
    meta:{
      requestAuth:true,
    }
  },
  {
    path : "/ranklist/",
    name : "ranklist_index",
    component : RanklistIndexView,
  },
  {
    path : "/user/bot/",
    name : "user_bot_index",
    component : UserBotIndexView,
    meta:{
      requestAuth:true,
    }
  },
  {
    path : "/user/account/login/",
    name : "user_login_index",
    component : UserAccountLoginView,
    meta:{
      requestAuth:false,
    }
  },
  {
    path : "/user/account/register/",
    name : "user_register_index",
    component : UserAccountRegisterView,
    meta:{
      requestAuth:false,
    }
  },
  {
    path : "/404/",
    name : "not_found_index",
    component : NotFound,
    meta:{
      requestAuth:false,
    }
  },
  {
    path:"/pk/gameover/",
    name:"GameOver_index",
    component:GameOver,
    meta:{
      requestAuth:true,
    }
  },
  {
    path: "/:catchAll(.*)", // 匹配所有未匹配的路由
    redirect: "/404", // 重定向到 /404
    meta: {
      requestAuth: false,
    }
  }
  
]

const router = createRouter({
  history: createWebHistory(),
  routes
})
router.beforeEach((to,from,next)=>{
  // let jwt_token = localStorage.getItem("jwt_token");
  // if(jwt_token){
  //   store.dispatch("getinfo",jwt_token);
  //   next();
  // }
  const jwt_token = localStorage.getItem("jwt_token");
  if(jwt_token){
    store.commit('updateToken',jwt_token);
    store.dispatch('getinfo',{
      success(){
        next();
      },
      error(){
        // console.log(store.state.user);
        store.dispatch('logout');
        alert("token无效,请重新登录！");
        next({name:"user_login_index"});
      }
    })
  }else{
    if(to.meta.requestAuth && store.state.user.is_login === false){
      next({name:"user_login_index"});
    }else{
      next();
    }
  }
  // if(to.meta.requestAuth && store.state.user.is_login === false){
  //   next({name:"user_login_index"});
  // }else{
  //   next();
  // }
})
export default router

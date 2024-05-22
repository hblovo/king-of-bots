import { createRouter, createWebHistory } from 'vue-router'
import PkIndexView from '../views/pk/PkIndexView'
import RecordIndexView from '../views/record/RecordIndexView'
import RanklistIndexView from '../views/ranklist/RanklistIndexView'
import UserBotIndexView from '../views/userBot/UserBotIndexView'
import NotFound from "../views/error/NotFound";
import GameOver from "../views/GameOver";
import UserAccountLoginView from "../views/user/account/UserAccountLoginView"
import UserAccountRegisterView from "../views/user/account/UserAccountRegisterView"
import store from "../store/index"
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
    path:"/catchAll(.*)",
    redirect: "/404/",
    meta:{
      requestAuth:false,
    }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})
router.beforeEach((to,from,next)=>{
  if(to.meta.requestAuth && store.state.user.is_login === false){
    next({name:"user_login_index"});
  }else{
    next();
  }
})
export default router

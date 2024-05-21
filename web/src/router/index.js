import { createRouter, createWebHistory } from 'vue-router'
import PkIndexView from '../views/pk/PkIndexView'
import RecordIndexView from '../views/record/RecordIndexView'
import RanklistIndexView from '../views/ranklist/RanklistIndexView'
import UserBotIndexView from '../views/userBot/UserBotIndexView'
import NotFound from "../views/error/NotFound";
import GameOver from "../views/GameOver";
import UserAccountLoginView from "../views/user/account/UserAccountLoginView"
import UserAccountRegisterView from "../views/user/account/UserAccountRegisterView"
const routes = [
  {
    path : "/",
    name : "home",
    redirect: "/pk/",
  },
  {
    path : "/pk/",
    name : "pk_index",
    component : PkIndexView,
  },
  {
    path : "/record/",
    name : "record_index",
    component : RecordIndexView,
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
  },
  {
    path : "/user/account/login/",
    name : "user_login_index",
    component : UserAccountLoginView,
  },
  {
    path : "/user/account/register/",
    name : "user_register_index",
    component : UserAccountRegisterView,
  },
  {
    path : "/404/",
    name : "not_found_index",
    component : NotFound,
  },
  {
    path:"/pk/gameover/",
    name:"GameOver_index",
    component:GameOver,
  },
  {
    path:"/catchAll(.*)",
    redirect: "/404/"
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router

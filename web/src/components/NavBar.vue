<template>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="/" style="font-family: 'Brush Script MT', cursive;">King Of Bots</a>
            <router-link class="navbar-brand" :to="{name : 'home'}"></router-link>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <!-- <router-link :class="route_name == 'pk_index' ? 'nav-link active' : 'nav-link'" :to="{name:'pk_index'}">对战</router-link> -->
                        <router-link class="nav-link" active-class="active" :to="{ name: 'pk_index' }"> 匹配对战</router-link>
                    </li>
                    <li class="nav-item">
                        <!-- <router-link :class="route_name == 'record_index' ? 'nav-link active' : 'nav-link'" :to="{name:'record_index'}">对局列表</router-link> -->
                        <router-link class="nav-link" active-class = "active" :to="{name: 'record_index'}">对局列表</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :class="route_name == 'ranklist_index' ? 'nav-link active' : 'nav-link'" :to="{name:'ranklist_index'}">排行榜</router-link>
                    </li>
                </ul>
                <ul class="navbar-nav" v-if = "$store.state.user.is_login">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            {{$store.state.user.username}}
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <router-link class="dropdown-item" :to="{ name: 'user_bot_index' }">我的Bot</router-link>
                            <li><a class="dropdown-item" href="/user/">个人中心</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="#" @click= "logout">退出登录</a></li>
                        </ul>
                    </li>

                </ul>
                <ul class="navbar-nav" v-else>
                    <li class="nav-item">
                        <router-link class="nav-link" to="/user/account/login/" role="button" aria-expanded="false">
                            登录
                        </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link class="nav-link" to="/user/account/register/" role="button" aria-expanded="false">
                            注册
                        </router-link>
                    </li>
                </ul>

            </div>
        </div>
    </nav>
</template>

<script>
import { useRoute } from 'vue-router';
import { computed } from 'vue';
import {useStore} from 'vuex';
export default{
    setup(){
        const route = useRoute();
        const store = useStore();
        let route_name = computed(() => route.name);
        const logout = ()=>{
            store.dispatch("logout");
        }
        return {
            route_name,
            logout
        }
    }
}
</script>

<style scoped>
</style>
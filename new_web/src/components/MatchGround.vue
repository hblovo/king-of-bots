<template>
    <div class="matchground">
        <div class="row">
            <!-- left -->
            <div class="col-6">
                <div class="user-photo">
                    <img :src="$store.state.user.photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.user.username }}
                </div>
            </div>
            <!-- left -->
            <!-- right -->
            <div class="col-6">
                <div class="user-photo">
                    <img :src="$store.state.pk.opponent_photo" alt="">
                </div>
                <div class="user-username">
                    {{ $store.state.pk.opponent_username }}
                </div>
            </div>
            <!-- right -->
            <div class="col-12" style="text-align:center; padding-top:15vh;" >
                <button @click="click_match" type="button" class="btn btn-warning btn-lg" id="begin-match">{{ match_btn_info }}</button>
            </div>
        </div>
    </div>
</template>

<script>
import {ref} from "vue"
import { useStore } from "vuex";

export default{
    setup(){
        const store = useStore();
        console.log(store.state.pk.status);
        let match_btn_info = ref("开始匹配");
        const click_match = ()=>{
            if(match_btn_info.value === "开始匹配"){
                store.state.pk.socket.send(JSON.stringify({
                    event: "start-matching",
                }));

                match_btn_info.value = "取消匹配";

            }else if(match_btn_info.value === "取消匹配"){
                store.state.pk.socket.send(JSON.stringify({
                    event: "stop-matching",
                }));

                match_btn_info.value = "开始匹配";
            }
        }
        return {
            match_btn_info,
            click_match,
        }
    }
}
</script>

<style scoped>
div.matchground{
    width:60vw;
    height: 70vh;
    margin: 40px auto;
    background-color:rgba(50, 50, 50,0.5);
}
div.user-photo{
    text-align: center;
    padding-top:5vh;
}
div.user-photo>img{
    border-radius:50%;
}
div.user-username{
    color:white;
    text-align: center;
    font-size: 20px;
    font-weight: bold;
    padding-top:2vh;
}
</style>
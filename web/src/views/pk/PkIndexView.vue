<template>
    <PlayGround v-if="$store.state.pk.status === 'playing'"/>
    <MatchGround v-if="$store.state.pk.status === 'matching'"/>
    <div>
        <CountTime v-if="showCountTime">

        </CountTime>
    </div>
</template>
<script>
//import ContentField from "../../components/ContentField.vue"
import PlayGround from "../../components/PlayGround.vue"
import MatchGround from "../../components/MatchGround.vue"
import { onMounted,onUnmounted } from "vue";
import { useStore } from "vuex";
import CountTime from "../pk/CountTime.vue";
import { ref } from "vue";
export default {
    components:{
        PlayGround,
        MatchGround,
        CountTime,
    },
    setup(){
        const store = useStore();
        const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.token}/`;
        let socket = null;
        const showCountTime = ref(false);

        onMounted(()=>{
            store.commit("updateOpponent",{
                username:"搜索对手中……",
                photo:"https://tse3-mm.cn.bing.net/th/id/OIP-C.5GSkNFdv9SAzlW6FtwY9HQAAAA?w=153&h=176&c=7&r=0&o=5&dpr=1.5&pid=1.7",
            });
            socket = new WebSocket(socketUrl);
            socket.onopen = ()=>{
                console.log("连接成功");
                store.commit("updateSocket",socket);
            }
            socket.onmessage = (msg)=>{
                console.log(msg);
                const data = JSON.parse(msg.data);
                if(data.event === "match-success"){
                    store.commit("updateOpponent",{
                        username:data.opponent_username,
                        photo:data.opponent_photo,
                    });
                    showCountTime.value = true;
                    setTimeout(()=>{
                        store.commit("updateStatus","playing");
                    },4000);
                }
            }
            socket.onclose = ()=>{
                console.log("连接关闭");
            }
        });
        onUnmounted(()=>{
            socket.close();
        });

        return{
            showCountTime,
        }
    }
}
</script>
<style scoped>
    
</style>
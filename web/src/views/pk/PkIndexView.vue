<template>
    <PlayGround v-if="$store.state.pk.status === 'playing'"/>
    <MatchGround v-if="$store.state.pk.status === 'matching'"/>
    <ResultBoard v-if="$store.state.pk.loser != 'none'" />
    <div>
        <CountTime v-if="showCountTime">

        </CountTime>
    </div>
</template>
<script>
//import ContentField from "../../components/ContentField.vue"
import PlayGround from "../../components/PlayGround.vue"
import MatchGround from "../../components/MatchGround.vue"
import ResultBoard from "../../components/ResultBoard.vue"
import { onMounted,onUnmounted } from "vue";
import { useStore } from "vuex";
import CountTime from "../pk/CountTime.vue";
import { ref } from "vue";

export default {
    components:{
        PlayGround,
        MatchGround,
        ResultBoard,
        CountTime,
    },
    setup(){ 
        const store = useStore();
        const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.token}/`;
        let socket = null;
        const showCountTime = ref(false);

        onMounted(()=>{
            store.commit("updateOpponent",{
                username:"我的对手",
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

                    store.commit("updateGame",data.game);
                }else if(data.event === "move"){
                    const game = store.state.pk.gameObject;
                    const [snake0,snake1] = game.snakes;
                    snake0.set_direction(data.a_direction);//在Player类中查看
                    snake1.set_direction(data.b_direction);
                }else if(data.event === "result"){
                    const game = store.state.pk.gameObject;
                    const [snake0,snake1] = game.snakes;
                    if (data.loser === "all" || data.loser === "A") {
                        snake0.status = "die";
                    }
                    if (data.loser === "all" || data.loser === "B") {
                        snake1.status = "die";
                    }
                    store.commit("updateLoser",data.loser);

                }
            }
            socket.onclose = ()=>{
                console.log("连接关闭");
            }
        });
        onUnmounted(()=>{
            socket.close();
            store.commit("updateStatus","matching");
        });

        return{
            showCountTime,
        }
    }
}
</script>
<style scoped>
    
</style>
<template>
    <div class="container">
        <div class="row">
            <div class="col-3">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-body">
                        <img :src="$store.state.user.photo" alt="" style="width: 100%;">
                        <div class = "card-header" style="text-align: center;">
                            <span style="font-size: 130%;">{{ $store.state.user.username }}</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-9">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-header">
                        <span style="font-size: 130%;">我的Bot</span>
                        <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal" data-bs-target="#add-bot-btn">
                            创建Bot
                        </button>
                        <!-- Modal 可以放在任意地方-->
                        <div class="modal fade" id="add-bot-btn" tabindex="-1">
                            <div class="modal-dialog modal-xl">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5 w-100 text-center" id="addBotLabel">
                                            <i class="bi bi-robot"></i> 添加你的Bot
                                        </h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <!-- Forms 表单-->
                                        <div class="mb-3">
                                            <label for="add-bot-title" class="form-label">Bot名称</label>
                                            <!-- <div class="error-message">{{botadd.error_message}}</div> -->
                                            <input v-model="botadd.title" type="text" class="form-control" id="add-bot-title" placeholder="请输入Bot名称……">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-bot-description" class="form-label">Bot简介</label>
                                            <!-- <div class="error-message">{{ botadd.error_message }}</div> -->
                                            <input v-model="botadd.description" type="email" class="form-control" id="add-bot-description" placeholder="请输入Bot描述……">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-bot-code" class="form-label">Bot代码</label>
                                            <textarea v-model="botadd.content" class="form-control" id="add-bot-code" rows="15"></textarea>
                                        </div>
                                        <!-- Forms 表单-->
                                    </div>
                                    <div class="modal-footer d-flex justify-content-center">
                                        <div class="error-message">{{ botadd.error_message }}</div>
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" style="margin-right: 15px;">取消</button>
                                        <button type="button" class="btn btn-primary" @click="add_bot">创建</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Modal -->
                    </div>
                    <div class = "card-body">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>Bot名称</th>
                                    <th>Bot描述</th>
                                    <th>Bot创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="bot in bots" :key="bot.id">
                                    <td>{{bot.title}}</td>
                                    <td>{{bot.description}}</td>
                                    <td>{{bot.createtime}}</td>
                                    <td>
                                        <button type="button" class="btn btn-secondary" style="margin-right: 20px;">修改</button>
                                        <!-- data-bs-toggle="modal" data-bs-target="#deleteBot" -->
                                        <!-- @click="remove_bot(bot)" -->
                                        <button type="button" class="btn btn-danger" data-bs-toggle="modal" @click="select_bot(bot)" data-bs-target="#deleteBot">
                                            删除
                                        </button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 模态框   信息删除确认 -->
    <div class="modal fade" id="deleteBot">
        <div class="modal-dialog">
            <div class="modal-content message_align">
                <div class="modal-header">
                    <h4 class="modal-title">提示</h4>
                </div>
                <div class="modal-body">
                    <p>您确认要删除该条信息吗？</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" @click="closeModal">取消</button>
                    <button type="button" class="btn btn-primary" @click="remove_bot()">确认</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 模态框   信息删除确认 -->
</template>

<script>
import {ref,reactive} from 'vue'
import $ from "jquery"
import {useStore} from 'vuex'
import { Modal } from 'bootstrap/dist/js/bootstrap'
export default {
    setup(){
        const store = useStore();
        //刷新列表
        let bots = ref([]);
        let selectBot = ref();
        const botadd = reactive({
            title:"",
            description:"",
            content:"",
            error_message:"",
        });
        const closeModal = () => {
            Modal.getInstance("#deleteBot").hide();
        }
        const refresh_bots = ()=>{
            // $.ajax({
            //     url:"http://127.0.0.1:3000/user/bot/getlist/",
            //     type:"get",
            //     headers:{
            //         Authorization:"Bearer" + store.state.user.token,
            //     },
            //     success(resp){//resp返回的就是一个list
            //         bots.value = resp;
            //     }
            // })
                $.ajax({
                url: "http://127.0.0.1:3000/user/bot/getlist/",
                type: "get",
                
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);
                    bots.value = resp;
                },
                error(resp) {
                    console.log(resp);
                }
            })
        }

        refresh_bots();
        const add_bot = ()=>{
            botadd.error_message = "";
            $.ajax({
                url:"http://127.0.0.1:3000/user/bot/add/",
                type:"post",
                data:{
                    title:botadd.title,
                    description:botadd.description,
                    content:botadd.content,
                },
                headers:{
                    Authorization:"Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        botadd.title = "";
                        botadd.description = "";
                        botadd.content = "";
                        refresh_bots();
                        Modal.getInstance("#add-bot-btn").hide();
                        
                    } else {
                        botadd.error_message = resp.error_message;
                    }
                }

            })
        }
        const remove_bot = ()=>{
            $.ajax({
                url:"http://127.0.0.1:3000/user/bot/remove/",
                type:"post",
                data:{
                    bot_id : selectBot.value.id
                },
                headers:{
                    Authorization:"Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);
                    if (resp.error_message === "Success") {
                        Modal.getInstance("#deleteBot").hide();
                        refresh_bots();
                    } 
                }

            })
        }
        const select_bot = (bot)=>{
            selectBot.value =  bot;
        }
        return{
            bots,
            botadd,
            add_bot,
            remove_bot,
            closeModal,
            select_bot,
            selectBot,
        }
    }
}
</script>

<style scoped> 
div.error-message{
    color:red;
}
</style>

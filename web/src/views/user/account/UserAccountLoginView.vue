<template>
    <ContentField>
        <div class="row justify-content-center">
            <div class="col-3">
                <h3 class="custom-title text-center mb-4 ">用户登录</h3>
                <form @submit.prevent="login">
                    <div class="mb-3">
                        <label for="username" class="form-label">用户名</label>
                        <div class="input-group">
                        <div class="input-group-text"><i class="bi bi-person"></i></div>
                        <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">密码</label>
                        <div class="input-group">
                        <div class="input-group-text"><i class="bi bi-lock"></i></div>
                        <input v-model="password" type="password" class="form-control" id="password" placeholder="请输入密码">
                        </div>
                    </div>
                    <div class="error-message">{{ error_message }}</div>
                    <button type="submit" class="btn btn-primary justify-content-center">提交</button>
                </form>
            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from "../../../components/ContentField.vue"
import {useStore} from "vuex"
import {ref} from 'vue'
import router from "../../../router/index"
export default {
    components:{
        ContentField
    },
    setup(){
        const store = useStore();
        let username = ref("");
        let password = ref("");
        let error_message = ref("");
        const login = ()=>{
            error_message.value = "";
            store.dispatch("login",{
                username:username.value,
                password:password.value,
                success(){
                    store.dispatch("getinfo",{
                        success(){
                            router.push({name : "home"});
                            console.log(store.state.user);
                        },
                        error(){
                            
                        }
                    })
                    router.push({name:"home"})
                },
                error(resp){
                    if(resp.error_message == "账号不存在，请先注册一个账号"){
                        error_message.value = "账号不存在，请进行注册";
                    }
                    else{
                        error_message.value = "用户名或密码错误";
                    }
                }
            })
        }
        return {
            username ,
            password ,
            error_message,
            login,
        }
    }
}
</script>
<style scoped>
button{
    width:100%;
}
div.error-message{
    color:red;
}
.custom-title {
        font-family: 'Arial', sans-serif;
        font-size: 24px;
        font-weight: bold;
        color: #333; /* 你可以根据需要更改颜色 */
    }
.myser{
    background: url('../../../assets/images/user.jpg') no-repeat;
    width: 100%;
    padding: left 20px;;
}
</style>
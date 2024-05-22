<template>
    <ContentField>
        <div class="row justify-content-center">
            <div class="col-3">
                <form @submit.prevent="register">
                    <div class="mb-3">
                    <label for="username" class="form-label">用户名</label>
                    <input v-model = "username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                    </div>
                    <div class="mb-3">
                    <label for="password" class="form-label">密码</label>
                    <input v-model = "password" type="password" class="form-control" id="password" placeholder="请输入密码">
                    </div>
                    <div class="mb-3">
                    <label for="password" class="form-label">密码</label>
                    <input v-model = "confirmed_password" type="password" class="form-control" id="confirmed_password" placeholder="请再次输入密码">
                    </div>
                    <div class = "error-message">{{error_message}}</div>
                    <button type="submit" class="btn btn-primary justify-content-center">提交</button>
                </form>
            </div>
        </div>
    </ContentField>
</template>
<script>
import ContentField from "@/components/ContentField.vue"
import {ref} from 'vue'
import router from "../../../router/index"
import $ from 'jquery'
export default {
    components:{
        ContentField
    },
    setup(){
        let username = ref('');
        let password = ref('');
        let confirmed_password = ref('');
        let error_message = ref('');
        
        const register = ()=>{
            $.ajax({
                url:"http://127.0.0.1:3000/user/account/register/",
                type:"post",
                data:{
                    username:username.value,
                    password:password.value,
                    confirmed_password:confirmed_password.value,

                },
                
                success(resp){
                    console.log(resp);
                    if(resp.error_message === 'success'){
                        router.push({name :"user_login_index"});
                    }else{
                        error_message.value = resp.error_message;
                    }
                },
            });
        }
        return {
            username,
            password,
            confirmed_password,
            error_message,
            register,
        }
    }
}
</script>
<style scoped>
    button{
        width:100%;
    }
    div.error-message{
        color : red;
    }
</style>
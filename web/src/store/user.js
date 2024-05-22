import $ from 'jquery';
export default ({
  state: {
    id:"",
    username:"",
    photo:"",
    token:"",
    is_login:false,
  },
  //用来定义修改 state 的方法
  mutations: {
    updateUser(state, user){
        state.id = user.id;
        state.username = user.username;
        state.photo = user.photo;
        state.is_login = user.is_login;
    },
    updateToken(state, token){
        state.token = token;
    },
    logout(state){
        state.id = "";
        state.username = "";
        state.photo = "";
        state.is_login = false;
    }
  },
  actions: {
    login(context,data){
        $.ajax({
            url:"http://127.0.0.1:3000/user/account/token/",
            type:"post",
            data:{
              username : data.username,
              password : data.password,
            },
            success(resp){
                if(resp.error_message === "success"){
                    localStorage.setItem("jwt_token",resp.token);
                    context.commit("updateToken",resp.token);
                    data.success(resp);
                }else{
                    data.error(resp);
                }
            },
            error(resp){
              console.log(resp);
            }
          });
    },
    getinfo(context,data){
        $.ajax({
            url:"http://127.0.0.1:3000/user/account/info/",
            type:"get",
            async:false,
            headers:{
              Authorization:"Bearer " + context.state.token,
            },
            success(resp){
                if(resp.error_message === "success"){
                    context.commit("updateUser",{
                        ...resp,
                        is_login:true,
                    });
                    data.success(resp);
                }else{
                    data.error(resp);
                }
                
            },
            error(resp){
              data.error(resp);
            }
          });
    },
    logout(context){
        localStorage.removeItem("jwt_token");
        context.commit("logout");
    }
  },
  modules: {
  }
})
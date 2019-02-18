
var app = new Vue({
    el:"#register",
    data:{
        username:'',
        password:'',
        repeatPassword:''
    }
    ,
    methods:{
        register:function () {

            if (this.username === ''){
                alert("请输入用户名");
                return;
            }

            if (this.password === ''){
                alert("请输入密码");
                return;
            }


            if (this.password === this.repeatPassword){
                common.ajax.put("/user/register",function (response) {
                    if (response.success){
                        alert(response.data);
                        window.location="/";
                    }else{
                        alert(response.msg);
                    }
                },{
                    username:this.username,
                    password:hex_md5(this.password)
                });
            }else{
                alert("两次输入的密码不一致");
            }
        }
    }
});



var app = new Vue({
   el:"#login",
   data:{
       username:'',
       password:''
   }
   ,
    methods:{
       login:function () {

           if (this.username === ''){
               alert("请输入用户名");
               return;
           }

           if (this.password === ''){
               alert("请输入密码");
               return 0;
           }

           common.ajax.post(common.data.loginUrl,function (response) {
               if (response.success){
                   window.location="/";
               }else{
                   alert(response.msg);
               }
           },{
               username:this.username,
               password:hex_md5(this.password)
           });

       }
    }
});
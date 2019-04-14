
var app = new Vue({
   el:"#nav",
   data:{
       username:'未登录',nickName:'',profile:'',hasLogin:false
   }
   ,
    created:function () {

       this.getState();

    }
   ,
    methods:{
       getState:function () {
           var that = this;
           common.ajax.get(common.data.getCurrentUserDataUrl,function (response) {
               if (response.data == null){
                   that.username = '未登录';

               }else{
                   that.username = response.data.username;
                   that.nickName= response.data.nickName;
                   that.profile= response.data.profile;
                   that.hasLogin = true;
               }
               if (localStorage.getItem('username') != null){
                   if (!that.hasLogin){
                       window.location = "/login.html";
                   }

               }
           });
       }
       ,
        logout:function () {

           common.ajax.get(common.data.logoutUrl,function (res) {
               window.location = "";
           })
        }
    }
});


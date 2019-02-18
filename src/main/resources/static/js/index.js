
var app = new Vue({
   el:"#nav",
   data:{
       username:'未登录',nickName:'',profile:''
   }
   ,
    created:function () {

       this.getState();
    }
   ,
    methods:{
       getState:function () {
           var that = this;
           common.ajax.get("/user/state",function (response) {
               if (response.data == null){
                   that.username = '未登录';
               }else{
                   that.username = response.data.username;
                   that.nickName= response.data.nickName;
                   that.profile= response.data.profile;
               }
           });
       }
       ,
        logout:function () {

           common.ajax.get("/user/logout",function (res) {
               window.location = "";
           })
        }
    }
});


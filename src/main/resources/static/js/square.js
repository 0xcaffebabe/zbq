
var square = new Vue({
   el:"#square",
   data:{
       content:'',
       broadcastList:[],
       userList:[],
       self:{}
   }
   ,
    created:function () {

       this.getBroadcast();


        moment.locale("zh-cn");

       if (WebSocket){

           var host = "127.0.0.1";
           var protocol = "ws";

           host = location.host;

           if (host != "127.0.0.1"){
               protocol = "wss";
           }

           var ws = new WebSocket(protocol+"://"+host+"/socket");

           ws.onopen = function (ev) {
               console.log("连接打开:",ev);
               setInterval(function () {
                  ws.send("heartBeat");
               },10000);
           }

           var that = this;
           ws.onmessage = function (ev) {
               console.log("消息到达：",ev);
               var obj = JSON.parse(ev.data);
               if (obj instanceof Array){
                   that.userList = obj;
               }else{

                   // obj.createTime = moment(obj.createTime).fromNow();
                   that.broadcastList = that.broadcastList.concat([obj]);
                   setTimeout(function () {
                       var div = document.getElementById('msgPane');
                       div.scrollTop = div.scrollHeight;
                   },500);
               }


           };


           ws.onclose = function (ev) {
               console.log("ws关闭",ev);
               alert("与服务器连接断开");
           }


       }else{
           alert("您的浏览器不支持webScocket，请升级到最新版");
       }

       this.getSelf();

    }
   ,
    methods:{
       broadcast:function () {

           if (this.content){

               var that = this;
               common.ajax.put(common.data.sendBroadcastUrl,function (r) {
                   if (r.success){
                       that.content = '';
                   }else {
                       alert(r.msg);
                   }
               },{content:this.content})

           }else{
               alert("发送内容不得为空");
           }
       }
       ,
        getBroadcast:function () {

           var that = this;
           common.ajax.get(common.data.getBroadcastListUrl,function (r) {
               if (r.success){
                   var list = r.data;

                   for (var i =0;i<list.length;i++){
                       // list[i].createTime = moment(list[i].createTime).fromNow();
                   }

                   that.broadcastList = list;
                   setTimeout(function () {
                       var div = document.getElementById('msgPane');
                       div.scrollTop = div.scrollHeight;
                   },500);
               }else {
                   alert(r.msg);
               }
           })
        }
        ,
        getSelf:function () {

           var that = this;
           common.ajax.get(common.data.getCurrentUserDataUrl,function (r) {
               if (r.success){
                   that.user = r.data;
               }else{
                   alert(r.msg);
               }
           })
        }
        ,
        timeConvert:function (time) {
            return moment(time).fromNow();
        }

    }
});
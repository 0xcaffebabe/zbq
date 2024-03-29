

var chat = new Vue({
    el: "#chat",
    data: {
        messageList: [],
        friendId:1,
        content:'',
        userMessageList:[],
        nickName:''
    }
    ,
    created:function () {

        this.friendId = location.pathname.replace("/chat/","");
        moment.locale("zh-cn");
        this.getMessageList();
        this.getUnreadMessageList();
        this.getNickName();
    }
    ,
    methods: {

        getMessageList:function () {


            var that = this;
            common.ajax.get(common.data.getMessageListUrl+this.friendId,function (response) {

                if (response.success){

                    var list = response.data;

                    for (var i = 0;i<list.length;i++){
                        list[i].sendTime = moment(list[i].sendTime).fromNow();
                    }
                    that.messageList = list;


                    setTimeout(function () {
                        var div = document.getElementById('msgPane');
                        div.scrollTop = div.scrollHeight;
                    },500);



                }else{
                    alert("获取对话消息失败:"+response.msg);
                }
            })
        }
        ,
        sendMessage:function () {

            if (this.content === ''){
                alert("请输入待发送的内容");
                return;
            }else{
                var that =this;
                common.ajax.post(common.data.sendMessageUrl,function (response) {
                    if (response.success){
                        that.content = '';
                        that.getMessageList();
                    }else{
                        alert(response.msg);
                    }
                },{to:this.friendId,content:this.content});



            }


        }
        ,
        getUnreadMessageList:function () {

            var that = this;
            common.ajax.get(common.data.getUserMessageListUrl,function (response) {
                if (response.success){
                    that.userMessageList = response.data;
                }else{
                    alert("获取未读消息列表失败:"+response.msg);
                }
            });
        }
        ,
        chat:function (id) {
            window.location = "/chat/"+id;
        }
        ,
        getNickName:function () {
            var that = this;
            common.ajax.get(common.data.getNickNameUrl+this.friendId,function (r) {
                if (r.success){
                    that.nickName = r.data;
                }else{
                    alert(r.msg);
                }
            })
        }
    }
});
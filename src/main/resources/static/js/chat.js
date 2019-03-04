var chat = new Vue({
    el: "#chat",
    data: {
        messageList: []
    }
    ,
    created:function () {

        this.getMessageList();
    }
    ,
    methods: {

        getMessageList:function () {

            var id = location.pathname.replace("/chat/","");
            var that = this;
            common.ajax.get(common.data.getMessageListUrl+id,function (response) {

                if (response.success){

                    that.messageList = response.data;
                }else{
                    alert("获取对话消息失败:"+response.msg);
                }
            })
        }
    }
});
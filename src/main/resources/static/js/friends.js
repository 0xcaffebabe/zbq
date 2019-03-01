var friends = new Vue({
    el: "#friends",
    data: {
        friendList: [],
        recommendFriendList:[],
        friendAddList:[],
        friendSearch:'',strangerSearch:'',validMsg:'',toUser:''
    },
    created: function () {

        this.getFriendList();
        this.getRecommendFriendList();
        this.getFriendAddList();
    },
    watch:{
        friendSearch:function () {
            if (this.friendSearch === ''){
                this.getFriendList();
            }
        },
        strangerSearch:function () {
            if (this.strangerSearch === ''){
                this.getRecommendFriendList();
            }
        }
    },
    methods: {
        getFriendList: function () {
            var that = this;
            common.ajax.get(common.data.getFriendListUrl, function (response) {
                if (response.success) {
                    that.friendList = response.data;
                } else {
                    alert("获取好友列表失败:" + response.msg);
                }
            },{kw:this.friendSearch});
        },
        getRecommendFriendList:function () {
            var that = this;
            common.ajax.get(common.data.getRecommendFriendListUrl,function (response) {
                if (response.success) {
                    that.recommendFriendList = response.data;
                } else {
                    alert("获取推荐列表失败:" + response.msg);
                }
            },{kw:this.strangerSearch});
        },
        getFriendAddList:function () {

            var that = this;
            common.ajax.get(common.data.getFriendAddListUrl,function (response) {
               if (response.success){
                   that.friendAddList = response.data;
               }else{
                   alert("获取验证消息失败:"+response.msg);
               }
            });
        }
        ,
        searchFriend:function () {

            this.getFriendList();
        }
        ,
        searchStranger:function () {

            this.getRecommendFriendList();
        },
        showFriendAddDialog:function (event) {
            this.toUser = event.srcElement.dataset.to;
        },
        addFriend:function () {


            if (this.validMsg.length > 64){
                alert("验证消息长度不得大于64！");
            }else{
                common.ajax.put(common.data.addFriendUrl,function (response) {
                    if (response.success){
                        alert(response.data);
                    }else{
                        alert(response.msg);
                    }
                    $("#staticModal").modal("hide");
                },{
                    toUser:this.toUser,msg:this.validMsg
                });
            }
        }
    }
});
var friends = new Vue({
    el: "#friends",
    data: {
        friendList: [],
        recommendFriendList:[],
        friendAddList:[],
        friendSearch:'',
        strangerSearch:'',
        validMsg:'',
        toUser:'',
        friendPage:1,
        friendQuantity:0,
        strangerPage:1,
        strangerQuantity:10,
        strangerButtonEnable:true
    },
    created: function () {

        this.getFriendList();
        this.getRecommendFriendList();
        this.getFriendAddList();
        this.countFriends();
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
                this.strangerButtonEnable=true;

            }else{
                this.strangerButtonEnable=false;
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
            },{kw:this.friendSearch,page:this.friendPage,length:5});
        },
        getRecommendFriendList:function () {
            var that = this;
            common.ajax.get(common.data.getRecommendFriendListUrl,function (response) {
                if (response.success) {
                    if (response.data.length == 0){
                        alert("没有数据！");
                        that.strangerPage--;
                    }else{
                        that.recommendFriendList = response.data;
                    }

                } else {
                    alert("获取推荐列表失败:" + response.msg);
                }
            },{kw:this.strangerSearch,page:this.strangerPage,length:5});
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
        countFriends:function () {

            var that = this;
            common.ajax.get(common.data.countFriendsUrl,function (response) {
                if (response.success){

                    that.friendQuantity = response.data;
                }else{

                    alert(response.msg);
                }
            })
        }
        ,
        friendNextPage:function () {
            if (this.friendPage * 5 >= this.friendQuantity) {
                alert("当前已是最后一页");
            }else{
                this.friendPage++;
                this.getFriendList();
            }
        }
        ,
        friendPrevPage:function () {

            if (this.friendPage <= 1){
                alert("当前一是第一页");
            }else{
                this.friendPage--;
                this.getFriendList();
            }
        }
        ,
        strangerNextPage:function () {
            this.strangerPage++;
            this.getRecommendFriendList();
        }
        ,
        strangerPrevPage:function () {

            if (this.strangerPage <=1){
                alert("已经是第一页");
            }else{
                this.strangerPage--;
                this.getRecommendFriendList();
            }
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
        ,
        agreeFriendAdd:function (event) {
            var friendAddId = event.srcElement.dataset.id;
            var that = this;
            common.ajax.post(common.data.agreeFriendAddUrl+friendAddId,function (response) {
                if (response.success){
                    alert(response.data);
                    that.getFriendAddList();
                    that.getFriendList();
                    that.getRecommendFriendList();
                }else{
                    alert(response.msg);
                }
            });

        }
        ,
        chat:function (event) {
            var friendAddId = event.srcElement.dataset.id;
            window.location="/chat/"+friendAddId;
        }
    }
});
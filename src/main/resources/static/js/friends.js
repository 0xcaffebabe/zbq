var friends = new Vue({
    el: "#friends",
    data: {
        friendList: [],
        recommendFriendList:[]
    },
    created: function () {
        this.getFriendList();
        this.getRecommendFriendList();
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
            })
        },
        getRecommendFriendList:function () {
            var that = this;
            common.ajax.get(common.data.getRecommendFriendListUrl,function (response) {
                if (response.success) {
                    that.recommendFriendList = response.data;
                } else {
                    alert("获取推荐列表失败:" + response.msg);
                }
            })
        }
    }
});
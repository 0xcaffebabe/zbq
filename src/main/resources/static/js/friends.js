var friends = new Vue({
    el: "#friends",
    data: {
        friendList: [],
        recommendFriendList:[],
        friendSearch:'',strangerSearch:''
    },
    created: function () {

        this.getFriendList();
        this.getRecommendFriendList();
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
        }
        ,
        searchFriend:function () {

            this.getFriendList();
        }
        ,
        searchStranger:function () {

            this.getRecommendFriendList();
        },
        addFriend:function (event) {
            console.log(event.srcElement.dataset.to);

        }
    }
});
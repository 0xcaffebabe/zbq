var friends = new Vue({
    el: "#friends",
    data: {
        friendList: [],
        recommendFriendList: [],
        friendAddList: [],
        strangerList: [],
        friendSearch: '',
        strangerSearch: '',
        validMsg: '',
        toUser: '',
        friendPage: 1,
        friendQuantity: 0,
        strangerPage: 1,
        strangerQuantity: 10,
        strangerButtonEnable: true
    },
    created: function () {

        this.getFriendList();
        this.getRecommendFriendList();
        this.getFriendAddList();
        this.countFriends();
    },
    watch: {
        friendSearch: function () {
            if (this.friendSearch === '') {
                this.getFriendList();
            }
        },
        strangerSearch: function () {
            if (this.strangerSearch === ""){
                this.strangerList = [];
            }
            this.strangerPage = 1;
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
            }, {kw: this.friendSearch, page: this.friendPage, length: 5});
        },
        getRecommendFriendList: function () {
            var that = this;
            common.ajax.get(common.data.getRecommendFriendListUrl, function (response) {
                if (response.success) {
                    if (response.data.length == 0) {
                        alert("没有数据！");

                    } else {
                        that.recommendFriendList = response.data;
                    }

                } else {
                    alert("获取推荐列表失败:" + response.msg);
                }
            });
        },
        getFriendAddList: function () {
            var that = this;
            common.ajax.get(common.data.getFriendAddListUrl, function (response) {
                if (response.success) {
                    that.friendAddList = response.data;
                } else {
                    alert("获取验证消息失败:" + response.msg);
                }
            });
        }
        ,
        countFriends: function () {

            var that = this;
            common.ajax.get(common.data.countFriendsUrl, function (response) {
                if (response.success) {

                    that.friendQuantity = response.data;
                } else {

                    alert(response.msg);
                }
            })
        }
        ,
        friendNextPage: function () {
            if (this.friendPage * 5 >= this.friendQuantity) {
                alert("当前已是最后一页");
            } else {
                this.friendPage++;
                this.getFriendList();
            }
        }
        ,
        friendPrevPage: function () {

            if (this.friendPage <= 1) {
                alert("当前已是第一页");
            } else {
                this.friendPage--;
                this.getFriendList();
            }
        }
        ,
        loadMoreStranger:function () {

            var that = this;
            this.strangerPage++;
            common.ajax.get(common.data.searchStrangerUrl, function (r) {
                if (r.success) {
                    if (r.data.length == 0) {
                        alert("没有更多结果");
                        that.strangerPage--;
                        return;
                    }
                    that.strangerList = that.strangerList.concat(r.data);

                } else {
                    alert(r.msg);
                }
            }, {kw: this.strangerSearch, page: this.strangerPage, length: 5})
        }
        ,
        searchFriend: function () {

            this.getFriendList();
        }
        ,
        searchStranger: function () {
            this.strangerList = [];
            var that = this;
            common.ajax.get(common.data.searchStrangerUrl, function (r) {
                if (r.success) {
                    if (r.data.length == 0) {

                        alert("没有找到相关pser");
                        return;
                    }


                    that.strangerList = r.data;

                } else {
                    alert(r.msg);
                }
            }, {kw: this.strangerSearch, page: this.strangerPage, length: 5})
        },
        showFriendAddDialog: function (stranger) {
            var msg = prompt("给" + stranger.nickName + "发送验证消息:");

            if (msg === null) {
                return;
            }
            if (msg) {
                this.addFriend(msg, stranger.userId);
            } else {
                alert("请输入验证消息");
            }

        },
        addFriend: function (msg, id) {
            if (msg.length > 64) {
                alert("验证消息长度不得大于64！");
            } else {
                common.ajax.put(common.data.addFriendUrl, function (response) {
                    if (response.success) {
                        alert(response.data);
                    } else {
                        alert(response.msg);
                    }

                }, {
                    toUser: id, msg: msg
                });
            }
        }
        ,
        agreeFriendAdd: function (event) {
            var friendAddId = event.srcElement.dataset.id;
            var that = this;
            common.ajax.post(common.data.agreeFriendAddUrl + friendAddId, function (response) {
                if (response.success) {
                    alert(response.data);
                    that.getFriendAddList();
                    that.getFriendList();
                    that.getRecommendFriendList();
                    that.countFriends();
                } else {
                    alert(response.msg);
                }
            });

        }
        ,
        rejectFriendAdd:function (friendAdd) {
            var that = this;
            common.ajax.post(common.data.rejectFriendAddUrl+friendAdd.friendAddId,function (r) {
                if (r.success){
                    alert(r.data);
                    that.getFriendAddList();
                }else {
                    alert(r.msg);
                }
            })
        }
        ,
        chat: function (friend) {

            window.location = "/user/id/" + friend.friendUserId;
        }
        ,
        visitFriend:function (id) {
            location="/space/"+id;
        }
    }
});
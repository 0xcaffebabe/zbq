function getCommonObject() {
    return {
        ajax: {
            get: function (url, success, data) {
                if (data == undefined || data == null) {
                    $.ajax({
                        url: url,
                        method: "GET",
                        success: success,
                        error: function (response) {
                            alert("数据请求出错");
                            console.log(response);
                        }
                    });
                } else {
                    $.ajax({
                        url: url,
                        method: "GET",
                        success: success,
                        data: data,
                        error: function (response) {
                            alert("数据请求出错");
                            console.log(response);
                        }
                    });
                }

            }
            ,
            post: function (url, success, data) {
                $.ajax({
                    url: url,
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    data: JSON.stringify(data),
                    success: success,
                    error: function (response) {
                        alert("数据请求出错");
                        console.log(response);
                    }
                });
            }
            ,
            put: function (url, success, data) {
                if (data == undefined || data == null) {
                    $.ajax({
                        url: url,
                        method: "PUT",
                        headers: {"Content-Type": "application/json"},
                        success: success,
                        error: function (response) {
                            alert("数据请求出错");
                            console.log(response);
                        }
                    });
                } else {
                    $.ajax({
                        url: url,
                        method: "PUT",
                        headers: {"Content-Type": "application/json"},
                        success: success,
                        data: JSON.stringify(data),
                        error: function (response) {
                            alert("数据请求出错");
                            console.log(response);
                        }
                    });
                }
            }
            ,
            delete:function (url,success,data) {
                if (data == undefined || data == null) {
                    $.ajax({
                        url: url,
                        method: "DELETE",
                        headers: {"Content-Type": "application/json"},
                        success: success,
                        error: function (response) {
                            alert("数据请求出错");
                            console.log(response);
                        }
                    });
                } else {
                    $.ajax({
                        url: url,
                        method: "DELETE",
                        success: success,
                        headers: {"Content-Type": "application/json"},
                        data: JSON.stringify(data),
                        error: function (response) {
                            alert("数据请求出错");
                            console.log(response);
                        }
                    });
                }

            }
            ,
            upload: function (url, success, data) {

                $.ajax({
                    url: url,
                    type: 'POST',
                    data: data,
                    cache: false,
                    processData: false,
                    contentType: false,
                    success: success,
                    error: function (response) {
                        alert("数据请求出错");
                        console.log(response);
                    }
                });
            }
        },
        data: {
            loginUrl: '/user/login',
            registerUrl: '/user/register',
            logoutUrl: '/user/logout',
            getCurrentUserDataUrl: '/user/state',
            getCurrentUserInfoUrl: "/userInfo/self",
            updateUserInfoUrl: '/userInfo',
            getFriendListUrl: "/friend/self",
            getRecommendFriendListUrl: "/friend/recommend",
            addFriendUrl: '/friend/add',
            getFriendAddListUrl: "/friend/add",
            agreeFriendAddUrl: "/friend/add/agree/",
            countFriendsUrl: "/friend/self/count",
            getMessageListUrl: "/message/friend/",
            sendMessageUrl: "/message",
            getUnreadMessageListUrl: "/message/unread",
            getUserMessageListUrl: "/message/list",
            getSelfStateListUrl:"/state/self",
            publishStateUrl:"/state",
            likeStateUrl:"/like/state/",
            countStateUrl:"/state/self/count"
        }
    }
}

var common = getCommonObject();

var nav = new Vue({
    el: "#side",
    data: {
        url: location.pathname
    }
});


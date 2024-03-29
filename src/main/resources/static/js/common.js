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
            countStateUrl:"/state/self/count",
            publishStateCommentUrl:"/state/comment",
            countLikeUrl:"/like/count",
            getAllLocationUrl:"/location/list",
            shareLocationUrl:"/location",
            getSelfLocationUrl:"/location/self",
            analyzeVideoUrl:"/video/analyze",
            deleteStateUrl:"/state/",
            getContentListUrl:"/content/list",
            publishContentUrl:"/content",
            likeContentUrl:"/like/content/",
            submitContentCommentUrl:"/content/comment",
            getContentCommentListUrl:"/content/comment/",
            detectUsernameUrl:"/user/username",
            getContentByIdUrl:"/content/",
            getCourseListUrl:"/course/list",
            getCourseLessonUrl:"/course/lesson/",
            getLessonByIdUrl:"/lesson/",
            getSelfCourseListUrl:"/course/self",
            publishLessonUrl:"/lesson",
            publishCourseUrl:"/course",
            createLearningUrl:"/learning/",
            getSelfLearningUrl:"/learning/self/",
            getSelfLearningProgressUrl:"/learning/self/progress/",
            searchStrangerUrl:"/friend/stranger",
            getNickNameUrl:"/user/nickName/",
            rejectFriendAddUrl:"/friend/add/reject/",
            getLearningListUrl:"/learning/self/list",
            bindEmailUrl:"/userAccount/",
            getAccountListUrl:"/userAccount/list",
            getTop10LoginLogUrl:"/user/log/top10",
            getUserStateUrl:"/user/state/",
            countUserUrl:"/user/count",
            getClassmatesUrl:"/course/classmates/",
            countOnlineUserUrl:"/user/online/count",
            sendBroadcastUrl:"/broadcast",
            getBroadcastListUrl:"/broadcast/list",
            countDaysUrl:"/user/online/days",
            searchVideoUrl:"/video/search",
            getVideoSearchEngineUrl:"/video/engine/list",
            getHotKwUrl:"/video/hotKw",
            collectContentUrl:"/content/collect",
            getCollectionListUrl:"/collection/list",
            publishLessonCommentUrl:"/lesson/comment",
            getCommentListUrl:"/comment/list",
            getCourseCommentListUrl:"/course/comment/",
            courseRatingUrl:"/course/rating",
            getCourseRatingListUrl:"/course/rating/",
            getActionListUrl:"/user/action/",
            subscribeAuthorUrl:"/subscription/",
            getSelfSettingUrl:"/user/setting/self",
            userRestPasswordUrl:"/user/password",
            getFriendInfoUrl:"/friend/",


        },
        methods:{
            getSelfState:function (success) {
                common.ajax.get(common.data.getCurrentUserDataUrl,success);
            }
        }
    }
}

var common = getCommonObject();




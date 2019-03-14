$(window).scroll(function () {
    var scrollTop = $(this).scrollTop();
    var scrollHeight = $(document).height();
    var windowHeight = $(this).height();
    if (scrollTop + windowHeight == scrollHeight) {
        if (state.selfStateList.length != 0) {
            state.pageTuring();
            console.log("模拟获取数据");
        }

    }
});


var state = new Vue({
    el: "#state",
    data: {
        selfStateList: [],
        stateContent: '',
        userId: 0,
        pageNo: 1,
        length: 5,
        likeCacheMap: {}
    }
    ,
    created: function () {
        moment.locale("zh-cn");
        this.userId = header.userId;
        this.getSelfStateList();


    }
    ,
    methods: {
        getSelfStateList: function () {

            var that = this;
            common.ajax.get(common.data.getSelfStateListUrl, function (response) {
                if (response.success) {
                    var list = response.data;
                    if (list.length == 0) {
                        alert("没有更多数据!");
                        that.pageNo--;
                    }
                    for (var i = 0; i < list.length; i++) {
                        list[i].createTime = moment(list[i].createTime).fromNow();
                    }
                    that.selfStateList = that.selfStateList.concat(list);

                    for (var i = 0; i < that.selfStateList.length; i++) {
                        that.likeCacheMap[that.selfStateList[i].stateId] =
                            that.hasLike(that.selfStateList[i].likes.likeList);

                    }
                    console.log(that.likeCacheMap);
                } else {
                    alert("获取朋友动态失败:" + response.msg);
                }
            }, {page: this.pageNo, length: this.length});
        }
        ,
        hasLike: function (list) {
            for (var i = 0; i < list.length; i++) {
                if (list[i].likeUser.userId == this.userId) {

                    return true;
                }
            }

            return false;
        }
        ,
        publishState: function () {
            var that = this;
            common.ajax.put(common.data.publishStateUrl, function (response) {
                if (response.success) {
                    that.stateContent = '';
                    alert(response.data);
                    that.pageNo = 1;
                    that.selfStateList = [];
                    that.getSelfStateList();
                } else {
                    alert("发表动态失败:" + response.msg);
                }
            }, {content: this.stateContent});
        }
        ,
        likeClick: function (event) {
            var id = event.srcElement.dataset.id;

            var likeState = true;
            for (var i = 0; i < this.selfStateList.length; i++) {
                if (this.selfStateList[i].stateId == id) {
                    if (this.hasLike(this.selfStateList[i].likes.likeList)) {
                        likeState = false;
                    }
                }
            }

            var that = this;
            if (likeState) {
                common.ajax.put(common.data.likeStateUrl + id, function (response) {
                    if (response.success) {
                        alert(response.data);
                        that.getSelfStateList();
                    } else {
                        alert("点赞失败:" + response.msg);
                    }
                });
            } else {
                common.ajax.delete(common.data.likeStateUrl + id, function (response) {
                    if (response.success) {
                        alert(response.data);
                        that.getSelfStateList();
                    } else {
                        alert("取消点赞失败:" + response.msg);
                    }
                });
            }

        }
        ,
        pageTuring: function () {

            this.pageNo++;
            this.getSelfStateList();
        }
    }
});
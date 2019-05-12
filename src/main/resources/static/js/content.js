$(window).scroll(function () {
    var scrollTop = $(this).scrollTop();
    var scrollHeight = $(document).height();
    var windowHeight = $(this).height();
    if (scrollTop + windowHeight == scrollHeight) {
        content.pageTuring();

    }
});


var content = new Vue({
    el: "#content",
    data: {
        contentList: [],
        page: 1,
        length: 5,
        contentFold: {},
        commentSubmitBtn: false,
        currentContent: {},
        commentContent:'',
        commentList:[],
        commentPage:1,
        commentLength:15
    }
    ,
    created: function () {
        moment.locale("zh-cn");
        this.getContentList();
    }

    ,
    methods: {
        getContentList: function () {

            var that = this;
            common.ajax.get(common.data.getContentListUrl, function (response) {

                if (response.success) {

                    var list = response.data;

                    if (list.length == 0) {
                        alert("无数据");
                        return;
                    }

                    for (var i = 0; i < list.length; i++) {
                        list[i].createTime = moment(list[i].createTime).fromNow();
                    }
                    that.contentList = that.contentList.concat(list);

                    setTimeout(function () {
                        $(".targetFold").each(function () {
                            if ($(this).height() >= 250) {
                                $(this).addClass("fold");
                                $(this).parent(".mx-auto").append("<button class=\"btn btn-sm btn-primary\"  onclick=\"content.unfold(event)\">展开全文 <i class=\"fa fa-caret-down\"></i>");
                            }
                        })
                    }, 1500);

                } else {
                    alert(response.msg);
                }
            }, {page: this.page, length: this.length});
        }
        ,
        pageTuring: function () {
            this.page++;
            this.getContentList();
        }
        ,
        unfold: function (event) {
            if (event.target.innerHTML.startsWith("展开")) {
                $(event.srcElement).prev("p").removeClass("fold");
                event.target.innerHTML = '收起全文<i class="fa fa-caret-up"></i>';
            } else {
                $(event.srcElement).prev("p").addClass("fold");
                event.target.innerHTML = '展开全文<i class="fa fa-caret-down"></i>';

            }

        }
        ,
        likeContent: function (content) {

            if (content.hasLike) {
                common.ajax.delete(common.data.likeContentUrl + content.contentId, function (resposne) {
                    if (resposne.success) {
                        content.hasLike = false;
                        content.likeCount = content.likeCount - 1;
                        alert(resposne.data);
                    } else {
                        alert(resposne.msg);
                    }
                });
            } else {
                common.ajax.put(common.data.likeContentUrl + content.contentId, function (resposne) {
                    if (resposne.success) {
                        content.hasLike = true;
                        content.likeCount = content.likeCount + 1;
                        alert(resposne.data);
                    } else {
                        alert(resposne.msg);
                    }
                });
            }

        }

        ,
        showComment: function (content) {
            console.log(content);
            this.currentContent = content;
            var that = this;
            if(!$("#staticModal").is(":visible")){
                that.pullCommentList(content);
            }

            $("#modalTrigger").trigger("click");
            $("#staticModal").css("margin-top", 100);
        }
        ,
        pullCommentList:function (content) {
            var that = this;
            common.ajax.get(common.data.getContentCommentListUrl+content.contentId,function (response) {
                if (response.success){
                    var list = response.data;
                    for (var i =0;i<list.length;i++){
                        list[i].createTime = moment(list[i].createTime).fromNow();
                    }
                    that.commentList = list;
                }else{
                    alert(response.msg);
                }
            },{page:this.commentPage,length:this.commentLength});
        }
        ,
        commentPaneFocus: function () {
            this.commentSubmitBtn = true;
        }
        ,
        commentPaneBlur: function () {
            this.commentSubmitBtn = false;
        }
        ,
        submitComment:function () {

            var that = this;
            common.ajax.put(common.data.submitContentCommentUrl,function (response) {
                if (response.success){
                    that.commentContent = '';
                    that.pullCommentList(that.currentContent);
                    alert(response.data);

                }else{
                    alert(response.msg);
                }
            },{contentId:this.currentContent.contentId,content:this.commentContent});
        }
        ,
        collect:function (content) {

            common.ajax.put(common.data.collectContentUrl,function (r) {
                if (r.success){
                    alert(r.data);
                    content.collectCount++;
                    content.hasCollect = true;
                }else {
                    alert(r.msg);
                }
            },{collectionType:1,contentId:content.contentId})
        }

    }
});

var id = location.pathname.replace("/lessons/","");
var lesson = new Vue({
    el:"#lesson",
    data:{
        lesson:{},
        learning:null,
        mirrorButton:'镜像',
        commentContent:'',
        commentList:[]
    }
    ,
    created:function () {
        this.getLesson();
        this.getLearning();
        this.getCommentList();
    }
    ,
    methods:{
        getLesson:function () {
            var that = this;
            common.ajax.get(common.data.getLessonByIdUrl+id,function (r) {
                if (r.success){
                    that.lesson = r.data;
                }else{
                    alert(r.msg);
                }
            })
        }
        ,
        getLearning:function () {
            var that = this;
            common.ajax.get(common.data.getSelfLearningUrl+id,function (r) {
                if (r.success){
                    that.learning = r.data;
                }else {
                    alert(r.msg);
                }
            })

        }
        ,
        deleteLearning:function () {
            var that = this;
            common.ajax.delete(common.data.getSelfLearningUrl+this.learning.learningId,function (r) {
                if (r.success){
                    that.getLearning();
                    alert(r.data);
                }else {
                    alert(r.msg);
                }
            })
        }
        ,
        createLearning:function () {
            var that = this;
            common.ajax.put(common.data.createLearningUrl+id,function (r) {
                if (r.success){
                    that.getLearning();
                    alert(r.data);

                }else {
                    alert(r.msg);

                }
            })
        }
        ,
        videoMirror:function () {
            if (this.mirrorButton == '镜像'){
                this.mirrorButton = '还原';
                $("iframe").addClass("mirror");
            }else{
                this.mirrorButton = '镜像';
                $("iframe").removeClass("mirror");
            }

        }
        ,
        publishComment:function () {

            common.ajax.put(common.data.publishLessonCommentUrl,function (r) {
                if (r.success){
                    alert(r.data);
                }else{
                    alert(r.msg);
                }
            },{
                lessonId:id,content:this.commentContent
            });
        }
        ,
        getCommentList:function () {

            var that = this;
            common.ajax.get(common.data.getCommentListUrl,function (r) {
                if (r.success){
                    var list = r.data;
                    that.commentList = list;
                }else{
                    alert(r.msg);
                }
            },{
                type:2,
                id:id,
                page:1,
                length:10
            })
        }
    }
})
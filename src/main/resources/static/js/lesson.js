
var id = location.pathname.replace("lessons/","");
var lesson = new Vue({
    el:"#lesson",
    data:{
        lesson:{},
        learning:null
    }
    ,
    created:function () {
        this.getLesson();
        this.getLearning();
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
    }
})
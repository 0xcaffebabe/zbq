
var id = location.pathname.replace("/addLesson/","");

var addLesson = new Vue({
    el: "#addLesson",
    data: {
        editor:{},lessonName:''
    }
    ,
    mounted:function () {
        var E = window.wangEditor;
        this.editor = new E('#editor');
        this.editor.create();
    }
    ,
    methods: {
        publishLesson:function () {

            var obj = {
                courseId:id,
                lessonName:this.lessonName,
                lessonContent:this.editor.txt.html()
            }


            common.ajax.put(common.data.publishLessonUrl,function (r) {
                if (r.success){
                    alert(r.data);
                }else {
                    alert(r.msg);
                }
            },obj);
        }
    }
});
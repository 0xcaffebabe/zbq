
var publish2 = new Vue({
    el:"#publish",
    data:{
        courseList:[],course:{}
    }
    ,
    created:function () {
        this.getSelfCourseList();
    }
    ,
    methods:{
        getSelfCourseList:function () {

            var that = this;
            common.ajax.get(common.data.getSelfCourseListUrl,function (r) {
                if (r.success){
                    that.courseList = r.data;
                }else {
                    alert(r.msg);
                }
            })

        }
        ,
        editCourse:function (course) {
            location = "/courseEdit/"+course.courseId;
        }
        ,
        showModal:function () {
            $("#modalTrigger").trigger("click");
        }
        ,
        showFile:function () {

            $("#file").trigger("click");
        }
        ,
        uploadThumbnail:function () {
            var files = $('#file').prop('files');

            var data = new FormData();
            data.append('file', files[0]);

            var that = this;
            common.ajax.upload('/upload/thumbnail',function (response) {
                if (response.success){

                    that.course.courseImg= response.data;
                    alert("上传成功，请即使保存！");

                }else{
                    alert(response.msg);
                }

            },data);
        }
        ,
        publishCourse:function () {

            common.ajax.put(common.data.publishCourseUrl,function (r) {
                if (r.success){
                    alert(r.data);
                }else {
                    alert(r.msg);
                }
            },this.course);
        }
    }
})
var id = location.pathname.replace("courseEdit/", "");

var courseEdit = new Vue({
    el: "#courseEdit",
    data: {
        course: {}
    }
    ,
    created: function () {
        console.log("run");
        this.getCourseLesson();
    }
    ,
    methods: {
        getCourseLesson: function () {
            var that = this;
            common.ajax.get(common.data.getCourseLessonUrl + id, function (r) {
                if (r.success) {
                    that.course = r.data;
                } else {
                    alert(r.msg);
                }

            })
        }
        ,
        addLesson:function () {

            window.location = "/addLesson/"+this.course.courseId;
        }
    }
});
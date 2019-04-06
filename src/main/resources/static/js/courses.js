var course = new Vue({
    el: "#course",
    data: {
        courseList: []
    },
    created: function () {
        this.getCourseList();
    }
    ,
    methods: {
        getCourseList: function () {

            var that = this;
            common.ajax.get(common.data.getCourseListUrl, function (response) {
                if (response.success) {
                    that.courseList = response.data;
                } else {
                    alert(response.msg);
                }
            })
        }
    }
});
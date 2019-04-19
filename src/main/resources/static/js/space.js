var id = location.pathname.replace("/space/", "");
var space = new Vue({
    el: "#space",
    data: {
        nickName: '',
        profile:''
    }
    ,
    created:function () {

        this.getUserState();
    }
    ,
    methods: {
        getUserState: function () {

            var that = this;
            common.ajax.get(common.data.getUserStateUrl + id, function (r) {
                if (r.success) {
                    that.nickName = r.data.nickName;
                    that.profile = r.data.profile;
                } else {
                    alert(r.msg);
                }
            })
        }
    }
});
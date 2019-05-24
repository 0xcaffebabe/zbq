var id = location.pathname.replace("/space/", "");
var space = new Vue({
    el: "#space",
    data: {
        nickName: '',
        profile:'',
        actionList:[]
    }
    ,
    created:function () {

        moment.locale("zh-cn");
        this.getUserState();
        this.getAction();
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
        },
        getAction:function () {
            var that = this;
            common.ajax.get(common.data.getActionListUrl+id,function (r) {
                if (r.success){
                    var list = r.data;

                    list.forEach(function (e) {
                        e.createTime = moment(e.createTime).fromNow();
                    })
                    that.actionList = list;
                }else{
                    alert(r.msg);
                }
            },{page:1,length:5})
        }
        ,
        actionClickHandler:function (action) {

        }
    }
});
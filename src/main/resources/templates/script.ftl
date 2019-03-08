<script src="/js/vue.js"></script>
<script src="/js/jquery.js"></script>
<script src="/js/bootstrap.js"></script>
<script src="http://momentjs.cn/downloads/moment-with-locales.min.js"></script>
<script src="/js/common.js"></script>


<script>

    var header = new Vue({
        el: "#header",
        created: function () {
            this.getInfo();
            moment.locale("zh-cn");
            this.getUnreadMessageList();
        },
        data: {
            profile: '',
            unreadMessageList: [],
            unreadCount: 0
        },
        methods: {
            getInfo: function () {
                var that = this;
                common.ajax.get(common.data.getCurrentUserDataUrl, function (response) {
                    if (response.success) {
                        that.profile = response.data.profile;

                    } else {
                        alert(response.msg);
                    }
                })

            }
            ,
            getUnreadMessageList: function () {

                var that = this;
                common.ajax.get(common.data.getUnreadMessageListUrl, function (response) {
                    if (response.success) {

                        var list = response.data;
                        for (var i = 0; i <= list.length; i++) {

                            for (var key in list[i]) {
                                if (key == 'msgCount') {
                                    that.unreadCount += list[i][key];
                                }else if (key =='sendTime'){
                                    list[i][key] = moment(list[i][key]).fromNow();
                                }
                            }




                        }
                        that.unreadMessageList = list;
                    } else {
                        alert("获取未读消息列表失败:" + response.msg);
                    }
                })
            }
        }
    })
</script>
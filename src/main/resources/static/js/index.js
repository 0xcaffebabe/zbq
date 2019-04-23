var app = new Vue({
    el: "#nav",
    data: {
        username: '未登录', nickName: '', profile: '', hasLogin: false,
        userCount: 0, onlineCount: 0
    }
    ,
    created: function () {

        this.getState();
        this.countUser();
        this.countOnline();

    }
    ,
    methods: {
        getState: function () {
            var that = this;
            common.ajax.get(common.data.getCurrentUserDataUrl, function (response) {
                if (response.data == null) {
                    that.username = '未登录';

                } else {
                    that.username = response.data.username;
                    that.nickName = response.data.nickName;
                    that.profile = response.data.profile;
                    that.hasLogin = true;
                }
                if (localStorage.getItem('username') != null) {
                    if (!that.hasLogin) {
                        window.location = "/login.html";
                    }

                }
            });
        }
        ,
        logout: function () {

            common.ajax.get(common.data.logoutUrl, function (res) {
                window.location = "";
            })
        }
        ,
        countUser: function () {

            var that = this;
            common.ajax.get(common.data.countUserUrl, function (r) {
                if (r.success) {
                    that.userCount = r.data;
                } else {
                    alert(r.msg);
                }
            })
        }
        ,
        countOnline: function () {

            var that = this;
            common.ajax.get(common.data.countOnlineUserUrl, function (r) {
                if (r.success) {
                    that.onlineCount = r.data;
                } else {
                    alert(r.msg);
                }
            })
        }
        ,
        jumpMain:function () {

            if (parent === window){
                location = '/main';
            }else{

                parent.window.jump("/home");
            }
        }
    }
});


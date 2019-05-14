





var app = new Vue({
    el: "#nav",
    data: {
        username: '未登录', nickName: '', profile: '', hasLogin: false,
        userCount: 0, onlineCount: 0,
        showQQPromot:false
    }
    ,
    created: function () {

        this.getState();
        this.countUser();
        this.countOnline();

        // service worker 注册
        if ('serviceWorker' in navigator) {
            window.addEventListener('load', function () {
                navigator.serviceWorker.register('/sw.js', {scope: '/'})
                    .then(function (registration) {
                        // 注册成功
                        console.log('ServiceWorker registration successful with scope: ', registration.scope);
                    })
                    .catch(function (err) {

                        // 注册失败:(
                        console.log('ServiceWorker registration failed: ', err);
                    });

            });
        }

        if (navigator.userAgent.indexOf("TBS") != -1){
            if(navigator.userAgent.indexOf("MQQBrowser") != -1){
                this.showQQPromot = true;
            }
        }

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


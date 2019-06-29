var app = new Vue({
    el: "#login",
    data: {
        username: '',
        password: '',
        isAutoLogin:false,
        showQQPromot:false,
        userCount: 0,
        onlineCount: 0
    }
    ,
    created: function () {

        this.autoLogin();
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

        if (navigator.userAgent.indexOf("TBS") !== -1){
            if(navigator.userAgent.indexOf("MQQBrowser") !== -1){
                this.showQQPromot = true;
            }
        }
    }
    ,
    methods: {
        login: function () {

            if (this.username === '') {
                alert("请输入用户名");
                return;
            }

            if (this.password === '') {
                alert("请输入密码");
                return 0;
            }

            var that = this;
            common.ajax.post(common.data.loginUrl, function (response) {
                if (response.success) {
                    that.saveLoginState();
                    window.location = "/main";
                } else {
                    alert(response.msg);
                }
            }, {
                username: this.username,
                password: hex_md5(this.password)
            });

        }

        ,
        enterEvent: function (e) {

            this.login();
        }
        ,
        autoLogin: function () {

            if (localStorage.getItem("username") != null) {
                common.ajax.post(common.data.loginUrl, function (response) {
                    if (response.success) {
                        window.location = "/main";
                    } else {
                        alert(response.msg);
                    }
                }, {
                    username: localStorage.getItem("username"),
                    password: localStorage.getItem("password")
                });
            }


        }
        ,
        saveLoginState: function () {
            if (!this.isAutoLogin){
                return;
            }
            console.log("save login state");
            localStorage.setItem("username", this.username);
            localStorage.setItem("password", hex_md5(this.password));
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
    }
});
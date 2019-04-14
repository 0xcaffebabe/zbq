var app = new Vue({
    el: "#login",
    data: {
        username: '',
        password: ''
    }
    ,
    created: function () {

        this.autoLogin();
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
            console.log("save login state");
            localStorage.setItem("username", this.username);
            localStorage.setItem("password", hex_md5(this.password));
        }
    }
});
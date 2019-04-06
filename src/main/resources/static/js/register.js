var app = new Vue({
    el: "#register",
    data: {
        username: '',
        password: '',
        repeatPassword: '',
        existShow: false,
        passwordStrength: '',
        passwordStrengthShow: false
    }
    ,
    watch: {
        username: function () {
            this.existShow = false;
        }
        ,
        password:function () {
            this.passwordChange();
        }
    }
    ,
    methods: {
        register: function () {

            if (this.username === '') {
                alert("请输入用户名");
                return;
            }

            if (this.password === '') {
                alert("请输入密码");
                return;
            }


            if (this.password === this.repeatPassword) {
                common.ajax.put(common.data.registerUrl, function (response) {
                    if (response.success) {
                        alert(response.data);
                        window.location = "/main";
                    } else {
                        alert(response.msg);
                    }
                }, {
                    username: this.username,
                    password: hex_md5(this.password)
                });
            } else {
                alert("两次输入的密码不一致");
            }
        }
        ,
        detectUsername: function () {

            if (this.username !== '') {
                var that = this;
                common.ajax.get(common.data.detectUsernameUrl, function (response) {
                    if (response.data) {


                    } else {
                        that.existShow = true;
                    }
                }, {username: this.username});
            }
        }
        ,
        passwordChange: function () {

            var ret = this.checkStrong(this.password);

            this.passwordStrengthShow = true;
            this.passwordStrength = ret;
        }
        ,
        checkStrong: function (sValue) {
            var modes = 0;
            //正则表达式验证符合要求的
            if (sValue.length < 1) return modes;
            if (/\d/.test(sValue)) modes++; //数字
            if (/[a-z]/.test(sValue)) modes++; //小写
            if (/[A-Z]/.test(sValue)) modes++; //大写
            if (/\W/.test(sValue)) modes++; //特殊字符

            //逻辑处理
            switch (modes) {
                case 1:
                    return "弱";
                    break;
                case 2:
                    return "一般";
                case 3:
                case 4:
                    return sValue.length < 12 ? "强" : "很强";
                    break;
            }
        }
    }
});
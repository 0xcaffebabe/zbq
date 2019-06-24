var account = new Vue({
    el: "#account",
    data: {
        email: '', emailBtn: '绑定',
        loginLogList: [],
        setting: {}
    },
    created: function () {
        this.getAccountList();
        this.getLoginLogTop10();
        this.getSetting();
    }
    ,

    methods: {
        getAccountList: function () {
            var that = this;
            common.ajax.get(common.data.getAccountListUrl, function (r) {
                if (r.success) {
                    that.email = r.data['email'];
                    if (that.email) {
                        that.emailBtn = "修改绑定";
                    }
                } else {
                    alert(r.msg);
                }
            })
        }
        ,

        bindEmail: function () {
            var pattern = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
            if (pattern.test(this.email)) {

                common.ajax.put(common.data.bindEmailUrl, function (r) {
                    if (r.success) {
                        alert(r.data);
                    } else {
                        alert(r.msg);
                    }
                }, this.email);
            } else {
                alert("邮箱格式错误");
            }

        }
        ,
        getLoginLogTop10: function () {
            var that = this;
            common.ajax.get(common.data.getTop10LoginLogUrl, function (r) {
                if (r.success) {
                    that.loginLogList = r.data;
                } else {
                    alert(r.msg);
                }
            })
        }
        ,
        getSetting: function () {
            var that = this;
            common.ajax.get(common.data.getSelfSettingUrl, function (r) {
                if (r.success) {
                    that.setting = r.data;
                } else {
                    alert(r.msg);
                }
            })
        }
        ,
        updateSetting: function () {
            common.ajax.post(common.data.getSelfSettingUrl,function (r) {
                if (r.success){
                    console.log("更新设置:"+r.data);
                }else{
                    alert(r.msg);
                }
            },this.setting);
        }
        ,
        toggleEmailInform: function () {

            this.setting.emailInform = !this.setting.emailInform;
            this.updateSetting();
        }
        ,
        toggleAnonymous: function () {
            this.setting.anonymous = !this.setting.anonymous;
            this.updateSetting();
        }

    }
});
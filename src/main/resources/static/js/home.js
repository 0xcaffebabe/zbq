var userCard = new Vue({
    created: function(){
        this.getUserInfo();
    },
    el: "#userCard",
    data: {
        nickName: 'N/A',
        profile: 'N/A',
        region: 'N/A',
        birthday: 'N/A',
        penYear: 0,
        gender: 0,
        description: 'N/A'
    },
    methods: {
        getUserInfo: function () {
            var that = this;
            common.ajax.get(common.data.getCurrentUserInfoUrl, function (response) {
                if (response.success) {
                    that.nickName = response.data.nickName;
                    that.profile = response.data.profile;
                    that.region = response.data.region;
                    that.birthday = response.data.birthday;
                    that.penYear = response.data.penYear;
                    that.gender = response.data.gender;
                    that.description = response.data.description;
                } else {
                    alert(response.msg);
                }
            });

        },
        saveUserInfo:function () {

            var obj = {
                nickName:this.nickName,
                profile:this.profile,
                region:this.region,
                birthday:this.birthday,
                penYear:this.penYear,
                gender:this.gender,
                description:this.description
            };

            common.ajax.post(common.data.updateUserInfoUrl,function (response) {

                if (response.success){
                    alert(response.data);
                }else{
                    alert(response.msg);
                }
            },obj);

        }
    },


});
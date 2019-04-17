
var quickStatrt = new Vue({
   el:"#quickStart",
   data:{
       welcome:''
   }
});

var userCard = new Vue({
    created: function () {
        this.getUserInfo();

    },
    el: "#userCard",
    data: {
        nickName: '',
        profile: '',
        region: '',
        birthday: '',
        penYear: 0,
        gender: 0,
        description: ''
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
                    that.createWelcome();
                } else {
                    alert(response.msg);
                }
            });

        },
        saveUserInfo: function () {

            var obj = {
                nickName: this.nickName,
                profile: this.profile,
                region: this.region,
                birthday: this.birthday,
                penYear: this.penYear,
                gender: this.gender,
                description: this.description
            };

            common.ajax.post(common.data.updateUserInfoUrl, function (response) {

                if (response.success) {
                    alert(response.data);
                } else {
                    alert(response.msg);
                }
            }, obj);

        }
        ,
        changeProfile: function () {

            $("#file").trigger("click");
        }
        ,
        uploadProfile:function () {
            var files = $('#file').prop('files');

            var data = new FormData();
            data.append('file', files[0]);

            var that = this;
            common.ajax.upload('/upload/profile',function (response) {
                if (response.success){

                    that.profile = response.data;
                    alert("上传成功，请点击右上角保存资料！");

                }else{
                    alert(response.msg);
                }

            },data);
        }
        ,
        createWelcome:function () {
            var hour = new Date().getHours();
            var prefix = '';

            if (hour >=6 && hour < 9){
                prefix = '早上';
            }

            if (hour >=9 && hour < 12){
                prefix = '上午';
            }

            if (hour >=12 && hour < 18){
                prefix = '下午';
            }

            if (hour >=18){
                prefix = '晚上';
            }

            if (hour >=0 && hour < 6){
                prefix = '凌晨';
            }

            quickStatrt.welcome = prefix+"好,"+this.nickName;
        }
    },


});

var dashboard = new Vue({
   el:"#dashboard",
   data:{
       friendQuantity:0,
       stateQuantity:0,
       likeQuantity:0,
       likeDetail:{}
   },
    created:function () {

       this.countFriend();
       this.countState();
       this.countLike();
    }

    ,

    methods:{
       countFriend:function () {
           var that = this;
           common.ajax.get(common.data.countFriendsUrl,function (response) {
               if (response.success){
                   that.friendQuantity = response.data;

               }else{
                   alert(response.msg);
               }
           })

       }
       ,
        countState:function () {
            var that = this;
            common.ajax.get(common.data.countStateUrl,function (response) {
                if (response.success){
                    that.stateQuantity = response.data;
                }else{
                    alert("获取动态数失败:"+response.msg);
                }
            })
        }
        ,
        countLike:function () {

           var that = this;
           common.ajax.get(common.data.countLikeUrl,function (response) {
               if (response.success){
                   that.likeQuantity = response.data.total;
                   that.likeDetail= response.data;
               }else{
                   alert("获取收获赞数失败:"+response.msg);
               }
           })
        }
        ,
        showLikeDetail:function () {
            alert("动态:"+this.likeDetail.stateLike+",内容:"+this.likeDetail.contentLike);
        }
    }
});
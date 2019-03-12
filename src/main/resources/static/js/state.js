
var state = new Vue({
   el:"#state",
   data:{
       selfStateList:[],
       stateContent:'',
       userId:0
   }
   ,
    created:function () {
        moment.locale("zh-cn");
        this.getSelfStateList();
        this.userId = header.userId;
    }
   ,
    methods:{
       getSelfStateList:function () {

           var that =this;
           common.ajax.get(common.data.getSelfStateListUrl,function (response) {
               if (response.success){
                   var list = response.data;

                   for (var i = 0;i<list.length;i++){
                       list[i].createTime = moment(list[i].createTime).fromNow();
                   }
                   that.selfStateList = list;

               }else{
                   alert("获取朋友动态失败:"+response.msg);
               }
           })
       }
       ,
       hasLike:function (list) {
           for (var i =0;i<list.length;i++){
               if (list[i].likeUser.userId == this.userId){
                   return true;
               }
           }
           return false;
       }
       ,
        publishState:function () {
            var that =this;
           common.ajax.put(common.data.publishStateUrl,function (response) {
               if (response.success){
                   that.stateContent = '';
                   alert(response.data);
                   that.getSelfStateList();
               }else{
                   alert("发表动态失败:"+response.msg);
               }
           },{content:this.stateContent});
        }
    }
});
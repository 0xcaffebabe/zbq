
var video = new Vue({
   el:"#videos",
   data:{
       videoList:[],
       kw:''
   }
   ,
    created:function () {

    }
   ,
    methods:{
       searchVideo:function () {
           if (this.kw){

               var that = this;
               common.ajax.get(common.data.searchVideoUrl,function (r) {
                   if (r.success){
                       that.videoList = r.data;
                   }else{
                       alert(r.msg);
                   }
               },{kw:this.kw});
           }else{
               alert("关键词不得为空");
           }
       }
    }
});
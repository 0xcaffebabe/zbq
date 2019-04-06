
var contentId = location.pathname.replace("article/","");

var content = new Vue({
   el:"#content",
   data:{
       content:{
           user:{}
       }
   }
   ,
    created:function () {
        this.getContent();
    }
   ,
    methods:{
       getContent:function () {
           var that = this;
           common.ajax.get(common.data.getContentByIdUrl+contentId,function (response) {
               if (response.success){

                   that.content = response.data;
               }else{
                   alert(response.msg);
               }
           })
       }
    }
});
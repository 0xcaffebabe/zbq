
$(window).scroll(function () {
    var scrollTop = $(this).scrollTop();
    var scrollHeight = $(document).height();
    var windowHeight = $(this).height();
    if (scrollTop + windowHeight == scrollHeight) {
            content.pageTuring();

    }
});


var content = new Vue({
   el:"#content",
   data:{
       contentList:[],
       page:1,
       length:5,
       contentFold:{}
   }
   ,
    created:function () {
        moment.locale("zh-cn");
       this.getContentList();
    }

    ,
    methods:{
       getContentList:function () {

           var that = this;
           common.ajax.get(common.data.getContentListUrl,function (response) {

               if (response.success){

                   var list = response.data;

                   if (list.length == 0){
                       alert("无数据");
                       return;
                   }

                   for (var i =0;i<list.length;i++){
                       list[i].createTime =moment(list[i].createTime).fromNow();
                   }
                   that.contentList = that.contentList.concat(list);

                   setTimeout(function () {
                       $(".targetFold").each(function () {
                           if ($(this).height()>=250){
                               $(this).addClass("fold");
                               $(this).parent(".mx-auto").append("<button class=\"btn btn-sm btn-primary\"  onclick=\"content.unfold(event)\">展开全文 <i class=\"fa fa-caret-down\"></i>");
                           }

                       })
                   },1500);

               }else{
                   alert(response.msg);
               }
           },{page:this.page,length:this.length});
       }
       ,
        pageTuring:function () {
           this.page ++;
           this.getContentList();
        }
        ,
        unfold:function (event) {
            if (event.target.innerHTML.startsWith("展开")){
                $(event.srcElement).prev("p").removeClass("fold");
                event.target.innerHTML = '收起全文<i class="fa fa-caret-up"></i>';
            }else{
                $(event.srcElement).prev("p").addClass("fold");
                event.target.innerHTML = '展开全文<i class="fa fa-caret-down"></i>';

            }

        }
        ,
        likeContent:function (content) {

           if (content.hasLike){
               common.ajax.delete(common.data.likeContentUrl+content.contentId,function (resposne) {
                   if (resposne.success){
                       content.hasLike = false;
                       content.likeCount = content.likeCount-1;
                       alert(resposne.data);
                   }else{
                       alert(resposne.msg);
                   }
               });
           }else{
               common.ajax.put(common.data.likeContentUrl+content.contentId,function (resposne) {
                   if (resposne.success){
                       content.hasLike = true;
                       content.likeCount = content.likeCount+1;
                       alert(resposne.data);
                   }else{
                       alert(resposne.msg);
                   }
               });
           }

        }
    }
});
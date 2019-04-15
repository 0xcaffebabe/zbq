
var larning = new Vue({

   el:"#learning",
   data:{
       learningList:[]
   }
   ,
    created:function () {

       this.getLearningList();
    }
    ,
    methods:{
       getLearningList:function () {

           var that = this;
           common.ajax.get(common.data.getLearningListUrl,function (r) {
               if (r.success){
                   that.learningList = r.data;
               }else {
                   alert(r.msg);
               }
           })
       }
       ,
        continueLearn:function (learning) {
            window.location = '/courses/'+learning.courseId;
        }
    }
});
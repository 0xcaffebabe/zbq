
var id = location.pathname.replace("courses/","");

var course = new Vue({
   el:"#course",
   data:{
       course:{
           author:{}
           },
       progress:0,
       classmateList:[],
       commentList:[]
   }
   ,
    created:function () {

       this.getCourseLesson();
       this.getLearningProgress();
       this.getClassmates();
       this.getCommentList();
       moment.locale("zh-cn");
    }
   ,
    methods:{
       getCourseLesson:function () {
           var that = this;
           common.ajax.get(common.data.getCourseLessonUrl+id,function (r) {
               if (r.success){
                   that.course = r.data;
               }else{
                   alert(r.msg);
               }
           })
       }
       ,
        getLearningProgress:function () {
           var that = this;
           common.ajax.get(common.data.getSelfLearningProgressUrl+id,function (r) {
               if (r.success){
                   that.progress = r.data;
               }else{
                   alert(r.msg);
               }
           })
        }
        ,
        getClassmates:function () {

           var that = this;
           common.ajax.get(common.data.getClassmatesUrl+id,function (r) {
               if (r.success){
                   that.classmateList = r.data;
               }else{
                   alert(r.msg);
               }
           })
        }
        ,
        getCommentList:function () {

           var that = this;
           common.ajax.get(common.data.getCourseCommentListUrl+id,function (r) {
               if (r.success){
                   var list = r.data;

                   list.forEach(function (e) {
                       e.createTime = moment(e.createTime).fromNow();
                   });

                   that.commentList = list;
               }else{
                   alert(r.msg);
               }
           },{page:1,length:10});
        }

    }
});
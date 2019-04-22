
var id = location.pathname.replace("courses/","");

var course = new Vue({
   el:"#course",
   data:{
       course:{},progress:0,classmateList:[]
   }
   ,
    created:function () {

       this.getCourseLesson();
       this.getLearningProgress();
       this.getClassmates();
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
    }
});
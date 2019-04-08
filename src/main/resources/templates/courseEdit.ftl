<!doctype html>

<!--[if gt IE 8]><!-->
<html class="no-js" lang=""> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>测试主页</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">


    <#include "css.ftl"/>


</head>

<body>

<!-- Content -->
<div class="content">
    <!-- Animated -->



    <div class="animated fadeIn" id="courseEdit">



        <div class="card">
            <div class="card-header">
                <h3>课程信息修改</h3>
            </div>
            <div class="card-body">
                <div class="form-group">
                    <label for="courseName">课程名称</label>
                    <input type="text" class="form-control" id="courseName" v-model="course.courseName">
                </div>

                <div>
                    <div >课程封面:</div>

                    <input type="text" v-model="course.courseImg" style="display: none">
                    <img :src="course.courseImg" alt="" style="width: 400px;height:300px">
                    <button class="btn btn-primary btn-sm" >更换封面</button>
                </div>

                <div class="form-group">
                    <label for="courseLevel">课程等级</label>
                    <input type="text" class="form-control" id="courseLevel" v-model="course.courseLevel">
                </div>

                <div class="form-group">
                    <label for="courseDesc">课程描述</label>
                    <textarea type="text" class="form-control" id="courseDesc" v-model="course.courseDesc"></textarea>
                </div>

                <hr>
                <button class="btn btn-primary" style="float: right;">保存</button>
            </div>

        </div>

        <div class="card">
            <div class="card-header">
                <button class="btn btn-sm btn-info" style="float: right;" @click="addLesson">添加章节</button>
                <h3>课程章节修改</h3>

            </div>

            <div class="card-body">
                <ul class="list-group" v-for="(lesson,index) in course.lessonList">
                    <li class="list-group-item">
                        <a :href="'/lessons/' + lesson.lessonId">
                            {{lesson.lessonName}}
                        </a>

                        <div style="float: right">
                            <button v-if="index != 0" class="btn btn-sm btn-primary"> <i class="fa fa-arrow-circle-up"></i></button>
                            <button v-if="index != course.lessonList.length-1" class="btn btn-sm btn-primary"> <i class="fa fa-arrow-circle-down"></i></button>
                            <button class="btn btn-sm btn-danger">删除</button>
                        </div>

                    </li>


                </ul>
            </div>
        </div>
    </div>

</div>
<!-- /.content -->

</div>
<div class="clearfix"></div>

<#include "script.ftl"/>

<script src="/js/courseEdit.js"></script>
</body>
</html>

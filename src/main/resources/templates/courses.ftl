<!doctype html>

<!--[if gt IE 8]><!-->
<html class="no-js" lang=""> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>测试主页</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">


    <#include "css.ftl"/>


    <style>
        .courseTitle{
            color:#000
        }

        .courseTitle:hover{
            color:#878787;
        }

        .center-cropped {
            object-fit: none; /* Do not scale the image */
            object-position: center; /* Center the image within the element */
            height: 960px;
            width: 640px;
        }
    </style>
</head>

<body>

<!-- Content -->
<div class="content">
    <!-- Animated -->

    <div class="animated fadeIn" id="course">
        <div>
            <strong>筛选:</strong>
            <div class="btn btn-group">

                <button class="btn btn-sm btn-default">初级</button>
                <button class="btn btn-sm btn-default">中级</button>
                <button class="btn btn-sm btn-default">高级</button>
            </div>
        </div>
        <div class="row" style="margin-top: 15px;">
            <div class="col-md-4" v-for="course in courseList">

                <div style="position: absolute;top: -7px;right: 15px" v-if="course.currentProgress != 0">
                    <span class="badge badge-success">{{course.currentProgress}}%</span>
                </div>


                <a :href="'/courses/' + course.courseId" class="thumbnail">
                        <img :src="course.courseImg" alt="" style="border-radius: 5px;width: 100%;">
                </a>

                <div style="padding:10px;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;" :title="course.courseName">
                    <a :href="'/courses/' + course.courseId" class="courseTitle">
                        <h4>{{course.courseName}}</h4>
                    </a>

                </div>

                <div style="padding:15px;">
                    <p>
                        {{course.courseLevel}}
                        &nbsp;&nbsp;&nbsp;
                        {{course.learningNumber}} 人正在学习
                        &nbsp;&nbsp;
                        10.0分
                    </p>

                    <p style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden;" :title="course.courseDesc">
                        {{course.courseDesc}}
                    </p>



                </div>


            </div>

        </div>
    </div>
    <!-- /.content -->

</div>
<div class="clearfix"></div>

<#include "script.ftl"/>
<script src="/js/courses.js?v=20190410"></script>
</body>
</html>

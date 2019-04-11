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

        .green{
            color:green;
        }
        .hasLearn{
            color:#878787
        }
        .unLearn{
            color:#000;
        }
    </style>
</head>

<body>

<div id="course">
    <div class="jumbotron" style="background:#4183c4">
        <h1 style="color:white">{{course.courseName}}</h1>
        <strong>学习进度:{{progress}}%</strong>
        <p><a class="btn btn-info btn-lg" href="#" role="button">开始学习</a></p>
    </div>

    <!-- Content -->
    <div class="content">

        <!-- Animated -->

        <div class="animated fadeIn" >
            <div class="card">
                <div class="card-header"><h3>简介</h3></div>
                <div class="card-body">{{course.courseDesc}}</div>
            </div>

            <div class="row">
                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header"><h3>章节</h3></div>
                        <div class="card-body">
                            <ul class="list-group">
                                <li class="list-group-item" v-for="lesson in course.lessonList">
                                    <i class="fa fa-play-circle"></i>
                                    <i class="fa fa-check-circle" :class="{green:lesson.hasLearn}" style="float: right"></i>
                                    <a :href="'/lessons/' + lesson.lessonId" :class="{hasLearn:lesson.hasLearn,unLearn:!lesson.hasLearn}">
                                        {{lesson.lessonName}}
                                    </a>
                                </li>

                            </ul>
                        </div>
                    </div>
                </div>
            </div>

        </div>


        <!-- /.content -->

    </div>
    <div class="clearfix"></div>
</div>


<#include "script.ftl"/>
<script src="/js/course.js?v=20190410"></script>
</body>
</html>

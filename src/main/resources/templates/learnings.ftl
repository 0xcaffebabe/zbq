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
        [v-cloak]{
            display: none;
        }
    </style>
</head>

<body>


<!-- Content -->
<div class="content">

    <!-- Animated -->

    <div class="animated fadeIn" id="learning" v-cloak>

        <div style="height: 64px;"></div>

        <div class="row">
            <div class="col-md-8">
                <ul class="list-group">
                    <li class="list-group-item" v-for="learning in learningList">
                        <div class="row">
                            <div class="col-md-4">
                                <img style="height: 128px" :src="learning.courseImg" alt="">
                            </div>
                            <div class="col-md-8">
                                <h3>{{learning.courseName}}</h3>

                                <p style="margin-top: 15px;">
                                    <span style="color: red">已学 {{learning.learningProgress}}%</span> <span>学至 {{learning.lastLessonName}}</span>
                                </p>

                                <button class="btn btn-sm btn-info" style="float: right" @click="continueLearn(learning)">继续学习</button>
                            </div>
                        </div>


                    </li>


                </ul>
            </div>
        </div>

    </div>


    <!-- /.content -->

</div>
<div class="clearfix"></div>

<#include "script.ftl"/>
<script src="js/learning.js?v=20190415"></script>
</body>
</html>

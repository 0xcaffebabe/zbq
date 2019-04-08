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

    </style>
</head>

<body>

<div id="lesson">
    <div class="jumbotron" style="background:#694d9f">
        <h1 style="color:white">{{lesson.lessonName}}</h1>
        <p>...</p>

    </div>

    <!-- Content -->
    <div class="content">

        <!-- Animated -->

        <div class="animated fadeIn" >
            <button onclick="window.history.back()"> <i class="fa fa-backward"></i></button>
            <div class="card">
                <div class="card-header"><h3>正文</h3></div>
                <div class="card-body">
                    <p v-html="lesson.lessonContent">

                    </p>

                    <hr>

                    <button v-if="learning == null" class="btn btn-sm btn-info" style="float: right" @click="createLearning">标记为已学会</button>
                    <button v-if="learning != null" class="btn btn-sm btn-warning" style="float: right" @click="deleteLearning">标记为未学会</button>
                </div>
            </div>
        </div>


        <!-- /.content -->

    </div>
    <div class="clearfix"></div>
</div>


<#include "script.ftl"/>
<script src="/js/lesson.js"></script>
</body>
</html>

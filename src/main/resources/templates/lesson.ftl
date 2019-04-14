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

        .mirror {
            -webkit-transform: scaleX(-1);
            animation: mirror forwards 0s;
            -moz-animation: mirror forwards 0s;
            -webkit-animation: mirror forwards 0s;
            -o-animation: mirror forwards 0s;
        }
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

                    <button class="btn btn-sm btn-info" @click="videoMirror">{{mirrorButton}}</button>
                    <button v-if="learning == null" class="btn btn-sm btn-info" style="float: right" @click="createLearning">标记为已学会</button>
                    <button v-if="learning != null" class="btn btn-sm btn-warning" style="float: right" @click="deleteLearning">标记为未学会</button>
                </div>
            </div>

            <div class="card">
                <div class="card-header">评论</div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-9">
                            <textarea class="form-control"></textarea>
                        </div>
                        <div class="col-md-3">
                            <button class="btn btn-sm btn-primary">发表评论</button>
                        </div>
                    </div>

                    <ul class="media-list">
                        <li class="media" style="margin-top: 15px;">
                            <div class="media-left">
                                <a href="#">
                                    <img class="media-object" src="/img/anonymous.jpg" alt="..." width="64">
                                </a>
                            </div>
                            <div class="media-body" style="margin-left: 10px;">
                                <h4 class="media-heading">My、 <span style="color: #878787;font-size: 16px">3天前</span></h4>
                                <p style="margin-top: 8px">太垃圾了吧 <a href="#" style="font-weight: bold">回复</a></p>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>


        <!-- /.content -->

    </div>
    <div class="clearfix"></div>
</div>


<#include "script.ftl"/>
<script src="/js/lesson.js?v=20190413"></script>
</body>
</html>

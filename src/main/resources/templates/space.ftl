<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link rel="stylesheet" href="/css/index.css">
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/timeline.css">
    <title>主页</title>


    <style>
        #headerwrap{
            background: url(/img/newb.jpg) no-repeat center top;
        }



    </style>
</head>
<body>


<!-- Static navbar -->
<div class="navbar navbar-default navbar-fixed-top" role="navigation" id="nav">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand">Zbq</a>


            <div class="btn-group navbar navbar-btn navbar-right" v-if="username != '未登录'">
                <a href="/main" @click.prevent="jumpMain">
                    <img :src="profile"
                         alt="" height="32" width="32" class="img-circle">
                </a>
            </div>

            <a class="navbar-brand">{{nickName==''?'未登录':nickName}}</a>
            <div class="btn-group navbar navbar-btn" v-if="username == '未登录'">
                <a v-show="username == '未登录'" href="/login.html" class="btn btn-primary">登录</a>
                <a v-show="username == '未登录'" href="/register.html" class="btn btn-success">注册</a>
            </div>


        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a>主页</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>

<div id="space">
    <div id="headerwrap">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-lg-offset-3">

                    <img :src="profile" width="72" style="border-radius: 70px;border:1px #cccq solid" alt="">
                    <h1 style="color: black;font-size: 52px;">{{nickName}}</h1>


                </div>
            </div>

        </div> <!-- /container -->
    </div>


    <section id="works"></section>
    <div class="container">
        <div class="row centered mt mb">


            <div class="container">
                <div class="page-header">
                    <h1 id="timeline">最近动态</h1>
                </div>
                <ul class="timeline">
                    <li v-for="(action,index) in actionList" :class="{'timeline-inverted' :index % 2 == 1 }">

                        <div class="timeline-badge primary" v-if="action.actionType == 'PUBLISH'"><i class="glyphicon glyphicon-check"></i></div>
                        <div class="timeline-badge warning" v-if="action.actionType == 'COMMENT'"><i class="glyphicon glyphicon-comment"></i></div>
                        <div class="timeline-badge danger" v-if="action.actionType == 'LIKE'"><i class="glyphicon glyphicon-thumbs-up"></i></div>
                        <div class="timeline-badge success" v-if="action.actionType == 'COLLECT'"><i class="glyphicon glyphicon-heart"></i></div>
                        <div class="timeline-panel">
                            <div class="timeline-heading">
                                <a href="#" @click.prevent="actionClickHandler(action)">
                                    <h4 class="timeline-title">{{action.title}}</h4>
                                </a>

                                <p><small class="text-muted"><i class="glyphicon glyphicon-time"></i> {{action.createTime}}</small></p>
                            </div>
                            <div class="timeline-body">
                                {{action.body}}
                            </div>
                        </div>
                    </li>

                </ul>
            </div>

        </div>
        <! --/row -->
    </div>
</div>



<div id="footerwrap">
    <div class="container">
        <div class="row centered">
            <div class="col-lg-4">
                <p>&copy2019 IM</p>
            </div>

            <div class="col-lg-4">
                <p>In somewhere.</p>
            </div>
            <div class="col-lg-4">
                <p>715711877@qq.com</p>
            </div>
        </div>
    </div>
</div>

</body>

<#include "script.ftl"/>
<script src="/js/index.js?v=20190423"></script>
<script src="/js/space.js?v=20190524"></script>

<script>

</script>
</html>
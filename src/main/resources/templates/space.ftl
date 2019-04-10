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
            <a class="navbar-brand" href="/">Zbq</a>


            <div class="btn-group navbar navbar-btn navbar-right" v-if="username != '未登录'">
                <a href="/main">
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
                <li class="active"><a href="/">主页</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>


<div id="headerwrap">
    <div class="container">
        <div class="row">
            <div class="col-lg-6 col-lg-offset-3">

                <h1>My、的主页</h1>

            </div>
        </div>
        <! --/row -->
    </div> <!-- /container -->
</div>
<! --/headerwrap -->

<section id="works"></section>
<div class="container">
    <div class="row centered mt mb">
        <h1>最近动态</h1>

    </div>
    <! --/row -->
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
<script src="/js/vue.js"></script>
<script src="/js/jquery.js"></script>
<script src="/js/bootstrap.js"></script>
<script src="/js/common.js?v=20190410"></script>
<script src="/js/index.js?v=20190410"></script>
</html>
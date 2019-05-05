<!doctype html>

<!--[if gt IE 8]><!-->
<html class="no-js" lang=""> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>测试主页</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">


    <#include "css.ftl"/>

    <link rel="stylesheet" href="/css/timeline.css">

</head>

<body>

<!-- Content -->
<div class="content">
    <!-- Animated -->

    <div class="animated fadeIn">

        <div class="container-fluid">

            <div class="card">
                <div class="card-header">
                    <h1 id="timeline">收藏列表</h1>
                </div>

                <div class="card-body">

                    <div class=" form-group">
                        <div class="col col-md-12">
                            <div class="input-group">
                                <input type="text" id="input1-group3" name="input1-group3" placeholder="搜索收藏内容"
                                       class="form-control" v-model="kw" @keypress.enter="searchVideo">

                                <span class="input-group-btn">
                        <button class="btn btn-info" type="button" @click="searchVideo">搜索</button>
                    </span>
                            </div>
                        </div>
                    </div>

                    <ul class="timeline">
                        <li>
                            <div class="timeline-badge info fa fa-video-camera"></div>
                            <div class="timeline-panel">
                                <div class="timeline-heading">

                                    <h4 class="timeline-title">
                                        <a href="#" style="color:black">
                                            《论转笔的转后清理》
                                        </a>
                                        <strong style="float: right"> 11小时前</strong>
                                    </h4>


                                </div>
                                <div class="timeline-body">
                                    <p class="text-sm-right">
                                        568人收藏
                                    </p>

                                </div>
                            </div>
                        </li>
                        <li class="timeline-inverted">
                            <div class="timeline-badge warning fa fa-search"></div>
                            <div class="timeline-panel">
                                <div class="timeline-heading">
                                    <h4 class="timeline-title">
                                        <a href="#" style="color:black">
                                            《【转】给想学转笔的人》
                                        </a>
                                        <strong style="float: right"> 12小时前</strong>
                                    </h4>
                                </div>

                                <div class="timeline-body">
                                    <p class="text-sm-right">
                                        568人收藏
                                    </p>

                                </div>

                            </div>
                        </li>

                        <li>
                            <div class="timeline-badge danger fa fa-book    "></div>
                            <div class="timeline-panel">
                                <div class="timeline-heading">
                                    <h4 class="timeline-title">
                                        <a href="#" style="color:black">
                                            《转笔探讨新手入门篇》
                                        </a>
                                        <strong style="float: right"> 13小时前</strong>
                                    </h4>
                                </div>


                                <div class="timeline-body">
                                    <p class="text-sm-right">
                                        568人收藏
                                    </p>

                                </div>
                            </div>


                        </li>


                    </ul>

                    <div class="text-sm-center">
                        <button class="btn btn-primary">加载更多</button>
                    </div>
                </div>
            </div>


        </div>


    </div>


</div>
<div class="clearfix"></div>

<#include "script.ftl"/>

<script>


</script>
</body>
</html>

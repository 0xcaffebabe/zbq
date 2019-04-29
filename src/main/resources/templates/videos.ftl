<!doctype html>

<!--[if gt IE 8]><!-->
<html class="no-js" lang=""> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>测试主页</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="referrer" content="never">
    <#include "css.ftl"/>

    <style>
        .keyword{
            color:red;
        }
    </style>
</head>

<body>

<!-- Content -->
<div class="content">
    <!-- Animated -->

    <div class="animated fadeIn" id="videos">

        <div class=" form-group">
            <div class="col col-md-12">
                <div class="input-group">
                    <div class="input-group-btn">
                        <div class="btn-group">
                            <button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                                    class="dropdown-toggle btn btn-primary">百度
                            </button>
                            <div tabindex="-1" aria-hidden="true" role="menu" class="dropdown-menu">
                                <button type="button" tabindex="0" class="dropdown-item">百度</button>
                                <button type="button" tabindex="0" class="dropdown-item">搜狗</button>
                                <button type="button" tabindex="0" class="dropdown-item">优酷</button>
                                <button type="button" tabindex="0" class="dropdown-item">B站</button>
                            </div>
                        </div>
                    </div>
                    <input type="text" id="input1-group3" name="input1-group3" placeholder="请输入关键词，如：thumb around normal"
                           class="form-control" v-model="kw" @keypress.enter="searchVideo">

                    <span class="input-group-btn">
                        <button class="btn btn-info" type="button" @click="searchVideo">搜索</button>
                    </span>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="col-md-3 col-xs-6 col-sm-6" v-for="video in videoList">

                <a class="thumbnail" :href="video.link" target="_blank">
                    <img :src="video.thumbnail" alt=""
                         style="border-radius: 5px;width: 100%;">
                </a>

                <div style="padding:10px;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;">
                    <a class="courseTitle">
                        <h5 v-html="video.title"></h5>
                    </a>

                </div>

                <div style="padding:5px;">
                    <p>
                        2019-04-23
                        <strong style="float: right">{{video.source}}</strong>
                    </p>

                </div>

            </div>


        </div>
    </div>

</div>
<!-- /.content -->

</div>
<div class="clearfix"></div>

<#include "script.ftl"/>
<script src="/js/videos.js"></script>
</body>
</html>

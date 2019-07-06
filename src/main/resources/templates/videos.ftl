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
        [v-cloak]{
            display: none;
        }
    </style>
</head>

<body>

<!-- Content -->
<div class="content">
    <!-- Animated -->

    <div class="animated fadeIn" id="videos" v-cloak>

        <div class=" form-group">
            <div class="col col-md-12">
                <div class="input-group">
                    <div class="input-group-btn">
                        <div class="btn-group">
                            <button type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                                    class="dropdown-toggle btn btn-primary">{{engine.engineName}}
                            </button>
                            <div tabindex="-1" aria-hidden="true" role="menu" class="dropdown-menu">
                                <ul class="list-group">
                                    <li class="list-group-item" v-for="engine in engineList">
                                        <button type="button" tabindex="0" class="dropdown-item" @click="changeEngine(engine)">{{engine.engineName}}</button>
                                    </li>

                                </ul>

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

        <div>
            <h5>热门搜索</h5>
            <div class="inline" style="margin-top: 5px">

                <button class="btn btn-default btn-sm" v-for="kw in hotKw" @click="changeKw(kw.kw)">{{kw.kw}}({{kw.heat}})</button>
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

        <div class="text-center">
            <button class="btn btn-primary" v-show="kw && this.videoList.length != 0" @click="getMore">加载更多</button>
        </div>
    </div>

</div>
<!-- /.content -->

</div>
<div class="clearfix"></div>

<#include "script.ftl"/>
<script src="/js/videos.js?v=20190703"></script>
</body>
</html>

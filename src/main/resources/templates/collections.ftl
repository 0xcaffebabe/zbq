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

            <div class="card" id="collections">
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

                    <div class="text-center" v-if="collectionList.length == 0">收藏列表为空</div>
                    <ul class="timeline">

                        <li v-for="(collection,index) in collectionList" :class="{'timeline' : index%2 == 0,'timeline-inverted':index%2 == 1}">
                            <div class="timeline-badge info fa"
                                 :class="{'fa-search':collection.collectionType == 1,'fa-video-camera':collection.collectionType ==2}"></div>
                            <div class="timeline-panel">
                                <div class="timeline-heading">

                                    <h4 class="timeline-title">
                                        <a href="#" @click.prevent="clickHandler(collection)" style="color:black">
                                            {{collection.summary}}
                                        </a>
                                        <strong style="float: right"> {{collection.createTime}}</strong>
                                    </h4>


                                </div>
                                <div class="timeline-body">
                                    <p class="text-sm-right">
                                        {{collection.collectCount}}人收藏
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
<script src="/js/collections.js?v=20190512"></script>
<script>


</script>
</body>
</html>

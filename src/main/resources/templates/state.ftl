<!doctype html>

<!--[if gt IE 8]><!-->
<html class="no-js" lang=""> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>测试主页</title>
    <meta name="description" content="Ela Admin - HTML5 Admin Template">
    <meta name="viewport" content="width=device-width, initial-scale=1">


    <link rel="stylesheet" href="assets/css/normalize.css">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/themify-icons.css">
    <link rel="stylesheet" href="assets/css/pe-icon-7-filled.css">
    <link rel="stylesheet" href="assets/css/flag-icon.min.css">
    <link rel="stylesheet" href="assets/css/cs-skin-elastic.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="css/common.css">


    <link href="assets/css/lib/vector-map/jqvmap.min.css" rel="stylesheet">

    <style>
        /* 设置滚动条的样式 */
        ::-webkit-scrollbar {
            width: 6px;
        }

        /* 滚动槽 */
        ::-webkit-scrollbar-track {
            -webkit-box-shadow: inset006pxrgba(0, 0, 0, 0.3);
            border-radius: 10px;
        }

        /* 滚动条滑块 */
        ::-webkit-scrollbar-thumb {
            border-radius: 10px;
            background: rgba(0, 0, 0, 0.1);
            -webkit-box-shadow: inset006pxrgba(0, 0, 0, 0.5);
        }

        ::-webkit-scrollbar-thumb:window-inactive {
            background: rgba(255, 0, 0, 0.4);
        }

        table td {
            text-transform: none !important;
        }

        .card .nav-tabs a {
            font-size: 20px;
        }

        .media-object {
            border-radius: 50px !important;
        }

        .red{
            color:red;
        }


    </style>
</head>

<body>
<!-- Left Panel -->
<#include "aside.ftl"/>
<!-- /#left-panel -->
<!-- Right Panel -->
<div id="right-panel" class="right-panel">
    <!-- Header-->
    <#include "header.ftl"/>
    <!-- /#header -->
    <div class="breadcrumbs">
        <div class="breadcrumbs-inner">
            <div class="row m-0">
                <div class="col-sm-4">
                    <div class="page-header float-left">
                        <div class="page-title">
                            <h1>笔圈动态</h1>
                        </div>
                    </div>
                </div>
                <div class="col-sm-8">
                    <div class="page-header float-right">
                        <div class="page-title">
                            <ol class="breadcrumb text-right">
                                <li><a href="/home">主页</a></li>
                                <li class="active">动态</li>
                            </ol>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <!-- Content -->
    <div class="content">
        <div class="animated fadeIn" id="state">


            <div class="modal fade" id="videoModal" tabindex="-1" role="dialog" aria-labelledby="staticModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-sm" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="staticModalLabel">视频添加</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">


                            <input type="text" class="form-control" placeholder="请输入视频播放地址" v-model="rowVideoUrl">
                            <p>支持哔哩哔哩、优酷</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" data-dismiss="modal" @click="analyzeVideoUrl">解析</button>
                        </div>
                    </div>
                </div>
            </div>




            <div class="card">
                <div class="card-header">
                    <h4>发布动态</h4>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-8">
                            <textarea class="form-control" v-model="stateContent"></textarea>
                        </div>
                        <div class="col-md-4">
                            <div class="btn btn-group" style="">
                                <button class="btn btn-sm btn-primary" @click="publishState">发布</button>
                                <button class="btn btn-sm btn-success">添加图片</button>
                                <button class="btn btn-sm btn-info" data-toggle="modal" data-target="#videoModal">添加视频</button>
                            </div>
                        </div>
                    </div>
                    <div>
                        <button v-if="videoUrl != ''" @click="clearVideo">一条视频 X</button>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-4" v-if="hotShow">
                    <div class="card">
                        <div class="card-header">
                            <button class="btn btn-default" style="float:right" @click="closeHot">X</button>
                            <h4>热门</h4>

                        </div>
                        <div class="card-body">
                            ...
                        </div>
                    </div>

                </div>
                <div :class="statePaneSize">
                    <div class="card">
                        <div class="card-header">
                            <h4>动态板</h4>
                        </div>
                        <div class="card-body">
                            <div class="default-tab">
                                <nav>
                                    <div class="nav nav-tabs" id="nav-tab" role="tablist">
                                        <a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab"
                                           href="#nav-home" role="tab" aria-controls="nav-home"
                                           aria-selected="true">朋友</a>
                                        <a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab"
                                           href="#nav-profile" role="tab" aria-controls="nav-profile"
                                           aria-selected="false">世界</a>

                                    </div>
                                </nav>
                                <div class="tab-content pl-3 pt-2" id="nav-tabContent">
                                    <div class="tab-pane fade show active" id="nav-home" role="tabpanel"
                                         aria-labelledby="nav-home-tab">

                                        <div class="card" v-for="i in selfStateList" style="margin-top: 20px;">
                                            <div class="card-header">
                                                <div class="row">
                                                    <div class="col-md-3 col-xs-3 col-sm-3">
                                                        <a href="#">
                                                            <img class="media-object" style="" :src="i.userVO.profile"
                                                                 alt="..."
                                                                 width="64" height="64">
                                                        </a>
                                                    </div>
                                                    <div class="col-md-9 col-xs-9 col-sm-9">
                                                        <h4>{{i.userVO.nickName}}</h4>
                                                        <div style="position:absolute;bottom:0">
                                                            <span class="fa fa-clock-o"></span> {{i.createTime}}
                                                        </div>

                                                        <div class="btn btn-group" v-if="i.self" style="float: right">
                                                            <button class="btn btn-sm btn-success">编辑</button>
                                                            <button class="btn btn-sm btn-danger" @click="deleteState(i.stateId)">删除</button>
                                                        </div>
                                                    </div>
                                                </div>


                                            </div>

                                            <div class="card-body">
                                                <p v-if="i.content.content == undefined">{{i.content}}</p>
                                                <p v-if="i.content.content != undefined">{{i.content.content}}</p>
                                                <iframe :src="i.content.video" v-if="i.content.video != undefined" style="width: 100%;height: 350px;"></iframe>

                                                <div>
                                                    <span class="fa fa-heart" style="color:red"></span>{{i.likes.likeCount}}

                                                    <a href="#" v-for="like in i.likes.likeList"
                                                       style="margin-left: 5px"
                                                       :title="like.likeUser.userInfo.nickName">
                                                        <img :src="like.likeUser.userInfo.profile" alt=""
                                                             width="24" style="border-radius: 50px;">
                                                    </a>

                                                </div>

                                                <div>

                                                    <a style="float:right;cursor: pointer"><span
                                                            @click.prevent="likeClick(i)" :data-id="i.stateId"
                                                            class="fa fa-heart "
                                                            :class="{red:i.likes.hasLike}"></span></a>
                                                </div>

                                            </div>
                                            <ul class="media-list" style="padding:20px">

                                                <li class="media" v-for="comment in i.comments">
                                                    <div class="media-left media-middle">
                                                        <a href="#">
                                                            <img class="media-object"
                                                                 :src="comment.fromUser.profile" alt="..."
                                                                 width="32">
                                                        </a>
                                                    </div>
                                                    <div class="media-body">
                                                        <h6 class="media-heading">{{comment.fromUser.nickName}}
                                                            <span class="fa fa-clock-o"></span>
                                                            {{comment.createTime}}</h6>
                                                        <p>
                                                            <strong v-if="comment.toUser != null">
                                                                <a href="#">
                                                                    @{{comment.toUser.nickName}}
                                                                </a>

                                                            </strong>
                                                            {{comment.content}}
                                                            <a href="#"
                                                               @click.prevent="replyComment(comment,i)"><b
                                                                    href="#"> 回复</b></a>
                                                        </p>


                                                    </div>

                                                </li>

                                                <li class="media">
                                                    <div class="media-left media-middle">
                                                        <a href="#">
                                                            <img class="media-object" :src="myProfile"
                                                                 alt="..." width="32">
                                                        </a>
                                                    </div>
                                                    <div class="media-body">
                                                        <div class="form-group form-inline">
                                                            <a href="#">{{atModel[i.stateId]}}</a>
                                                            <input type="text" class="form-control"
                                                                   style="border-left: none!important;"
                                                                   v-model="commentModel[i.stateId]">
                                                            <button class="btn btn-sm btn-primary form-control"
                                                                    @click="publishComment(i)">
                                                                评论
                                                            </button>
                                                        </div>
                                                    </div>
                                                </li>

                                            </ul>


                                        </div>
                                    </div>

                                    <div class="tab-pane fade" id="nav-profile" role="tabpanel"
                                         aria-labelledby="nav-profile-tab">
                                        <p>Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt
                                            tofu stumptown aliqua, retro synth master cleanse. Mustache cliche tempor,
                                            williamsburg carles vegan helvetica. Reprehenderit butcher retro keffiyeh
                                            dreamcatcher synth. Cosby sweater eu banh mi, irure terry richardson ex sd.
                                            Alip placeat salvia cillum iphone. Seitan alip s cardigan american apparel,
                                            butcher voluptate nisi .</p>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

            </div>


        </div><!-- .animated -->


    </div><!-- .content -->


    <!-- /.content -->
    <div class="clearfix"></div>
    <!-- Footer -->
    <footer class="site-footer">
        <div class="footer-inner bg-white">
            <div class="row">
                <div class="col-sm-6">
                    Copyright &copy; 2019 IM
                </div>
                <div class="col-sm-6 text-right"> Designed by anonymous
                </div>
            </div>
        </div>
    </footer>
    <!-- /.site-footer -->
</div>
<!-- /#right-panel -->


<script src="assets/js/vendor/jquery-2.1.4.min.js"></script>
<script src="assets/js/popper.min.js"></script>
<script src="assets/js/plugins.js"></script>
<script src="assets/js/main.js"></script>
<script src="assets/js/lib/flot-chart/jquery.flot.js"></script>
<script src="assets/js/lib/flot-chart/jquery.flot.pie.js"></script>
<script src="assets/js/lib/flot-chart/jquery.flot.spline.js"></script>

<#include "script.ftl"/>
<script src="/js/state.js"></script>


</body>

</html>

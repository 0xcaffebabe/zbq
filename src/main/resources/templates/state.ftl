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
            width:6px;
        }
        /* 滚动槽 */
        ::-webkit-scrollbar-track {
            -webkit-box-shadow:inset006pxrgba(0,0,0,0.3);
            border-radius:10px;
        }
        /* 滚动条滑块 */
        ::-webkit-scrollbar-thumb {
            border-radius:10px;
            background:rgba(0,0,0,0.1);
            -webkit-box-shadow:inset006pxrgba(0,0,0,0.5);
        }
        ::-webkit-scrollbar-thumb:window-inactive {
            background:rgba(255,0,0,0.4);
        }
        table td{
            text-transform: none!important;
        }

        .card .nav-tabs a{
            font-size: 20px;
        }

        .media-object{
            border-radius: 50px!important;
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

            <div class="card">
                <div class="card-header">
                    <h4>发布动态</h4>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-10">
                            <textarea class="form-control" v-model="stateContent"></textarea>
                        </div>
                        <div class="col-md-2">
                            <div class="btn btn-group" style="float: right">
                                <button class="btn btn-sm btn-primary" @click="publishState">发布</button>
                                <button class="btn btn-sm btn-success">添加图片</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-4">
                    <div class="card">
                        <div class="card-header">
                            <h4>热门</h4>
                        </div>
                        <div class="card-body">
                            ...
                        </div>
                    </div>

                </div>
                <div class="col-md-8">
                    <div class="card" >
                        <div class="card-header">
                            <h4>动态板</h4>
                        </div>
                        <div class="card-body">
                            <div class="default-tab">
                                <nav>
                                    <div class="nav nav-tabs" id="nav-tab" role="tablist">
                                        <a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true">朋友</a>
                                        <a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">世界</a>

                                    </div>
                                </nav>
                                <div class="tab-content pl-3 pt-2" id="nav-tabContent">
                                    <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
                                        <div class="media" v-for="i in selfStateList">
                                            <div class="media-left">
                                                <a href="#">
                                                    <img class="media-object" style="" :src="i.userVO.profile" alt="..." width="64" height="64">
                                                </a>
                                            </div>
                                            <div class="media-body">
                                                <div class="card">
                                                    <div class="card-header">
                                                        <h4>{{i.userVO.nickName}}</h4>
                                                        <span class="fa fa-clock-o"></span> {{i.createTime}}
                                                    </div>

                                                    <div class="card-body">
                                                        {{i.content}}
                                                        <div>
                                                            <span class="fa fa-heart" style="color:red"></span>{{i.likes.likeCount}}

                                                            <a  href="#" v-for="like in i.likes.likeList" style="margin-left: 5px" :title="like.likeUser.userInfo.nickName">
                                                                <img :src="like.likeUser.userInfo.profile" alt="" width="24"style="border-radius: 50px;">
                                                            </a>

                                                        </div>

                                                        <div>

                                                            <a  style="float:right;cursor: pointer" ><span @click.prevent="likeClick" :data-id="i.stateId" class="fa fa-heart " :class="{red:hasLike(i.likes.likeList)}"></span></a>
                                                        </div>

                                                    </div>
                                                    <ul class="media-list">
                                                        <li class="media">
                                                            <div class="media-left media-middle">
                                                                <a href="#">
                                                                    <img class="media-object" src="/img/anonymous.jpg" alt="..." width="32">
                                                                </a>
                                                            </div>
                                                            <div class="media-body">
                                                                <h6 class="media-heading">佚名 <span class="fa fa-clock-o"></span> 4分钟前</h6>
                                                                <p>你说尼玛呢</p>
                                                            </div>

                                                        </li>

                                                        <li class="media">
                                                            <div class="media-left media-middle">
                                                                <a href="#">
                                                                    <img class="media-object" src="/img/anonymous.jpg" alt="..." width="32">
                                                                </a>
                                                            </div>
                                                            <div class="media-body">
                                                                <h6 class="media-heading">佚名 <span class="fa fa-clock-o"></span> 30秒前</h6>

                                                                <p>太贵了</p>
                                                            </div>
                                                        </li>

                                                        <li class="media">
                                                            <div class="media-left media-middle">
                                                                <a href="#">
                                                                    <img class="media-object" src="/img/anonymous.jpg" alt="..." width="32">
                                                                </a>
                                                            </div>
                                                            <div class="media-body">
                                                                <div class="form-group form-inline">
                                                                    <input type="text" class="form-control">
                                                                    <button class="btn btn-sm btn-primary form-control">评论</button>
                                                                </div>
                                                            </div>
                                                        </li>

                                                    </ul>


                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
                                        <p>Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua, retro synth master cleanse. Mustache cliche tempor, williamsburg carles vegan helvetica. Reprehenderit butcher retro keffiyeh dreamcatcher synth. Cosby sweater eu banh mi, irure terry richardson ex sd. Alip placeat salvia cillum iphone. Seitan alip s cardigan american apparel, butcher voluptate nisi .</p>
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

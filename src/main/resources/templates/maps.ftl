<!doctype html>

<!--[if gt IE 8]><!-->
<html class="no-js" lang=""> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>测试主页</title>
    <meta name="description" content="Ela Admin - HTML5 Admin Template">
    <meta name="viewport" content="width=device-width, initial-scale=1">


    <link rel="stylesheet" href="/assets/css/normalize.css">
    <link rel="stylesheet" href="/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="/assets/css/themify-icons.css">
    <link rel="stylesheet" href="/assets/css/pe-icon-7-filled.css">
    <link rel="stylesheet" href="/assets/css/flag-icon.min.css">
    <link rel="stylesheet" href="/assets/css/cs-skin-elastic.css">
    <link rel="stylesheet" href="/assets/css/style.css">
    <link rel="stylesheet" href="/css/common.css">


    <link href="/assets/css/lib/vector-map/jqvmap.min.css" rel="stylesheet">

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

        .self {
            background-color: #03a9f3 !important;
            color: white !important;
        }

        iframe{
            border:none
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
                            <h1>转笔地图</h1>
                        </div>
                    </div>
                </div>
                <div class="col-sm-8">
                    <div class="page-header float-right">
                        <div class="page-title">
                            <ol class="breadcrumb text-right">
                                <li><a href="/home">主页</a></li>

                                <li class="active">转笔地图</li>
                            </ol>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>




    <!-- Content -->
    <div class="content">
        <!-- Animated -->

        <div class="animated fadeIn" id="map">

            <div class="modal fade" id="staticModal" tabindex="-1" role="dialog" aria-labelledby="staticModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-sm" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="staticModalLabel">Pser信息</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">

                            <img :src="user.profile"
                                 alt="" width="50" class="img-circle">
                            <span>{{user.nickName}}</span>
                            <a v-show="!user.anonymous" :href="'/space/' + user.userId">TA的主页</a>
                            <div>
                                <span>{{user.address}}</span>
                                <div></div>
                                <span>经纬度:{{user.lnglat}}</span>
                                <div></div>
                                <span>距离你 {{user.distance}}</span>
                            </div>



                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>

                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="locationModal" tabindex="-1" role="dialog" aria-labelledby="staticModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="staticModalLabel">我的位置信息</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <span>地址:{{position.address}}</span>
                            <div></div>
                            <span>经纬度:{{position.position.lng}},{{position.position.lat}}</span>
                            <div></div>
                            <span>是否匿名:</span>
                            <input type="checkbox" class="checkbox" v-model="anonymous">

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" data-dismiss="modal" :class="{disabled:location != null}" @click="shareLocation">共享我的位置</button>
                            <button type="button" class="btn btn-success" data-dismiss="modal" :class="{disabled:location == null}" @click="updateLocation">更新我的位置</button>

                        </div>
                    </div>
                </div>
            </div>

            <button style="display: none" data-toggle="modal" data-target="#locationModal" id="simulationLocation">111</button>
            <button style="display: none" data-toggle="modal" data-target="#staticModal" id="simulation">111</button>


            <span>{{address}}</span>
            <div class="btn btn-group">


                <button class="btn btn-sm btn-primary" @click="showShareLocation">共享我的位置</button>
            </div>
            <div>
                <p class="text-sm-center">已有{{locationCount}}位Pser共享了位置，红水滴为当前位置，蓝水滴为其他pser位置;拖动地图可更改你的定位</p>
            </div>
            <iframe name="container" src="/location.html" width="100%" height="500"></iframe>

            <div class="clearfix"></div>
            <!-- .animated -->
        </div>
        <!-- /.content -->

    </div>
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

<script src="/assets/js/vendor/jquery-2.1.4.min.js"></script>
<script src="/assets/js/popper.min.js"></script>
<script src="/assets/js/plugins.js"></script>
<script src="/assets/js/main.js"></script>
<script src="/assets/js/lib/flot-chart/jquery.flot.js"></script>
<script src="/assets/js/lib/flot-chart/jquery.flot.pie.js"></script>
<script src="/assets/js/lib/flot-chart/jquery.flot.spline.js"></script>
<#include "script.ftl"/>
<script src="/js/map.js"></script>

<script>




</script>
</body>
</html>

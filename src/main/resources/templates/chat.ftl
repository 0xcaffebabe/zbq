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
    <div class="breadcrumbs" style="box-shadow: 0 0 20px rgba(0, 0, 0, 0.08);">
        <div class="breadcrumbs-inner">
            <div class="row m-0">
                <div class="col-sm-4">
                    <div class="page-header float-left">
                        <div class="page-title">
                            <h1>笔友</h1>
                        </div>
                    </div>
                </div>
                <div class="col-sm-8">
                    <div class="page-header float-right">
                        <div class="page-title">
                            <ol class="breadcrumb text-right">
                                <li><a href="#">主页</a></li>
                                <li><a href="#">笔友</a></li>
                                <li class="active">与root聊天</li>
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
    <div class="animated fadeIn">


        <div class="row" id="chat">

            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title box-title">消息列表</h4>
                        <div class="card-content">
                            <table class="table table-striped">
                                <tr>
                                    <td>
                                        <div class="media">
                                            <div class="media-left">
                                                <div class="round-img">
                                                    <a href="#"><img class="rounded-circle" src="/img/anonymous.jpg"
                                                                     width="64" alt=""></a>
                                                </div>
                                            </div>
                                            <div class="media-body" style="margin-left: 15px;">


                                                <div class="text-left" style="font-size: 20px;font-weight: bold">
                                                    root
                                                    <span style="float:right;border-radius: 50px;font-size: 14px"
                                                          class="badge badge-primary">4</span>
                                                </div>

                                                <p style="font-size: 12px">你好</p>
                                            </div>
                                        </div>


                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div class="media">
                                            <div class="media-left">
                                                <div class="round-img">
                                                    <a href="#"><img class="rounded-circle" src="/img/anonymous.jpg"
                                                                     width="64" alt=""></a>
                                                </div>
                                            </div>
                                            <div class="media-body" style="margin-left: 15px;">


                                                <div class="text-left" style="font-size: 20px;font-weight: bold">
                                                    my
                                                    <span style="float:right;border-radius: 50px;font-size: 14px"
                                                          class="badge badge-primary">4</span>
                                                </div>

                                                <p style="font-size: 12px">你好</p>
                                            </div>
                                        </div>

                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div> <!-- /.card-body -->
                </div><!-- /.card -->
            </div>


            <div class="col-md-8">


                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title box-title">聊天</h4>
                        <div class="card-content">
                            <div class="messenger-box">
                                <div style="height:400px;overflow-y: scroll" ;>
                                    <ul>
                                        <li v-for="message in messageList">
                                            <div class="msg-received msg-container">
                                                <div class="avatar">
                                                    <img src="/img/anonymous.jpg" alt="">
                                                    <div>
                                                        <div class="send-time">{{message.sendTime}}</div>
                                                    </div>

                                                </div>
                                                <div class="msg-box">
                                                    <div class="inner-box">
                                                        <div class="name">
                                                            小助手
                                                        </div>
                                                        <div class="meg">
                                                            {{message.content}}
                                                        </div>

                                                    </div>
                                                </div>
                                            </div><!-- /.msg-received -->
                                        </li>

                                        <#--<li>-->
                                            <#--<div class="msg-received msg-container">-->
                                                <#--<div class="avatar">-->
                                                    <#--<img src="/img/anonymous.jpg" alt="">-->
                                                    <#--<div>-->
                                                        <#--<div class="send-time">11.11 am</div>-->
                                                    <#--</div>-->

                                                <#--</div>-->
                                                <#--<div class="msg-box">-->
                                                    <#--<div class="inner-box"-->
                                                         <#--style="background-color: #03a9f3;color:white">-->
                                                        <#--<div class="name">-->
                                                            <#--我-->
                                                        <#--</div>-->
                                                        <#--<div class="meg">-->
                                                            <#--你好-->
                                                        <#--</div>-->

                                                    <#--</div>-->
                                                <#--</div>-->
                                            <#--</div><!-- /.msg-received &ndash;&gt;-->
                                        <#--</li>-->
                                        <#--<li>-->
                                            <#--<div class="msg-received msg-container">-->
                                                <#--<div class="avatar">-->
                                                    <#--<img src="/img/anonymous.jpg" alt="">-->
                                                    <#--<div>-->
                                                        <#--<div class="send-time">11.11 am</div>-->
                                                    <#--</div>-->

                                                <#--</div>-->
                                                <#--<div class="msg-box">-->
                                                    <#--<div class="inner-box">-->
                                                        <#--<div class="name">-->
                                                            <#--小助手-->
                                                        <#--</div>-->
                                                        <#--<div class="meg">-->
                                                            <#--你好，有什么可以帮您-->
                                                        <#--</div>-->

                                                    <#--</div>-->
                                                <#--</div>-->
                                            <#--</div><!-- /.msg-received &ndash;&gt;-->
                                        <#--</li>-->

                                        <#--<li>-->
                                            <#--<div class="msg-received msg-container">-->
                                                <#--<div class="avatar">-->
                                                    <#--<img src="/img/anonymous.jpg" alt="">-->
                                                    <#--<div>-->
                                                        <#--<div class="send-time">11.15 am</div>-->
                                                    <#--</div>-->

                                                <#--</div>-->
                                                <#--<div class="msg-box">-->
                                                    <#--<div class="inner-box"-->
                                                         <#--style="background-color: #03a9f3;color:white">-->
                                                        <#--<div class="name">-->
                                                            <#--我-->
                                                        <#--</div>-->
                                                        <#--<div class="meg">-->
                                                            <#--你，你是曾经的转笔机器人吗-->
                                                        <#--</div>-->

                                                    <#--</div>-->
                                                <#--</div>-->
                                            <#--</div><!-- /.msg-received &ndash;&gt;-->
                                        <#--</li>-->

                                        <#--<li>-->
                                            <#--<div class="msg-received msg-container">-->
                                                <#--<div class="avatar">-->
                                                    <#--<img src="/img/anonymous.jpg" alt="">-->
                                                    <#--<div>-->
                                                        <#--<div class="send-time">11.11 am</div>-->
                                                    <#--</div>-->

                                                <#--</div>-->
                                                <#--<div class="msg-box">-->
                                                    <#--<div class="inner-box">-->
                                                        <#--<div class="name">-->
                                                            <#--小助手-->
                                                        <#--</div>-->
                                                        <#--<div class="meg">-->
                                                            <#--是的，我将在新的岗位发光发热-->
                                                        <#--</div>-->

                                                    <#--</div>-->
                                                <#--</div>-->
                                            <#--</div><!-- /.msg-received &ndash;&gt;-->
                                        <#--</li>-->

                                    </ul>
                                </div>

                                <div class="send-mgs">
                                    <div class="yourmsg">
                                        <input class="form-control" type="text">
                                    </div>
                                    <button class="btn msg-send-btn">
                                        <i class="pe-7s-paper-plane"></i>
                                    </button>
                                </div>
                            </div><!-- /.messenger-box -->
                        </div>
                    </div> <!-- /.card-body -->
                </div><!-- /.card -->

            </div>


        </div>

        <div class="clearfix"></div>
        <!-- .animated -->
    </div>
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
<script src="/js/chat.js"></script>

</body>
</html>

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

        table tr:hover {
            background-color: #ccc;
            cursor: pointer;
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
                            <h1>与my聊天</h1>
                        </div>
                    </div>
                </div>
                <div class="col-sm-8">
                    <div class="page-header float-right">
                        <div class="page-title">
                            <ol class="breadcrumb text-right">
                                <li><a href="/home">主页</a></li>
                                <li><a href="/friends">笔友</a></li>
                                <li class="active">聊天</li>
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
                                <table class="table ">
                                    <tr v-for="message in userMessageList" >
                                        <td :onclick="'chat.chat('+message.oppositeSideId+')'">

                                            <div class="media" >
                                                <div class="media-left" >
                                                    <div class="round-img">
                                                        <a href="#"><img class="rounded-circle"
                                                                         :src="message.oppositeSideUserInfo.profile"
                                                                         width="64" alt=""></a>
                                                    </div>
                                                </div>
                                                <div class="media-body" style="margin-left: 15px;">
                                                    <div class="text-left" style="font-size: 20px;font-weight: bold">
                                                        {{message.oppositeSideUserInfo.nickName}}
                                                        <span v-show="message.msgCount > 0" style="float:right;border-radius: 50px;font-size: 14px"
                                                              class="badge badge-primary">{{message.msgCount}}</span>
                                                    </div>

                                                    <p style="font-size: 12px">{{message.newestMsg}}</p>
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
                                    <div id="msgPane"
                                         style="height:400px;overflow-y: scroll;padding:20px;border:1px #ccc solid;border-radius: 5px">
                                        <ul>
                                            <li v-for="message in messageList">
                                                <div class="msg-received msg-container">
                                                    <div class="avatar">
                                                        <img :src="message.senderInfo.profile" alt="">
                                                        <div>
                                                            <div class="send-time">{{message.sendTime}}</div>
                                                        </div>

                                                    </div>
                                                    <div class="msg-box">
                                                        <div class="inner-box"
                                                             :class="{self:message.senderId != friendId}">
                                                            <div class="name">
                                                                {{message.senderInfo.nickName}}
                                                            </div>
                                                            <div class="meg" style="word-break: break-all">
                                                                {{message.content}}
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div><!-- /.msg-received -->
                                            </li>


                                        </ul>
                                    </div>

                                    <div class="send-mgs">
                                        <div class="yourmsg" style="border:1px #ccc solid;border-radius: 5px">
                                            <input class="form-control" type="text" v-model="content"
                                                   @keyup.enter="sendMessage">
                                        </div>
                                        <button class="btn msg-send-btn" @click="sendMessage">
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
<script src="/js/chat.js"></script>

</body>
</html>
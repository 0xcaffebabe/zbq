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
                            <h1>笔友</h1>
                        </div>
                    </div>
                </div>
                <div class="col-sm-8">
                    <div class="page-header float-right">
                        <div class="page-title">
                            <ol class="breadcrumb text-right">
                                <li><a href="#">主页</a></li>

                                <li class="active">笔友</li>
                            </ol>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>






    <!-- Content -->
    <div class="content">
        <div class="animated fadeIn">




            <div class="row"  id="friends">

                <div class="modal fade" id="staticModal" tabindex="-1" role="dialog" aria-labelledby="staticModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-sm" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="staticModalLabel">添加好友</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <p>
                                    发送验证消息:
                                </p>

                                <textarea class="form-control" v-model="validMsg"></textarea>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                                <button type="button" class="btn btn-primary" @click="addFriend" data-dismiss="modal">添加</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-7">


                    <div class="card">
                        <div class="card-header">
                            <strong class="card-title">笔友列表</strong>


                                <div class="form-group form-inline" style="float: right">
                                    <input type="text" class="form-control" placeholder="根据昵称模糊搜索" v-model="friendSearch">
                                    <button class="btn btn-sm btn-success form-control" @click="searchFriend"><span class="fa fa-search"></span>搜索好友</button>
                                </div>


                        </div>
                        <div class="table-stats order-table ov-h">
                            <table class="table ">
                                <thead>
                                <tr>
                                    <th class="serial">#</th>
                                    <th>头像</th>
                                    <th>昵称</th>
                                    <th>笔龄</th>
                                    <th class="text-center">操作</th>
                                </tr>
                                </thead>
                                <tbody>

                                <tr v-for="friend in friendList">
                                    <td class="serial">{{friend.friendUserId}}.</td>
                                    <td>
                                        <div class="round-img">
                                            <a href="#"><img class="rounded-circle" :src="friend.friendUserInfo.profile" alt=""></a>
                                        </div>
                                    </td>

                                    <td>  <span>{{friend.friendUserInfo.nickName}}</span> </td>
                                    <td> <span class="product">{{friend.friendUserInfo.penYear}} 年</span> </td>

                                    <td>
                                        <div class="text-center btn-group">

                                            <button class="btn btn-sm btn-success">聊天</button>
                                            <button class="btn btn-sm btn-danger">删除</button>
                                        </div>


                                    </td>
                                </tr>

                                </tbody>
                            </table>
                        </div> <!-- /.table-stats -->
                    </div>
                    <div>
                        <div class="card">
                            <div class="card-header">
                                <strong class="card-title">好友添加请求</strong>

                            </div>
                            <div class="table-stats table-striped ov-h">
                                <table class="table ">
                                    <thead>
                                    <tr>

                                        <th>头像</th>
                                        <th>昵称</th>
                                        <th>验证消息</th>
                                        <th class="text-center">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <tr v-for="friend in friendAddList">

                                        <td>
                                            <div class="round-img">
                                                <a href="#"><img class="rounded-circle" :src="friend.userInfo.profile" alt=""></a>
                                            </div>
                                        </td>

                                        <td>  <span>{{friend.userInfo.nickName}}</span> </td>
                                        <td>{{friend.msg}}</td>

                                        <td>
                                            <div class="text-center btn-group">
                                                <button class="btn btn-sm btn-primary" :data-id="friend.friendAddId" @click="agreeFriendAdd">同意</button>
                                                <button class="btn btn-sm btn-danger">拒绝</button>
                                            </div>


                                        </td>
                                    </tr>

                                    </tbody>
                                </table>
                            </div> <!-- /.table-stats -->
                        </div>
                    </div>
                </div>

                <div class="col-md-5">
                    <div class="card">
                        <div class="card-header">
                            <strong class="card-title">推荐</strong>
                            <div class="form-group form-inline" style="float: right">
                                <input type="text" class="form-control" placeholder="根据昵称模糊搜索" v-model="strangerSearch">
                                <div></div>
                                <button class="btn btn-sm btn-primary form-control" @click="searchStranger"><span class="fa fa-search"></span>搜索陌生人</button>
                            </div>
                        </div>
                        <div class="table-stats order-table ov-h">
                            <table class="table ">
                                <thead>
                                <tr>
                                    <th class="serial">#</th>
                                    <th>头像</th>
                                    <th>昵称</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>

                                <tr v-for="friend in recommendFriendList">
                                    <td class="serial">{{friend.friendUserId}}.</td>
                                    <td>
                                        <div class="round-img">
                                            <a href="#"><img class="rounded-circle" :src="friend.friendUserInfo.profile" alt=""></a>
                                        </div>
                                    </td>
                                    <td>  <span class="name">{{friend.friendUserInfo.nickName}}</span> </td>
                                    <td>
                                        <button class="btn btn-sm btn-primary" @click="showFriendAddDialog" :data-to="friend.friendUserId" data-toggle="modal" data-target="#staticModal">加为好友</button>

                                    </td>
                                </tr>


                                </tbody>
                            </table>
                        </div> <!-- /.table-stats -->
                    </div>
                </div>

            </div>

            <#--modal-->




            <#--modal-->
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
<script src="js/friends.js"></script>


</body>

x
</html>

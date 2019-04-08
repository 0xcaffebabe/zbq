<!doctype html>

<!--[if gt IE 8]><!-->
<html class="no-js" lang=""> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>测试主页</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <#include "css.ftl"/>


</head>

<body>


<!-- Content -->
<div class="content">
    <div class="animated fadeIn">

        <div class="row" id="friends">



            <div class="modal fade"  id="searchFriendModal" tabindex="-1" role="dialog" aria-labelledby="staticModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog modal-lg" style="z-index: 99" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="staticModalLabel">搜索好友</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">


                                <div class="form-group">

                                        <input type="text" class="form-control"placeholder="昵称" v-model="strangerSearch" @keyup.enter="searchStranger">
                                        <button class="btn btn-sm btn-primary form-control" @click="searchStranger">搜索</button>
                                </div>

                            <div v-show="strangerList.length != 0">
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

                                    <tr v-for="stranger in strangerList">
                                        <td class="serial">{{stranger.friendUserId}}.</td>
                                        <td>
                                            <div class="round-img">
                                                <a href="#"><img class="rounded-circle" width="48" :src="stranger.friendUserInfo.profile"
                                                                 alt=""></a>
                                            </div>
                                        </td>
                                        <td><span class="name">{{stranger.friendUserInfo.nickName}}</span></td>
                                        <td>
                                            <button class="btn btn-sm btn-info" @click="showFriendAddDialog(stranger)">加为好友
                                            </button>

                                        </td>
                                    </tr>


                                    </tbody>

                                </table>

                                <button class="btn btn-sm btn-primary form-control" @click="loadMoreStranger">加载更多</button>

                            </div>


                        </div>
                        <div class="modal-footer">

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
                            <button class="btn btn-sm btn-success form-control" @click="searchFriend"><span
                                    class="fa fa-search"></span>搜索好友
                            </button>

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
                                        <a href="#"><img class="rounded-circle" :src="friend.friendUserInfo.profile"
                                                         alt=""></a>
                                    </div>
                                </td>

                                <td><span>{{friend.friendUserInfo.nickName}}</span></td>
                                <td><span class="product">{{friend.friendUserInfo.penYear}} 年</span></td>

                                <td>
                                    <div class="text-center btn-group">

                                        <button class="btn btn-sm btn-success" @click="chat"
                                                :data-id="friend.friendUserId">聊天
                                        </button>
                                        <button class="btn btn-sm btn-danger">删除</button>
                                    </div>
                                </td>
                            </tr>


                            <tr>


                                <td>共{{friendQuantity}}位笔友</td>
                                <td>当前第{{friendPage}}页</td>

                                <td>
                                    <button class="btn  btn-primary" @click="friendPrevPage"><span
                                            class="fa fa-arrow-left"></span></button>
                                </td>
                                <td>
                                    <button class="btn  btn-primary" @click="friendNextPage"><span
                                            class="fa fa-arrow-right"></span></button>
                                </td>

                            </tr>
                            </tbody>

                        </table>
                    </div> <!-- /.table-stats -->
                </div>

            </div>

            <div class="col-md-5">
                <div class="card">
                    <div class="card-header">
                        <strong class="card-title">好友添加请求</strong>
                        <button class="btn btn-sm btn-info" style="float: right;" data-target="#searchFriendModal" data-toggle="modal">添加笔友</button>
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
                                        <a href="#"><img class="rounded-circle" :src="friend.userInfo.profile"
                                                         alt=""></a>
                                    </div>
                                </td>

                                <td><span>{{friend.userInfo.nickName}}</span></td>
                                <td>{{friend.msg}}</td>

                                <td>
                                    <div class="text-center btn-group">
                                        <button class="btn btn-sm btn-primary" :data-id="friend.friendAddId"
                                                @click="agreeFriendAdd">同意
                                        </button>
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

    <#--modal-->


    <#--modal-->
    </div><!-- .animated -->


</div><!-- .content -->


<#include "script.ftl"/>
<script src="js/friends.js"></script>


</body>

</html>

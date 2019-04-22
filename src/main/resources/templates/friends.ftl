<!doctype html>

<!--[if gt IE 8]><!-->
<html class="no-js" lang=""> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>测试主页</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <#include "css.ftl"/>

    <style>

        .list-enter-active, .list-leave-active {
            transition: all 1s;
        }

        .list-enter, .list-leave-to
            /* .list-leave-active for below version 2.1.8 */
        {
            opacity: 0;
            transform: translateY(30px);
        }
    </style>

</head>

<body>


<!-- Content -->
<div class="content">
    <div class="animated fadeIn">

        <div class="row" id="friends">


            <div class="modal fade" id="searchFriendModal" tabindex="-1" role="dialog"
                 aria-labelledby="staticModalLabel"
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

                                <input type="text" class="form-control" placeholder="昵称" v-model="strangerSearch"
                                       @keyup.enter="searchStranger">
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
                                                <a href="#"><img class="rounded-circle" width="48"
                                                                 :src="stranger.friendUserInfo.profile"
                                                                 alt=""></a>
                                            </div>
                                        </td>
                                        <td><span class="name">{{stranger.friendUserInfo.nickName}}</span></td>
                                        <td>
                                            <button class="btn btn-sm btn-info" @click="showFriendAddDialog(stranger)">
                                                加为好友
                                            </button>

                                        </td>
                                    </tr>


                                    </tbody>

                                </table>

                                <button class="btn btn-sm btn-primary form-control" @click="loadMoreStranger">加载更多
                                </button>

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
                    <div class="card-body">

                        <ul class="media-list">
                            <transition-group name="list">
                                <li class="media" style="margin-top: 15px" v-for="(friend,index) in friendList"
                                    :key="index">
                                    <div class="media-left">
                                        <a href="#">
                                            <img class="media-object" :src="friend.friendUserInfo.profile" alt="..."
                                                 width="48">
                                        </a>
                                    </div>

                                    <div class="media-body" style="margin-left: 15px;">

                                        <h4 class="media-heading">{{friend.friendUserInfo.nickName}}
                                            <span class="badge badge-danger"><i class="fa fa-heart"></i> {{friend.friendUserInfo.penYear}}年</span>
                                        </h4>

                                        <p>[在线]这个人很懒，没有留下什么</p>


                                    </div>
                                    <button class="btn btn-sm btn-success" @click="chat" style="float: right"
                                            :data-id="friend.friendUserId"><i class="fa fa-envelope"></i> 聊天

                                </li>
                            </transition-group>
                        </ul>

                        <div class="row">
                            <div class="col-md-8 col-xs-6 col-sm-6">共{{friendQuantity}}位笔友,当前第{{friendPage}}页</div>
                            <div class="col-md-2 col-xs-3 col-sm-3">
                                <button class="btn  btn-primary btn-sm" @click="friendPrevPage"><span
                                        class="fa fa-arrow-left"></span></button>
                            </div>
                            <div class="col-md-2 col-xs-3 col-sm-3">
                                <button class="btn  btn-primary btn-sm" @click="friendNextPage"><span
                                        class="fa fa-arrow-right"></span></button>
                            </div>
                        </div>
                    </div> <!-- /.table-stats -->
                </div>

                <div class="card">
                    <div class="card-header">
                        <button class="btn btn-default btn-sm" style="float: right" @click="getRecommendFriendList">
                            换一批
                        </button>
                        <strong>好友推荐</strong>
                    </div>

                    <div class="card-body">
                        <ul class="media-list">

                            <transition-group name="list">
                                <li class="media" style="margin-top: 15px" v-for="(friend,index) in recommendFriendList"
                                    :key="index" class="list-item">
                                    <div class="media-left">
                                        <a href="#">
                                            <img class="media-object" :src="friend.profile" alt="..."
                                                 width="48">
                                        </a>
                                    </div>

                                    <div class="media-body" style="margin-left: 15px;">

                                        <h4 class="media-heading">{{friend.nickName}} <span
                                                style="color:#878787;font-size: 16px;">来自：{{friend.source}}</span></h4>

                                        <span class="badge badge-primary" v-if="friend.gender == 1"
                                              title="男"><i class="fa fa-male"></i></span>
                                        <span class="badge badge-warning" v-if="friend.gender == 0"
                                              title="未知"><i class="fa fa-transgender"></i></span>
                                        <span class="badge badge-danger" v-if="friend.gender == -1"
                                              title="女"><i class="fa fa-female"></i></span>
                                        <span class="badge badge-info"><i class="fa fa-globe"></i> {{friend.region}}</span>
                                        <span class="badge badge-primary"><i class="fa fa-heart"></i> {{friend.penYear}}年</span>

                                    </div>
                                    <button class="btn btn-info btn-sm" style="float: right"
                                            @click="showFriendAddDialog(friend)">
                                        加为好友
                                    </button>
                                </li>

                            </transition-group>

                        </ul>
                    </div>
                </div>

            </div>

            <div class="col-md-5">
                <div class="card">
                    <div class="card-header">
                        <strong class="card-title">好友添加请求</strong>
                        <button class="btn btn-sm btn-info" style="float: right;" data-target="#searchFriendModal"
                                data-toggle="modal">添加笔友
                        </button>
                    </div>


                    <div class="card-body">
                        <ul class="media-list">

                            <transition-group name="list">
                                <li class="media" style="margin-top: 15px" v-for="(friend,index) in friendAddList"
                                    :key="index">
                                    <div class="media-left">
                                        <a href="#">
                                            <img class="media-object" :src="friend.userInfo.profile" alt="..."
                                                 width="48">
                                        </a>
                                    </div>

                                    <div class="media-body" style="margin-left: 15px;">


                                        <div class="text-center btn-group" v-if="friend.visible" style="float: right">
                                            <button class="btn btn-sm btn-primary" :data-id="friend.friendAddId"
                                                    @click="agreeFriendAdd">同意
                                            </button>
                                            <button class="btn btn-sm btn-danger" @click="rejectFriendAdd(friend)">拒绝
                                            </button>
                                        </div>

                                        <span v-if="!friend.visible" style="float: right">已失效</span>
                                        <h4 class="media-heading">{{friend.userInfo.nickName}} <span
                                                style="color:#878787;font-size: 16px;">来自：查找</span></h4>

                                        <span class="badge badge-primary" v-if="friend.userInfo.gender == 1"
                                              title="男"><i
                                                class="fa fa-male"></i></span>
                                        <span class="badge badge-warning" v-if="friend.userInfo.gender == 0" title="未知"><i
                                                class="fa fa-transgender"></i></span>
                                        <span class="badge badge-danger" v-if="friend.userInfo.gender == -1"
                                              title="女"><i
                                                class="fa fa-female"></i></span>
                                        <span class="badge badge-info"><i class="fa fa-globe"></i> {{friend.userInfo.region}}</span>
                                        <span class="badge badge-primary"><i class="fa fa-heart"></i> {{friend.userInfo.penYear}}年</span>
                                        <strong>验证消息:</strong><span
                                            style="color: #878787;font-size: 16px">{{friend.msg}}</span>


                                    </div>

                                </li>
                            </transition-group>

                        </ul>
                    </div>
                </div>

            </div>


        </div>

    <#--modal-->


    <#--modal-->
    </div><!-- .animated -->


</div><!-- .content -->


<#include "script.ftl"/>
<script src="js/friends.js?v=2019041302"></script>


</body>

</html>

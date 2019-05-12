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

        .self {
            background-color: #03a9f3 !important;
            color: white !important;
        }

    </style>

</head>

<body>


<!-- Content -->
<div class="content">
    <div class="animated fadeIn">

        <div class="row" id="square">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title box-title">转笔广场</h4>
                        <div class="card-content">
                            <div class="messenger-box">
                                <div id="msgPane"
                                     style="height:600px;overflow-y: scroll;padding:20px;border:1px #ccc solid;border-radius: 5px">
                                    <ul>
                                        <li v-for="broadcast in broadcastList">
                                            <div class="msg-received msg-container">
                                                <div class="avatar">
                                                    <a :href="'/space/' + broadcast.user.userId">
                                                        <img :src="broadcast.user.profile" alt="">
                                                    </a>

                                                    <div>
                                                        <div class="send-time">{{timeConvert(broadcast.createTime)}}</div>
                                                    </div>

                                                </div>
                                                <div class="msg-box" >
                                                    {{broadcast.user.nickName}} <span class="badge badge-info"><i class="fa fa-globe"></i>{{broadcast.user.region}}</span>
                                                    <span class="badge badge-danger"><i class="fa fa-heart"></i>{{broadcast.user.penYear}}年</span>
                                                    <div class="inner-box" style="margin-top: 10px;"
                                                         :class="{self:broadcast.user.userId == user.userId}">
                                                        <div class="name">

                                                        </div>
                                                        <div class="meg" style="word-break: break-all">
                                                            {{broadcast.content}}
                                                        </div>

                                                    </div>
                                                </div>
                                            </div><!-- /.msg-received -->
                                        </li>



                                    </ul>
                                </div>

                                <div class="send-mgs">
                                    <div class="yourmsg" style="border:1px #ccc solid;border-radius: 5px">
                                <textarea class="form-control" type="text" v-model="content"
                                          @keyup.enter="broadcast"></textarea>
                                    </div>
                                    <button class="btn msg-send-btn" @click="broadcast">
                                        <i class="pe-7s-paper-plane"></i>
                                    </button>
                                </div>
                            </div><!-- /.messenger-box -->
                        </div>
                    </div> <!-- /.card-body -->
                </div><!-- /.card -->
            </div>

            <div class="col-md-4">
                <div class="card">
                    <div class="card-header">
                        {{userList.length}}位在线PSER
                    </div>
                    <div class="card-body">
                        <ul class="media-list">
                            <li class="media" v-for="user in userList" style="margin-top: 10px;">
                                <div class="media-left">
                                    <a href="#">
                                        <img class="media-object" :src="user.profile" alt="..." width="48">
                                    </a>
                                </div>
                                <div class="media-body" style="margin-left: 8px;">
                                    <h4 class="media-heading">{{user.nickName}}</h4>
                                    <span class="badge badge-info"><i class="fa fa-globe"></i>{{user.region}}</span>
                                    <span class="badge badge-primary"><i class="fa fa-male"></i></span>
                                    <span class="badge badge-danger"><i class="fa fa-heart"></i> {{user.penYear}}年</span>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>



    </div><!-- .animated -->


</div><!-- .content -->


<#include "script.ftl"/>
<script src="/js/square.js?v=2019042802"></script>

</body>

</html>

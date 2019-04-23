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

        <div class="row">
            <div class="col-md-9">
                <div class="card">
                    <div class="card-body">
                        <h4 class="card-title box-title">转笔广场</h4>
                        <div class="card-content">
                            <div class="messenger-box">
                                <div id="msgPane"
                                     style="height:600px;overflow-y: scroll;padding:20px;border:1px #ccc solid;border-radius: 5px">
                                    <ul>
                                        <li>
                                            <div class="msg-received msg-container">
                                                <div class="avatar">
                                                    <img src="/img/anonymous.jpg" alt="">
                                                    <div>
                                                        <div class="send-time">3秒前</div>
                                                    </div>

                                                </div>
                                                <div class="msg-box" >
                                                    My、 <span class="badge badge-info"><i class="fa fa-globe"></i>福建泉州</span>
                                                    <div class="inner-box" style="margin-top: 10px;"
                                                         :class="{self:message.senderId != friendId}">
                                                        <div class="name">

                                                        </div>
                                                        <div class="meg" style="word-break: break-all">
                                                            hello，anybody
                                                        </div>

                                                    </div>
                                                </div>
                                            </div><!-- /.msg-received -->
                                        </li>

                                        <li>
                                            <div class="msg-received msg-container">
                                                <div class="avatar">
                                                    <img src="/img/anonymous.jpg" alt="">
                                                    <div>
                                                        <div class="send-time">3秒前</div>
                                                    </div>

                                                </div>
                                                <div class="msg-box" >
                                                    My、 <span class="badge badge-info"><i class="fa fa-globe"></i>福建泉州</span>
                                                    <div class="inner-box self" style="margin-top: 10px;"
                                                    >
                                                        <div class="name">

                                                        </div>
                                                        <div class="meg" style="word-break: break-all">
                                                            hello，anybody
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
                                          @keyup.enter="sendMessage"></textarea>
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

            <div class="col-md-3">
                <div class="card">
                    <div class="card-header">
                        在线PSER
                    </div>
                    <div class="card-body">
                        <ul class="media-list">
                            <li class="media">
                                <div class="media-left">
                                    <a href="#">
                                        <img class="media-object" src="/img/anonymous.jpg" alt="..." width="48">
                                    </a>
                                </div>
                                <div class="media-body" style="margin-left: 8px;">
                                    <h4 class="media-heading">My、</h4>
                                    <span class="badge badge-info"><i class="fa fa-globe"></i>福建泉州</span>
                                    <span class="badge badge-primary"><i class="fa fa-male"></i></span>
                                    <span class="badge badge-danger"><i class="fa fa-heart"></i> 99年</span>
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


</body>

</html>

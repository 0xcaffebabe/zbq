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
        .line-limit-length {

            overflow: hidden;

            text-overflow: ellipsis;

            white-space: nowrap;

        }

        .dropdown-menu li {
            padding: 3px;

        }

        .dropdown-menu {
            border-radius: 5px;
            box-shadow: 2px 2px 8px;
        }

        [v-cloak]{
            display: none;
        }
    </style>
</head>

<body>


<!-- Content -->
<div class="content">
    <div class="animated fadeIn">

        <div class="row">

            <div class="col-md-6" id="friend" v-cloak>
                <aside class="profile-nav alt">
                    <section class="card">
                        <div class="card-header user-header alt bg-dark">
                            <div class="media">

                                <div class="media-left">

                                    <a href="#">
                                        <img class="align-self-center rounded-circle mr-3" style="width:64px; height:64px;" alt="" :src="info.profile">
                                    </a>

                                </div>

                                <div class="media-body">
                                    <h4 class="text-light display-6">{{info.nickName}}</h4>

                                    <p>不知名转笔者</p>
                                </div>

                                <!-- Single button -->
                                <div class="btn-group">
                                    <button type="button" class="btn btn-outline-secondary btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        设置
                                    </button>
                                    <ul class="dropdown-menu" style="margin-left: -100px">
                                        <li><a href="#" class="btn btn-block btn-outline-danger" @click.prevent="deleteFriend">删除</a></li>

                                    </ul>
                                </div>
                            </div>

                            <div class="media-bottom">
                                <p class="line-limit-length text-center" style="width: 90%">{{info.description}}</p>
                            </div>

                        </div>


                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">
                                <a href="#"> <i class="fa fa-map-marker" style="color:#3498db"></i> {{info.region}} </a>
                            </li>
                            <li class="list-group-item">
                                <a href="#"> <i class="fa fa-birthday-cake" style="color:#f1c40f"></i> {{info.birthday}} </a>
                            </li>
                            <li class="list-group-item">
                                <a href="#"> <i class="fa fa-heart" style="color: #c0392b"></i> {{info.penYear}}年 </a>
                            </li>
                            <li class="list-group-item">
                                <a href="#" v-if="info.gender === 1"> <i class="fa fa-transgender-alt" style="color:#3498db"></i> 男</a>
                                <a href="#" v-if="info.gender === -1"> <i class="fa fa-transgender-alt" style="color:#ff7675"></i> 女</a>
                                <a href="#" v-if="info.gender === 0"> <i class="fa fa-transgender-alt" style="color:#6c5ce7"></i> 未知</a>
                            </li>
                            <li class="list-group-item">
                                <a href="#"> <i class="fa fa-user" style="color:#27ae60"></i> {{info.joinDate}}</a>
                            </li>
                        </ul>

                        <div class="row" style="">
                            <div class="col-md-6 col-sm-6 col-xs-6">
                                <button class="btn btn-block btn-primary" @click="chat">发送消息</button>
                            </div>

                            <div class="col-md-6 col-sm-6 col-xs-6">
                                <button class="btn btn-block btn-default" @click="space">进入主页</button>
                            </div>


                        </div>
                    </section>
                </aside>
            </div>


            <div class="col-md-6">
                <aside class="profile-nav alt">
                    <section class="card">
                        <div style="background-color: #58C9F3">

                            <div style="height: 50px"></div>

                            <div class="media">

                                <div class="media-body">
                                    <h2 class="text-white display-6">成就面板</h2>
                                    <p class="text-light">初学乍练</p>
                                </div>
                            </div>

                        </div>


                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">
                                <a href="#"> <i class="fa fa-envelope-o"></i> Mail Inbox <span class="badge badge-primary pull-right">10</span></a>
                            </li>
                            <li class="list-group-item">
                                <a href="#"> <i class="fa fa-tasks"></i> Recent Activity <span class="badge badge-danger pull-right">15</span></a>
                            </li>
                            <li class="list-group-item">
                                <a href="#"> <i class="fa fa-bell-o"></i> Notification <span class="badge badge-success pull-right">11</span></a>
                            </li>
                            <li class="list-group-item">
                                <a href="#"> <i class="fa fa-comments-o"></i> Message <span class="badge badge-warning pull-right r-activity">03</span></a>
                            </li>
                        </ul>

                    </section>
                </aside>
            </div>



        </div>
    </div><!-- .animated -->


</div><!-- .content -->


<#include "script.ftl"/>
<script>
    var id = location.pathname.replace("/user/id/","");

    var friend = new Vue({
        el:"#friend",
        data:{
            info:{}
        }
        ,
        created:function () {
            this.getInfo();
        }

        ,
        methods:{
            getInfo:function () {
                common.ajax.get(common.data.getFriendInfoUrl+id,(r)=>{
                    if (r.success){
                        this.info = r.data;
                    }else{
                        alert(r.msg);
                        window.history.back()
                    }
                });
            }
            ,
            chat:function () {
                location = "/chat/"+id;
            }
            ,
            space:function () {
                location= "/space/"+id;
            }
            ,
            deleteFriend:function () {

                if (confirm("确定要删除"+this.info.nickName+"？")){
                    common.ajax.delete(common.data.getFriendInfoUrl+id,(r)=>{
                        if (r.success){
                            alert(r.data);
                            window.history.back();
                        }else{
                            alert(r.msg);
                        }
                    });
                }
            }

        }
    })
</script>
</body>

</html>

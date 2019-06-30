<!doctype html>

<!--[if gt IE 8]><!-->
<html class="no-js" lang=""> <!--<![endif]-->
<script>

</script>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>我的空间</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="manifest" href="/manifest.json" />
    <#include "css.ftl">

    <style>

        .split{
            font-weight: bold;
            font-size: 16px;
            color:#607d8b;
        }

        .line-limit-length {

            overflow: hidden;

            text-overflow: ellipsis;

            white-space: nowrap;

        }
    </style>
</head>

<body>

<aside id="left-panel" class="left-panel">
    <nav class="navbar navbar-expand-sm navbar-default" id="side">
        <div id="main-menu" class="main-menu collapse navbar-collapse">
            <ul class="nav navbar-nav">


                <li :class="{active:url.startsWith('/space/${user.userId?c}')}"><a href="javascript:jump('/space/${user.userId?c}')"><i class="menu-icon fa fa-home"></i>我的主页 </a></li>


                <li :class="{active:url=='/home'}">
                    <a href="javascript:jump('/home')"><i class="menu-icon fa fa-user"></i>用户中心 </a>
                </li>

                <li :class="{active:url=='/account'}">
                    <a href="javascript:jump('/account')"><i class="menu-icon fa fa-gears"></i>设置中心 </a>
                </li>


                <li class="text-sm-center split">
                    社交

                </li>

                <li :class="{active:url == '/friends'}"><a href="javascript:jump('/friends')"> <i
                        class="menu-icon fa fa-users"></i>我的笔友 </a></li>
                <li :class="{active:url == '/states'}"><a href="javascript:jump('/states')"> <i
                        class="menu-icon fa fa-star"></i> 笔圈动态</a></li>

                <li ><a > <i
                        class="menu-icon fa fa-at"></i> 与我相关（不可用）</a></li>
                <li :class="{active:url == '/maps'}"><a href="javascript:jump('/maps')" class="dropdown-toggle"> <i
                        class="menu-icon fa fa-street-view"></i>转笔地图</a></li>

                <li :class="{active:url == '/square'}"><a href="javascript:jump('/square')"  class="dropdown-toggle"> <i
                        class="menu-icon fa fa-microphone"></i>转笔广场</a></li>

                <li class="text-sm-center split">内容</li>


                <li :class="{active:url == '/videos'}"><a href="javascript:jump('/videos')"> <i
                        class="menu-icon fa fa-internet-explorer"></i>全网转笔</a></li>

                <li :class="{active:url == '/contents'}"><a href="javascript:jump('/contents')"> <i
                        class="menu-icon fa fa-search"></i>发现内容</a></li>
                <li :class="{active:url == '/publish/content'}">
                    <a href="javascript:jump('/publish/content')"> <i class="menu-icon fa fa-plus"></i>发布内容</a>
                </li>

                <li :class="{active:url == '/collections'}">
                    <a href="javascript:jump('/collections')"> <i class="menu-icon fa fa-heart"></i>我的收藏（开发中）</a>
                </li>


                <li class="text-sm-center split">学习</li><!-- /.menu-title -->

                <li :class="{active:url == '/courses'}"><a href="javascript:jump('/courses')"> <i
                        class="menu-icon fa fa-book"></i>课程中心</a></li>

                <li :class="{active:url == '/learnings'}"><a href="javascript:jump('/learnings')"> <i
                        class="menu-icon fa fa-bookmark"></i>我的课程</a></li>

                <li :class="{active:url == '/publish/course'}">
                    <a href="javascript:jump('/publish/course')"> <i class="menu-icon fa fa-plus"></i>发布课程</a>
                </li>




            </ul>
        </div><!-- /.navbar-collapse -->
    </nav>
</aside>

<!-- Left Panel -->

<!-- /#left-panel -->
<!-- Right Panel -->
<div id="right-panel" class="right-panel">
    <!-- Header-->
    <header id="header" class="header">
        <div class="top-left">
            <div class="navbar-header">
                <a  class="navbar-brand">Zbq</a>
                <a id="menuToggle" class="menutoggle"><i class="fa fa-bars"></i></a>
            </div>
        </div>
        <div class="top-right">
            <div class="header-menu">
                <div class="header-left">

                    <button class="search-trigger"><i class="fa fa-search"></i></button>
                    <div class="form-inline">
                        <form class="search-form">
                            <input class="form-control mr-sm-2" type="text" placeholder="Search ..."
                                   aria-label="Search">
                            <button class="search-close" type="submit"><i class="fa fa-close"></i></button>
                        </form>
                    </div>


                    <div class="dropdown">
                        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                            主题
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                            <li><a href="#">黑色</a></li>
                            <li><a href="#">白色</a></li>
                            <li><a href="#">Something else here</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">Separated link</a></li>
                        </ul>
                    </div>

                    <div class="dropdown for-message" id="unread">
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="message"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fa fa-envelope"></i>
                            <span class="count bg-primary">{{unreadCount}}</span>
                        </button>
                        <div class="dropdown-menu" aria-labelledby="message">
                            <p class="red">你有 {{unreadCount}} 条新消息</p>
                            <div v-for="unread in unreadMessageList">
                                <a class="dropdown-item media" href="#"
                                   @click.prevent="jump('/chat/' + unread.fromUserId)">
                                    <span class="photo media-left "><img alt="avatar"
                                                                         :src="unread.fromUserInfo.profile"></span>
                                    <div class="message media-body">
                                        <span class="name float-left">{{unread.fromUserInfo.nickName}}</span>
                                        <span class="time float-right">{{unread.sendTime}}</span>
                                        <p class="line-limit-length" style="width: 240px">{{unread.newestMsg}}</p>
                                    </div>
                                </a>
                            </div>

                        </div>
                    </div>
                </div>

                <div class="user-area dropdown float-right">
                    <a href="#" class="dropdown-toggle active" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false">
                        <img class="user-avatar rounded-circle" :src="profile" alt="User Avatar">
                    </a>

                    <div class="user-menu dropdown-menu">
                        <a class="nav-link" href="#"><i class="fa fa- user"></i>My Profile</a>

                        <a class="nav-link" href="#"><i class="fa fa- user"></i>Notifications <span
                                class="count">13</span></a>

                        <a class="nav-link" href="#"><i class="fa fa -cog"></i>Settings</a>

                        <a class="nav-link" href="javascript:logout()"><i class="fa fa-power -off"></i>注销</a>
                    </div>
                </div>

            </div>
        </div>
    </header>


    <div>
        <iframe id="framework" src="/home" style="width: 100%;height: 700px;border:none"></iframe>
    </div>


</div>
<!-- /#right-panel -->

<#include "script.ftl"/>
<script>


    function logout() {
        common.ajax.delete(common.data.logoutUrl, function (response) {
            localStorage.removeItem("username");
            localStorage.removeItem("password");
            window.location = "/";
        });
    }

    var header = new Vue({
        el: "#header",
        created: function () {
            this.getInfo();
            moment.locale("zh-cn");
            this.getUnreadMessageList();
        },
        data: {
            profile: '',
            unreadMessageList: [],
            unreadCount: 0,
            userId: 0
        },
        methods: {
            getInfo: function () {
                var that = this;
                common.ajax.get(common.data.getCurrentUserDataUrl, function (response) {
                    if (response.success) {
                        that.profile = response.data.profile;
                        that.userId = response.data.userId;

                    } else {
                        alert(response.msg);
                    }
                })

            }
            ,
            getUnreadMessageList: function () {

                this.unreadCount = 0;
                var that = this;
                common.ajax.get(common.data.getUnreadMessageListUrl, function (response) {
                    if (response.success) {

                        var list = response.data;
                        for (var i = 0; i <= list.length; i++) {

                            for (var key in list[i]) {
                                if (key == 'msgCount') {
                                    that.unreadCount += list[i][key];
                                } else if (key == 'sendTime') {
                                    list[i][key] = moment(list[i][key]).fromNow();
                                }
                            }


                        }

                        that.unreadMessageList = list;
                    } else {
                        alert("获取未读消息列表失败:" + response.msg);
                    }
                })
            }
            ,
            jump: function (url) {
                framework.src = url;
                nav.url = url;
                var that = this;
                setTimeout(function () {
                    that.getUnreadMessageList();
                }, 5000);

            }
        }
    })

    var nav = new Vue({
        el: "#side",
        data: {
            url: location.pathname
        }
    });

    function jump(url) {
        framework.src = url;
        nav.url = url;

    }

    $('.navbar-nav>li>a').on('click', function () {

        if ($("body").attr("class") == 'small-device') {
            $("#menuToggle")[0].click();
        }
        console.log("click");


    });
</script>


</body>
</html>

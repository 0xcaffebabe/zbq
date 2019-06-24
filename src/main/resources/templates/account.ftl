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
        [v-cloak] { display: none }
    </style>
</head>

<body>

<!-- Content -->
<div class="content">
    <!-- Animated -->



    <div class="animated fadeIn" id="addLesson">
        <div class="row" id="account" v-cloak>
            <div class="col-md-8">
                <div class="card" >
                    <div class="card-header">
                        账户绑定
                    </div>
                    <div class="card-body">
                        <div class="input-group">

                            <input type="email" id="emailInput" name="emailInput" placeholder="电子邮箱" class="form-control" v-model="email">
                            <div class="input-group-btn"><button class="btn btn-primary" @click="bindEmail">{{emailBtn}}</button></div>
                        </div>

                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        功能设置
                    </div>
                    <div class="card-body">
                        <div>
                            邮箱通知
                            <button class="btn btn-sm" onclick="alert('关闭此功能，你将不会接收到本网站的邮件通知')">?</button>

                            <button class="btn btn-sm" style="float: right" :class="{'btn-primary':setting.emailInform}" @click="toggleEmailInform">{{setting.emailInform?'已开启':'已关闭'}}</button>

                        </div>

                        <div style="margin-top: 15px;">
                            匿名社交
                            <button class="btn btn-sm" onclick="alert('开启此功能，在本站任何位置，你的个人信息展示都将打码处理')">?</button>

                            <button class="btn btn-sm" style="float: right" :class="{'btn-primary':setting.anonymous}" @click="toggleAnonymous">{{setting.anonymous?'已开启':'已关闭'}}</button>

                        </div>

                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header">
                        登录日志
                    </div>
                    <div class="card-body">
                        <ul class="list-group">
                            <li class="list-group-item" v-for="log in loginLogList">
                                {{log.loginIp}},{{log.createTime}}
                            </li>
                        </ul>
                    </div>
                </div>

            </div>
        </div>



    </div>

</div>
<!-- /.content -->

</div>
<div class="clearfix"></div>

<#include "script.ftl"/>
<script src="/js/account.js?v=20190418"></script>
</body>
</html>

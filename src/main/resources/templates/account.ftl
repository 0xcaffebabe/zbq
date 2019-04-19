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
    <!-- Animated -->



    <div class="animated fadeIn" id="addLesson">
        <div class="row" id="account">
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

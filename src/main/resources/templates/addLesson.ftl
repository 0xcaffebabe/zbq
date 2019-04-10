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

        <div class="card">
            <div class="card-header">
                <h3>添加章节</h3>
            </div>

            <div class="card-body">
                <input type="text" class="form-control" v-model="lessonName">
                <div id="editor">

                </div>

                <hr>
                <button class="btn btn-primary" style="float: right" @click="publishLesson">发布</button>
            </div>
        </div>
    </div>

</div>
<!-- /.content -->

</div>
<div class="clearfix"></div>

<#include "script.ftl"/>
<script src="/js/wangEditor.js"></script>
<script src="/js/addLesson.js?v=20190410"></script>
</body>
</html>

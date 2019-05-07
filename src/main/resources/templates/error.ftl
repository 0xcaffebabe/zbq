<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">

    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <#include "css.ftl"/>

    <title>${error}</title>

    <style>

    </style>
</head>
<body>

<div class="container">
    <div style="margin-top: 144px;">


        <h3 class="text-center">${error}</h3>

        <div class="text-center" style="margin-top: 15px">
            <a href="javascript:parent.window.location='/'" class="btn btn-lg btn-primary">返回主页</a>

            <a href="javascript:window.history.back()" class="btn btn-lg btn-success" style="margin-left: 15px">后退一步</a>
        </div>
    </div>



</div>


</body>




</html>


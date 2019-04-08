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

    <div class="animated fadeIn">
        <div class="card">
            <div class="card-header">
                <h4>内容创作</h4>
            </div>
            <div class="card-body" id="publish">
                <div class="custom-tab">

                    <nav>
                        <div class="nav nav-tabs" id="nav-tab" role="tablist">
                            <a class="nav-item nav-link active" id="custom-nav-home-tab" data-toggle="tab"
                               href="#custom-nav-home" role="tab" aria-controls="custom-nav-home" aria-selected="true">发布文章</a>
                            <a class="nav-item nav-link" id="custom-nav-profile-tab" data-toggle="tab" href="#custom-nav-profile" role="tab"
                               aria-controls="custom-nav-profile" aria-selected="false">发布视频</a>
                        </div>
                    </nav>
                    <div class="tab-content pl-3 pt-2" id="nav-tabContent">
                        <div class="tab-pane fade show active" id="custom-nav-home" role="tabpanel" aria-labelledby="custom-nav-home-tab">
                            <input type="text" class="form-control" placeholder="请输入文章标题" v-model="title">
                            <div id="editor">

                            </div>
                            <div id="tag-pane" style="margin-top: 15px;">
                                <button v-for="(tag,index) in tags" class="btn btn-sm btn-primary" style="margin-left: 10px" @click="removeTag(index)">
                                    {{tag}}&times
                                </button>
                                <input type="text" placeholder="请输入标签,按回车键完成输入" style="font-size:18px;" v-model="rowTag" @keyup.enter="processTag">
                            </div>

                            <button class="btn btn-primary" @click="publishContent">发布内容</button>
                        </div>
                        <div class="tab-pane fade" id="custom-nav-profile" role="tabpanel" aria-labelledby="custom-nav-profile-tab">
                            <p>Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt tofu stumptown aliqua, retro synth master cleanse. Mustache cliche tempor, williamsburg carles vegan helvetica. Reprehenderit butcher retro keffiyeh dreamcatcher synth. Cosby sweater eu banh mi, irure terry richardson ex sd. Alip placeat salvia cillum iphone. Seitan alip s cardigan american apparel, butcher voluptate nisi .</p>
                        </div>
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


<script src="/js/wangEditor.js"></script>
<script src="/js/publish.js"></script>
<script>

</script>
</body>
</html>

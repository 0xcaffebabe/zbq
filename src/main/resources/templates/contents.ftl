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
        .fold {
            height: 200px;
        }
    </style>
</head>

<body>

<!-- Content -->
<div class="content">
    <!-- Animated -->

    <div class="animated fadeIn" id="content">
        <button id="modalTrigger" data-target="#staticModal" data-toggle="modal" style="display: none"></button>
        <div class="modal fade" id="staticModal" tabindex="-1" role="dialog" aria-labelledby="staticModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <span style="cursor: pointer;float: right" @click="showComment">&times;</span>
                        <h5 class="modal-title" id="staticModalLabel">{{commentList.length}}条评论</h5>
                    </div>
                    <div class="modal-body">

                        <ul class="media-list">
                            <li class="media" v-for="comment in commentList">
                                <div class="media-left">
                                    <a href="#">
                                        <img class="media-object" src="/img/anonymous.jpg" alt="..." width="48">
                                    </a>
                                </div>
                                <div class="media-body" style="margin-left: 10px;">
                                    <span style="float: right"><i class="fa fa-clock-o"></i> {{comment.createTime}}</span>
                                    <h4 class="media-heading">root</h4>
                                    <p>{{comment.content}}</p>
                                </div>
                                <hr>
                            </li>


                        </ul>

                    </div>
                    <div class="modal-footer">
                        <input type="text" class="form-control" placeholder="写下你的评论"
                               @focus="commentPaneFocus" @blur="commentPaneBlur" v-model="commentContent">
                        <button class="btn btn-primary"  @click="submitComment">发布</button>
                    </div>
                </div>
            </div>
        </div>


        <div class="card" v-for="content in contentList">
            <div class="card-header" :id="'content' + content.contentId">

                <div class="media">
                    <div class="media-left">
                        <a href="#">
                            <img class="media-object" :src="content.user.profile" alt="..." width="64">
                        </a>
                    </div>
                    <div class="media-body">
                        <div style="float: right" class="btn btn-group">
                            <a href="" style="color:red;font-weight: bold">关注</a>

                        </div>
                        <div style="margin-top: 15px;margin-left: 15px;">
                            <h4 class="media-heading">{{content.user.nickName}} </h4>
                            <p><span class="fa fa-clock-o"></span>{{content.createTime}} · 不知名转笔教学作者</p>
                        </div>


                    </div>
                </div>


            </div>

            <div class="card-body">

                <div class="mx-auto d-block">
                    <a :href="'/article/' + content.contentId">
                        <h3>{{content.title}}</h3>
                    </a>

                    <hr>
                    <p style="overflow: hidden" class="targetFold" v-html="content.content">

                    </p>

                    </button>

                </div>
                <hr>
                <div class="card-text inline-block">
                    <button class="btn btn-default btn-sm"
                            :class="{'btn-primary':content.hasLike,'btn-default':!content.hasLike}"
                            @click="likeContent(content)">
                        <i class="fa fa-thumbs-up"></i>
                        {{content.likeCount}} 赞同
                    </button>
                    <a href="#" @click="showComment(content)" style="margin-left: 50px">
                        <i class="fa fa-comment"></i>
                        {{content.commentCount}} 评论
                    </a>

                    <a href="" style="margin-left: 50px">
                        <i class="fa fa-star"></i>
                        485 收藏
                    </a>

                    <a href="" style="margin-left: 50px">
                        <i class="fa fa-paper-plane"></i>
                        分享
                    </a>


                </div>

            </div>

        </div>


    </div>
    <!-- /.content -->

</div>
<div class="clearfix"></div>

<#include "script.ftl"/>

<script src="/js/content.js?v=20190410"></script>
<script>


</script>
</body>
</html>

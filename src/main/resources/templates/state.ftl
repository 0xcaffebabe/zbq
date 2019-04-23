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
        .list-enter-active, .list-leave-active {
            transition: all 1s;
        }

        .list-enter, .list-leave-to
            /* .list-leave-active for below version 2.1.8 */
        {
            opacity: 0;
            transform: translateY(30px);
        }
    </style>
</head>

<body>

<!-- Content -->
<div class="content">
    <div class="animated fadeIn" id="state">


        <div class="modal fade" id="videoModal" tabindex="-1" role="dialog" aria-labelledby="staticModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-sm" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticModalLabel">视频添加</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">


                        <input type="text" class="form-control" placeholder="请输入视频播放地址" v-model="rowVideoUrl">
                        <p>支持哔哩哔哩、优酷</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" data-dismiss="modal" @click="analyzeVideoUrl">解析
                        </button>
                    </div>
                </div>
            </div>
        </div>


        <div class="card">
            <div class="card-header">
                <h4>发布动态</h4>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-8">
                        <textarea class="form-control" v-model="stateContent"></textarea>
                    </div>
                    <div class="col-md-4">
                        <div class="btn btn-group" style="">
                            <button class="btn btn-sm btn-primary" @click="publishState">发布</button>
                            <button class="btn btn-sm btn-success">添加图片</button>
                            <button class="btn btn-sm btn-info" data-toggle="modal" data-target="#videoModal">添加视频
                            </button>
                        </div>
                    </div>
                </div>
                <div>
                    <button v-if="videoUrl != ''" @click="clearVideo">一条视频 X</button>
                </div>
            </div>
        </div>


        <div>
            <div class="card">
                <div class="card-header">
                    <h4>动态板</h4>
                </div>
                <div class="card-body">
                    <div class="default-tab">
                        <nav>
                            <div class="nav nav-tabs" id="nav-tab" role="tablist">
                                <a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab"
                                   href="#nav-home" role="tab" aria-controls="nav-home"
                                   aria-selected="true">朋友</a>
                                <a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab"
                                   href="#nav-profile" role="tab" aria-controls="nav-profile"
                                   aria-selected="false">世界</a>

                            </div>
                        </nav>
                        <div class="tab-content pl-3 pt-2" id="nav-tabContent">
                            <div class="tab-pane fade show active" id="nav-home" role="tabpanel"
                                 aria-labelledby="nav-home-tab">
                                <ul class="media-list">
                                    <transition-group name="list">
                                        <li class="media" v-for="(i,index) in selfStateList" style="margin-top: 15px"
                                            :key="index">
                                            <div class="media-left">
                                                <a href="#" @click.prevent="visitFriend(i.userVO.userId)">
                                                    <img class="" :src="i.userVO.profile" alt="..." width="48"
                                                         style="border-radius: 5px;">
                                                </a>
                                            </div>
                                            <div class="media-body" style="margin-left: 15px">

                                                <a href="" style="color: midnightblue;font-weight: bold"><h4
                                                        class="media-heading">{{i.userVO.nickName}}</h4></a>
                                            <#--动态内容开始-->
                                                <div>
                                                    <div v-if="i.content.content == undefined">{{i.content}}</div>
                                                    <div v-if="i.content.content != undefined">{{i.content.content}}
                                                    </div>
                                                    <iframe :src="i.content.video" v-if="i.content.video != undefined"
                                                            style="width: 100%;height: 500px;border: none"></iframe>
                                                </div>
                                            <#--动态内容结束-->

                                                <p style="margin-top: 10px">{{i.createTime}}</p>

                                                <div>

                                                    <div style="">

                                                        <a style="float:right;cursor: pointer;font-size: 24px"><span
                                                                @click.prevent="likeClick(i)" :data-id="i.stateId"
                                                                class="fa fa-heart-o "
                                                                :class="{red:i.likes.hasLike}"></span></a>
                                                    </div>

                                                    <div>
                                                        <span class="fa fa-heart-o"></span> {{i.likes.likeCount}}
                                                        <a href="#" @click.prevent="visitFriend(like.likeUser.userId)" v-for="like in i.likes.likeList"
                                                           style="margin-left: 5px"
                                                           :title="like.likeUser.userInfo.nickName">
                                                            <img :src="like.likeUser.userInfo.profile" alt=""
                                                                 width="24" style="border-radius: 50px;">
                                                        </a>
                                                    </div>


                                                    <hr>

                                                    <div>

                                                    <#--评论开始-->
                                                        <ul class="media-list" style="padding:20px">

                                                            <li class="media" v-for="comment in i.comments">
                                                                <div class="media-left media-middle">
                                                                    <a href="#"
                                                                       @click="visitFriend(comment.fromUser.userId)">
                                                                        <img class="media-object"
                                                                             :src="comment.fromUser.profile" alt="..."
                                                                             width="32">
                                                                    </a>
                                                                </div>
                                                                <div class="media-body">
                                                                    <h6 class="media-heading">
                                                                        {{comment.fromUser.nickName}}
                                                                        <span class="fa fa-clock-o"></span>
                                                                        {{comment.createTime}}</h6>
                                                                    <p>
                                                                        <strong v-if="comment.toUser != null">
                                                                            <a href="#">
                                                                                @{{comment.toUser.nickName}}
                                                                            </a>

                                                                        </strong>
                                                                        {{comment.content}}
                                                                        <a href="#"
                                                                           @click.prevent="replyComment(comment,i)"><b
                                                                                href="#"> 回复</b></a>
                                                                    </p>


                                                                </div>

                                                            </li>

                                                            <li class="media">
                                                                <div class="media-left media-middle">
                                                                    <a href="#">
                                                                        <img class="media-object" :src="myProfile"
                                                                             alt="..." width="32">
                                                                    </a>
                                                                </div>
                                                                <div class="media-body">
                                                                    <div class="row">
                                                                        <div class="col-md-10 col-sm-8 col-xs-8">
                                                                        <#--<a href="#">{{atModel[i.stateId]}}</a>-->
                                                                            <input type="text" class="form-control"
                                                                                   style="border-left: none!important;margin-left: 10px"
                                                                                   v-model="commentModel[i.stateId]"
                                                                                   :placeholder="atModel[i.stateId]">
                                                                        </div>
                                                                        <div class="col-md-2 col-sm-4 col-xs-4">
                                                                            <button class="btn btn-sm btn-primary form-control"
                                                                                    @click="publishComment(i)">
                                                                                评论
                                                                            </button>
                                                                        </div>


                                                                    </div>
                                                                </div>
                                                            </li>

                                                        </ul>
                                                    <#--评论结束-->

                                                    </div>


                                                </div>

                                            </div>

                                            <hr>
                                        </li>
                                    </transition-group>
                                </ul>


                            </div>

                            <div class="tab-pane fade" id="nav-profile" role="tabpanel"
                                 aria-labelledby="nav-profile-tab">
                                <p>Raw denim you probably haven't heard of them jean shorts Austin. Nesciunt
                                    tofu stumptown aliqua, retro synth master cleanse. Mustache cliche tempor,
                                    williamsburg carles vegan helvetica. Reprehenderit butcher retro keffiyeh
                                    dreamcatcher synth. Cosby sweater eu banh mi, irure terry richardson ex sd.
                                    Alip placeat salvia cillum iphone. Seitan alip s cardigan american apparel,
                                    butcher voluptate nisi .</p>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>


    </div><!-- .animated -->


</div><!-- .content -->





<#include "script.ftl"/>
<script src="/js/state.js?v=20190423"></script>


</body>

</html>

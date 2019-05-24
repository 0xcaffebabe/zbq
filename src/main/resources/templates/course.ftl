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

        .green {
            color: green;
        }

        .hasLearn {
            color: #878787
        }

        .unLearn {
            color: #000;
        }

        .bb {
            background-size: cover
        }
        .rating-star{
            color:#ff9900;
            font-size: 24px;
        }
        .rating:hover{
            cursor: pointer;
        }
        .rating{
            font-size: 24px;
        }
    </style>
</head>

<body>

<div id="course">
    <div class="jumbotron"
         style="background:#4183c4"

    >
        <h1 style="color:white">{{course.courseName}}</h1>
        <div class="row">
            <div class="col-md-4">
                <div class="media" style="margin-top: 10px">
                    <div class="media-left">
                        <a href="#">
                            <img class="media-object" :src="course.author.profile" alt="..." width="48">
                        </a>
                    </div>
                    <div class="media-body" style="margin-left: 5px">
                        <h4 class="media-heading" style="color:white">{{course.author.nickName}}</h4>
                        <p style="color:white">不知名转笔教学作者</p>

                    </div>
                </div>
            </div>

            <div class="col-md-3">
                <ul style="color: white">
                    <li>
                        <strong>难度</strong>&nbsp;&nbsp;{{course.courseLevel}}
                    </li>
                    <li>
                        <strong>学习人数</strong>&nbsp;&nbsp;{{course.learningCount}}
                    </li>
                    <li>
                        <strong>评分</strong>&nbsp;&nbsp;10.0
                    </li>

                </ul>

            </div>
        </div>

        <hr style="color:white">
        <strong style="color: white">学习进度:{{progress}}%</strong>
        <p><a class="btn btn-info btn-lg" href="#" role="button">开始学习</a></p>
    </div>

    <!-- Content -->
    <div class="content">

        <!-- Animated -->

        <div class="animated fadeIn">
            <div class="card">
                <div class="card-header"><h3>简介</h3></div>
                <div class="card-body">{{course.courseDesc}}</div>
            </div>


            <div class="card">
                <div class="card-header">
                    <h4>Default Tab</h4>
                </div>
                <div class="card-body">
                    <div class="default-tab">
                        <nav>
                            <div class="nav nav-tabs" id="nav-tab" role="tablist">
                                <a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#nav-home"
                                   role="tab" aria-controls="nav-home" aria-selected="true"><span class="fa fa-list"></span>&nbsp;章节</a>
                                <a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile"
                                   role="tab" aria-controls="nav-profile" aria-selected="false"><span class="fa fa-users"></span>&nbsp;同学</a>
                                <a class="nav-item nav-link" id="nav-contact-tab" data-toggle="tab" href="#nav-contact"
                                   role="tab" aria-controls="nav-contact" aria-selected="false"><span class="fa fa-comments"></span>&nbsp;评论</a>
                                <a class="nav-item nav-link" id="nav-rating-tab" data-toggle="tab" href="#nav-rating"
                                   role="tab" aria-controls="nav-rating" aria-selected="false"><span class="fa fa-star"></span>&nbsp;评价</a>
                            </div>
                        </nav>
                        <div class="tab-content pl-3 pt-2" id="nav-tabContent">
                            <div class="tab-pane fade show active" id="nav-home" role="tabpanel"
                                 aria-labelledby="nav-home-tab">


                                <ul class="list-group">
                                    <li class="list-group-item" v-for="lesson in course.lessonList">
                                        <i class="fa fa-play-circle"></i>
                                        <i class="fa fa-check-circle" :class="{green:lesson.hasLearn}"
                                           style="float: right"></i>
                                        <a :href="'/lessons/' + lesson.lessonId"
                                           :class="{hasLearn:lesson.hasLearn,unLearn:!lesson.hasLearn}">
                                            {{lesson.lessonName}}
                                        </a>
                                    </li>

                                </ul>

                            </div>
                            <div class="tab-pane fade" id="nav-profile" role="tabpanel"
                                 aria-labelledby="nav-profile-tab">
                                <div class="row">
                                    <div class="col-md-3 col-xs-3 col-sm-3 col-lg3" v-for="classmate in classmateList"
                                         style="margin-top: 12px">
                                        <a href="#" :href="'/space/'+classmate.userId" :title="classmate.nickName">
                                            <img :src="classmate.profile" style="border-radius: 50px"
                                                 :alt="classmate.nickName" width="48">
                                            <h5 class="text-center">{{classmate.nickName}}</h5>
                                        </a>

                                    </div>


                                </div>
                            </div>
                            <div class="tab-pane fade" id="nav-contact" role="tabpanel"
                                 aria-labelledby="nav-contact-tab">
                                <ul class="media-list">


                                    <li class="media" style="margin-top: 15px;" v-for="comment in commentList">
                                        <div class="media-left">
                                            <a href="#">
                                                <img class="media-object" :src="comment.fromUser.profile" alt="..."
                                                     width="48">
                                            </a>
                                        </div>
                                        <div class="media-body" style="margin-left: 10px;">
                                            <h4 class="media-heading">{{comment.fromUser.nickName}}<span
                                                    style="color: #878787;font-size: 16px">{{comment.createTime}}</span>
                                            </h4>
                                            <p style="margin-top: 8px">{{comment.content}}<a href="#"
                                                                                             style="font-weight: bold">回复</a>
                                            </p>
                                            <p>

                                            </p>
                                            <p class="text-right">发表于 <a :href="'/lessons/' + comment.lesson.lessonId">{{comment.lesson.lessonName}}</a>
                                            </p>
                                        </div>


                                    </li>
                                </ul>
                            </div>
                            <div class="tab-pane fade" id="nav-rating" role="tabpanel"
                                 aria-labelledby="nav-rating-tab">

                                <div style="margin-top: 5px">
                                    评分:
                                    <span class="fa fa-star-o rating" @click="setRating(1)" :class="{'rating-star':rating >= 1}"></span>
                                    <span class="fa fa-star-o rating" @click="setRating(2)" :class="{'rating-star':rating >= 2}"></span>
                                    <span class="fa fa-star-o rating" @click="setRating(3)" :class="{'rating-star':rating >= 3}"></span>
                                    <span class="fa fa-star-o rating" @click="setRating(4)" :class="{'rating-star':rating >= 4}"></span>
                                    <span class="fa fa-star-o rating" @click="setRating(5)" :class="{'rating-star':rating >= 5}"></span>
                                </div>

                                <div class="row">
                                    <div class="col-md-8">
                                        <div>
                                            <textarea class="form-control" name="" id="" cols="30" rows="3" v-model="ratingComment"></textarea>
                                            <button class="btn btn-lg btn-primary" style="border-radius: 25px;float: right;margin-top: 5px" @click="publishRating">提交</button>
                                        </div>

                                        <ul class="media-list" style="margin-top: 72px">
                                            <li class="media" v-for="rating in ratingList">
                                                <div class="media-left" >
                                                    <a href="#">
                                                        <img class="media-object" :src="rating.user.profile" alt="..." width="48">
                                                    </a>
                                                </div>
                                                <div class="media-body" style="margin-left: 10px">
                                                    <div style="float: right">
                                                        <span class="fa fa-star-o rating" :class="{'rating-star':rating.rating >= 1}"></span>
                                                        <span class="fa fa-star-o rating" :class="{'rating-star':rating.rating >= 2}"></span>
                                                        <span class="fa fa-star-o rating" :class="{'rating-star':rating.rating >= 3}"></span>
                                                        <span class="fa fa-star-o rating" :class="{'rating-star':rating.rating >= 4}"></span>
                                                        <span class="fa fa-star-o rating" :class="{'rating-star':rating.rating >= 5}"></span>

                                                    </div>
                                                    <h4 class="media-heading"><span>{{rating.user.nickName}}</span>
                                                    {{rating.createTime}}
                                                    </h4>

                                                    <p>
                                                        {{rating.content}}
                                                    </p>

                                                </div>
                                            </li>

                                        </ul>

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
    </div>


<#include "script.ftl"/>
    <script src="/js/course.js?v=20190524"></script>
</body>
</html>

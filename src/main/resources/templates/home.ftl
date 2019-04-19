<!doctype html>

<!--[if gt IE 8]><!-->
<html class="no-js" lang=""> <!--<![endif]-->
<script>
    navigator.geolocation.getCurrentPosition(function (position) {
        console.log(position.coords.latitude);
    });
</script>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>测试主页</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">


    <#include "css.ftl"/>

</head>

<body>

<div class="content">
    <!-- Animated -->
    <div class="animated fadeIn">
        <!-- Widgets  -->
        <div class="row" id="dashboard">
            <div class="col-lg-3 col-md-6 col-sm-6 col-xs-6">
                <div class="card">
                    <div class="card-body">
                        <div class="stat-widget-five">
                            <div class="stat-icon dib flat-color-4">
                                <i class="pe-7s-users"></i>
                            </div>
                            <div class="stat-content">
                                <div class="text-left dib">
                                    <div class="stat-text"><span class="count">{{friendQuantity}}</span></div>
                                    <div class="stat-heading">好友数</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-3 col-md-6 col-sm-6 col-xs-6">
                <div class="card">
                    <div class="card-body">
                        <div class="stat-widget-five">
                            <div class="stat-icon dib flat-color-1">
                                <i class="pe-7s-star"></i>
                            </div>
                            <div class="stat-content">
                                <div class="text-left dib">
                                    <div class="stat-text"><span class="count">{{stateQuantity}}</span></div>
                                    <div class="stat-heading">动态数</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-3 col-md-6 col-sm-6 col-xs-6">
                <div class="card">
                    <div class="card-body">
                        <div class="stat-widget-five">
                            <div class="stat-icon dib flat-color-2">
                                <i class="pe-7s-sun"></i>
                            </div>
                            <div class="stat-content">
                                <div class="text-left dib">
                                    <div class="stat-text"><span class="count">0</span></div>
                                    <div class="stat-heading">在线天数</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-3 col-md-6 col-sm-6 col-xs-6">
                <div class="card">
                    <div class="card-body">
                        <div class="stat-widget-five">
                            <div class="stat-icon dib flat-color-3">
                                <i class="pe-7s-like"></i>
                            </div>
                            <div class="stat-content">
                                <div class="text-left dib">
                                    <div class="stat-text"><span class="count">{{likeQuantity}}</span></div>
                                    <div class="stat-heading">收获赞数 <i class="fa fa-question-circle"
                                                                      style="cursor: pointer;"
                                                                      @click="showLikeDetail"></i></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /Widgets -->
        <div class="row">

            <div class="col-md-4">
                <div class="card" id="quickStart">
                    <div class="card-header">
                        <strong>快速开始</strong>
                    </div>
                    <div class="card-body">
                        <p class="text-sm-center">
                            {{welcome}}
                        </p>


                        <button class="btn btn-success btn-block" onclick="location.hash='userCard'"><span class="fa fa-user"></span>&nbsp;完善个人信息</button>
                        <button class="btn btn-primary btn-block" onclick="location='/courses'"><span class="fa fa-list"></span>&nbsp;学习转笔课程</button>
                        <button class="btn btn-info btn-block" onclick="location='/maps'"><span class="fa fa-globe"></span>&nbsp;看看转笔地图</button>
                        <button class="btn btn-warning btn-block" onclick="location='/contents'"><span class="fa fa-pencil"></span>&nbsp;刷刷转笔内容</button>
                        <button class="btn btn-danger btn-block" onclick="location='/states'"><span class="fa fa-star"></span>&nbsp;发布一条动态</button>



                    </div> <!-- /.card-body -->
                </div><!-- /.card -->
            </div>

            <div class="col-md-8">
                <div class="card" id="userCard">
                    <div class="card-header">
                        <button class="btn btn-success btn-sm" style="float:right" @click="saveUserInfo">保存资料</button>
                        <strong class="card-title mb-3">资料卡</strong>


                    </div>
                    <div class="card-body">
                        <div class="mx-auto d-block">
                            <div class="row">
                                <div class="col-md-4">
                                    <img class="rounded-circle mx-auto d-block" height="72" width="72"
                                         :src="profile" alt="Card image cap">
                                    <h5 class="text-sm-center mt-2 mb-1">
                                        <input type="text" class="hidden-input text-center" style="width:90px;" name=""
                                               v-model="nickName">
                                    </h5>

                                    <h5 class="text-sm-center mt-2 mb-1">
                                        <div class="hiddenfile">
                                            <input type="file" id="file" @change="uploadProfile"/>
                                        </div>
                                        <button class="btn btn-primary btn-sm" @click="changeProfile">更换头像</button>
                                    </h5>
                                </div>
                                <div class="col-md-8" style="">
                                    <div class="location text-sm-left">
                                        <i class="fa fa-map-marker"></i>&nbsp;&nbsp;
                                        <input type="text" class="hidden-input" style="width:72px;" name=""
                                               v-model="region">
                                    </div>

                                    <div class="blank"></div>
                                    <div class="text-sm-left">
                                        <i class="fa fa-birthday-cake"></i>&nbsp;
                                        <input type="date" class="hidden-input" style="width:150px;" name=""
                                               v-model="birthday">
                                    </div>

                                    <div class="blank"></div>

                                    <div class="text-sm-left">
                                        <i class="fa fa-heart"></i>&nbsp;&nbsp;
                                        <input type="text" class="hidden-input" style="width:18px;" name=""
                                               v-model="penYear">年
                                    </div>

                                    <div class="blank"></div>
                                    <div class="text-sm-left">
                                        <i class="fa fa-transgender-alt"></i>&nbsp;
                                        <select style="width:72px;" class="hidden-input" v-model="gender">
                                            <option value="1">男</option>
                                            <option value=-1>女</option>
                                            <option value="0">未知</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="text-sm-center">
                                <input type="text" class="hidden-input text-center" name="" v-model="description">
                            </div>

                        </div>
                        <hr>
                        <div class="card-text text-sm-center">

                            <a href="#"><i class="fa fa-qq pr-1"></i></a>
                            <a href="#"><i class="fa fa-weibo pr-1"></i></a>
                            <a href="#"><i class="fa fa-weixin pr-1"></i></a>
                            <button class="btn btn-sm btn-info">邮箱绑定</button>
                        </div>
                    </div>
                </div>
            </div>


        </div>
        <div class="clearfix"></div>


    </div>
    <!-- .animated -->
</div>



<#include "script.ftl"/>
<script src="js/home.js?v=20190417"></script>


</body>
</html>

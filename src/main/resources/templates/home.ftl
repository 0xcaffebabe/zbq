<!doctype html>

<!--[if gt IE 8]><!-->
<html class="no-js" lang=""> <!--<![endif]-->
<script>
    navigator.geolocation.getCurrentPosition(function(position) {
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
                <div class="col-lg-3 col-md-6">
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

                <div class="col-lg-3 col-md-6">
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

                <div class="col-lg-3 col-md-6">
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

                <div class="col-lg-3 col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <div class="stat-widget-five">
                                <div class="stat-icon dib flat-color-3">
                                    <i class="pe-7s-like"></i>
                                </div>
                                <div class="stat-content">
                                    <div class="text-left dib">
                                        <div class="stat-text"><span class="count">{{likeQuantity}}</span></div>
                                        <div class="stat-heading">收获赞数 <i class="fa fa-question-circle" style="cursor: pointer;" @click="showLikeDetail"></i></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /Widgets -->
            <div class="row">
                <div class="col-md-7">
                    <div class="card" id="userCard">
                        <div class="card-header">
                            <button class="btn btn-success btn-sm" style="float:right" @click="saveUserInfo">保存资料</button>
                            <strong class="card-title mb-3">资料卡</strong>


                        </div>
                        <div class="card-body" >
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
                                            <input type="text" class="hidden-input" style="width:18px;" name="" v-model="penYear">年
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

                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-5">
                    <div class="card">
                        <div class="card-body">
                            <h4 class="card-title box-title">即时反馈</h4>
                            <div class="card-content">
                                <div class="messenger-box">
                                    <div style="height:400px;overflow-y: scroll" ;>
                                        <ul>
                                            <li>
                                                <div class="msg-received msg-container">
                                                    <div class="avatar">
                                                        <img src="img/anonymous.jpg" alt="">
                                                        <div>
                                                            <div class="send-time">11.11 am</div>
                                                        </div>

                                                    </div>
                                                    <div class="msg-box">
                                                        <div class="inner-box">
                                                            <div class="name">
                                                                小助手
                                                            </div>
                                                            <div class="meg">
                                                                你好，欢迎来到转笔圈，有什么不懂的请问我
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div><!-- /.msg-received -->
                                            </li>

                                            <li>
                                                <div class="msg-received msg-container">
                                                    <div class="avatar">
                                                        <img src="img/anonymous.jpg" alt="">
                                                        <div>
                                                            <div class="send-time">11.11 am</div>
                                                        </div>

                                                    </div>
                                                    <div class="msg-box">
                                                        <div class="inner-box" style="background-color: #03a9f3;color:white">
                                                            <div class="name">
                                                                我
                                                            </div>
                                                            <div class="meg">
                                                                你好
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div><!-- /.msg-received -->
                                            </li>
                                            <li>
                                                <div class="msg-received msg-container">
                                                    <div class="avatar">
                                                        <img src="img/anonymous.jpg" alt="">
                                                        <div>
                                                            <div class="send-time">11.11 am</div>
                                                        </div>

                                                    </div>
                                                    <div class="msg-box">
                                                        <div class="inner-box">
                                                            <div class="name">
                                                                小助手
                                                            </div>
                                                            <div class="meg">
                                                                你好，有什么可以帮您
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div><!-- /.msg-received -->
                                            </li>

                                            <li>
                                                <div class="msg-received msg-container">
                                                    <div class="avatar">
                                                        <img src="img/anonymous.jpg" alt="">
                                                        <div>
                                                            <div class="send-time">11.15 am</div>
                                                        </div>

                                                    </div>
                                                    <div class="msg-box">
                                                        <div class="inner-box" style="background-color: #03a9f3;color:white">
                                                            <div class="name">
                                                                我
                                                            </div>
                                                            <div class="meg">
                                                                你，你是曾经的转笔机器人吗
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div><!-- /.msg-received -->
                                            </li>

                                            <li>
                                                <div class="msg-received msg-container">
                                                    <div class="avatar">
                                                        <img src="img/anonymous.jpg" alt="">
                                                        <div>
                                                            <div class="send-time">11.11 am</div>
                                                        </div>

                                                    </div>
                                                    <div class="msg-box">
                                                        <div class="inner-box">
                                                            <div class="name">
                                                                小助手
                                                            </div>
                                                            <div class="meg">
                                                               是的，我将在新的岗位发光发热
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div><!-- /.msg-received -->
                                            </li>

                                        </ul>
                                    </div>

                                    <div class="send-mgs">
                                        <div class="yourmsg">
                                            <input class="form-control" type="text">
                                        </div>
                                        <button class="btn msg-send-btn">
                                            <i class="pe-7s-paper-plane"></i>
                                        </button>
                                    </div>
                                </div><!-- /.messenger-box -->
                            </div>
                        </div> <!-- /.card-body -->
                    </div><!-- /.card -->
                </div>

            </div>
            <div class="clearfix"></div>


        </div>
        <!-- .animated -->
    </div>



<#include "script.ftl"/>
<script src="js/home.js?v=20190410"></script>


</body>
</html>

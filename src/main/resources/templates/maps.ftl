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

    <div class="animated fadeIn" id="map">

        <div class="modal fade" id="staticModal" tabindex="-1" role="dialog" aria-labelledby="staticModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-sm" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticModalLabel">Pser信息</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <img :src="user.profile"
                             alt="" width="50" class="img-circle">
                        <span>{{user.nickName}}</span>
                        <a v-show="!user.anonymous" :href="'/space/' + user.userId">TA的主页</a>
                        <div>
                            <span>{{user.address}}</span>
                            <div></div>
                            <span>经纬度:{{user.lnglat}}</span>
                            <div></div>
                            <span>距离你 {{user.distance}}</span>
                        </div>


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>

                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="locationModal" tabindex="-1" role="dialog" aria-labelledby="staticModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticModalLabel">我的位置信息</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <span>地址:{{position.address}}</span>
                        <div></div>
                        <span>经纬度:{{position.position.lng}},{{position.position.lat}}</span>
                        <div></div>
                        <span>是否匿名:</span>
                        <input type="checkbox" class="checkbox" v-model="anonymous">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-primary" data-dismiss="modal"
                                :class="{disabled:location != null}" @click="shareLocation">共享我的位置
                        </button>
                        <button type="button" class="btn btn-success" data-dismiss="modal"
                                :class="{disabled:location == null}" @click="updateLocation">更新我的位置
                        </button>

                    </div>
                </div>
            </div>
        </div>

        <button style="display: none" data-toggle="modal" data-target="#locationModal" id="simulationLocation">111
        </button>
        <button style="display: none" data-toggle="modal" data-target="#staticModal" id="simulation">111</button>


        <span>{{address}}</span>
        <div class="btn btn-group">


            <button class="btn btn-sm btn-primary" @click="showShareLocation">共享我的位置</button>
        </div>
        <div>
            <p class="text-sm-center">已有{{locationCount}}位Pser共享了位置，红水滴为当前位置，蓝水滴为其他pser位置;拖动地图可更改你的定位</p>
        </div>
        <iframe name="container" src="/location.html" width="100%" height="500"></iframe>

        <div class="clearfix"></div>
        <!-- .animated -->
    </div>
    <!-- /.content -->

</div>
<div class="clearfix"></div>

<#include "script.ftl"/>
<script src="/js/map.js"></script>

<script>


</script>
</body>
</html>

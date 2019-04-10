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


    <div class="animated fadeIn" id="publish">
        <button id="modalTrigger" style="display: none" data-toggle="modal" data-target="#staticModal"></button>

        <div class="modal fade" id="staticModal" tabindex="-1" role="dialog" aria-labelledby="staticModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticModalLabel">添加课程</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <div class="form-group">
                            <label for="courseName">课程名称</label>
                            <input type="text" class="form-control" id="courseName" v-model="course.courseName">
                        </div>

                        <div>
                            <div >课程封面:</div>

                            <div class="hiddenfile">
                                <input type="file" id="file" @change="uploadThumbnail"/>
                            </div>
                            <img :src="course.courseImg" alt="" style="width: 400px;height:300px">
                            <button class="btn btn-primary btn-sm" @click="showFile">上传封面</button>
                        </div>

                        <div class="form-group">
                            <label for="courseLevel">课程等级</label>
                            <input type="text" class="form-control" id="courseLevel" v-model="course.courseLevel">
                        </div>

                        <div class="form-group">
                            <label for="courseDesc">课程描述</label>
                            <textarea type="text" class="form-control" id="courseDesc" v-model="course.courseDesc"></textarea>
                        </div>

                        <hr>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal" @click="publishCourse">保存</button>

                    </div>
                </div>
            </div>
        </div>


        <div class="card">
            <div class="card-header">
                <h4>课程制作</h4>
                <button class="btn btn-primary btn-sm" style="float: right" @click="showModal">添加课程</button>
            </div>
            <div class="card-body" >


                <table class="table table-striped table-hover">
                    <thead>
                    <td>课程名称</td>
                    <td>创建时间</td>
                    <td>操作</td>
                    </thead>
                    <tbody>
                    <tr v-for="course in courseList">
                        <td>
                            {{course.courseName}}
                        </td>
                        <td>
                            {{course.createTime}}
                        </td>

                        <td>
                            <button class="btn btn-sm btn-success" @click="editCourse(course)">编辑</button>
                            <button class="btn btn-sm btn-danger">删除</button>
                        </td>

                    </tr>


                    </tbody>
                </table>
            </div>
        </div>
    </div>

</div>
<!-- /.content -->

</div>
<div class="clearfix"></div>

<#include "script.ftl"/>

<script src="/js/wangEditor.min.js"></script>

<script src="/js/publish2.js?v=20190410"></script>
</body>
</html>

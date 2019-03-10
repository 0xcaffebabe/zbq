<aside id="left-panel" class="left-panel">
    <nav class="navbar navbar-expand-sm navbar-default" id="side">
        <div id="main-menu" class="main-menu collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="space/${user.userId?c}"><i class="menu-icon fa fa-home"></i>我的主页 </a></li>
                <li :class="{active:url=='/home'}">
                    <a href="/home"><i class="menu-icon fa fa-user"></i>用户中心 </a>

                </li>

                <li class="menu-title">社交</li><!-- /.menu-title -->


                <li :class="{active:url == '/friends'}"><a href="/friends"> <i class="menu-icon fa fa-users"></i>我的笔友 </a></li>
                <li :class="{active:url == '/state'}"><a href="/state"> <i class="menu-icon fa fa-cogs"></i> 笔圈动态</a></li>
                <li><a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false"> <i class="menu-icon fa fa-street-view"></i>转笔地图(不可用) </a></li>


                <li class="menu-title">内容</li>
                <li>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false"> <i class="menu-icon fa fa-search"></i>发现内容（不可用）</a>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false"> <i class="menu-icon fa fa-plus"></i>发布内容（不可用）</a>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false"> <i class="menu-icon fa fa-heart"></i>我的收藏（不可用）</a>
                </li>


                <li class="menu-title">学习</li><!-- /.menu-title -->

                <li>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false"> <i class="menu-icon fa fa-book"></i>课程中心（不可用） </a>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false"> <i class="menu-icon fa fa-bookmark"></i>我的课程（不可用） </a>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false"> <i class="menu-icon fa fa-square"></i>发布课程（不可用） </a>

                </li>

            </ul>
        </div><!-- /.navbar-collapse -->
    </nav>
</aside>
var map = new Vue({
    el: "#map",
    data: {
        address: '',
        user:{nickName:'',profile:'',distance:'',lnglat:'',userId:0,anonymous:false,address:''},
        locationCount:0,
        position:{
            position:{}
        }
        ,
        anonymous:false,
        location:null
    }
    ,
    created:function () {
        this.getSelfLocation();
    }
    ,
    methods: {
        positionClick:function (dataset,position) {
            $("#simulation").trigger("click");

            console.log(position);
            var t = dataset[position.index];
            var obj = {};
            if (!t.anonymous){
                obj = {
                    nickName:dataset[position.index].userVO.nickName,
                    profile:dataset[position.index].userVO.profile,
                    userId:dataset[position.index].userVO.userId
                };
            }else{
                obj = {
                    nickName:"匿名PSER",
                    profile:'/img/anonymous.jpg'
                };
            }


            if (container.window.position != null){

                var p1 = null;
                if (this.location != null){
                    p1 = [this.location.longitude,this.location.latitude];
                }else{
                    p1 = [container.window.position.position.lng,container.window.position.position.lat];
                }

                var p2 = position.data.lnglat;
                var dis = container.window.AMap.GeometryUtil.distance(p1, p2);
                obj.distance=(Math.floor(dis)/1000)+"千米";
            }else{
                if (this.location != null){
                    var p1 = [this.location.longitude,this.location.latitude];
                    var p2 = position.data.lnglat;
                    var dis = container.window.AMap.GeometryUtil.distance(p1, p2);
                    obj.distance=(Math.floor(dis)/1000)+"千米";
                }else{
                    obj.distance="未知千米";
                }

            }

            obj.lnglat=position.data.lnglat;
            obj.anonymous=t.anonymous;
            obj.address=t.address;
            this.user = obj;
        }

        ,
        showShareLocation:function () {
            var p = container.window.position;
            if (p == null){
                alert("获取位置信息失败，请拖动地图使小红标落在中国大陆上");
            }else{
                $("#simulationLocation").trigger("click");
                this.position = p;
            }

        }
        ,
        shareLocation:function () {

            var longitude = this.position.position.lng;
            var latitude = this.position.position.lat;
            var address = this.position.address;

            common.ajax.put(common.data.shareLocationUrl,function (response) {

                if (response.success){
                    alert(response.data);
                    window.location.reload();
                }else{
                    alert("共享位置信息失败:"+response.msg);
                }
            },{longitude:longitude,latitude:latitude,address:address,anonymous:this.anonymous})
        }
        ,
        updateLocation:function () {
            var longitude = this.position.position.lng;
            var latitude = this.position.position.lat;
            var address = this.position.address;

            common.ajax.post(common.data.shareLocationUrl,function (response) {

                if (response.success){
                    alert(response.data);
                    window.location.reload();
                }else{
                    alert("更新位置信息失败:"+response.msg);
                }
            },{longitude:longitude,latitude:latitude,address:address,anonymous:this.anonymous})
        }
        ,
        getSelfLocation:function () {
            var that = this;
            common.ajax.get(common.data.getSelfLocationUrl,function (response) {
                if (response.success){
                    if (response.data){
                        that.location = response.data;
                    }
                }else{
                    alert(response.data);
                }
            })
        }
    }
});
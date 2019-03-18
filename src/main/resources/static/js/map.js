var map = new Vue({
    el: "#map",
    data: {
        address: '',
        user:{nickName:'',profile:'',distance:'',lnglat:''},
        locationCount:0
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
                    profile:dataset[position.index].userVO.profile
                };
            }else{
                obj = {
                    nickName:"匿名PSER",
                    profile:'/img/anonymous.jpg'
                };
            }
            var p1 = [container.window.position.position.lng,container.window.position.position.lat];
            var p2 = position.data.lnglat;
            var dis = container.window.AMap.GeometryUtil.distance(p1, p2);
            obj.distance=(Math.floor(dis)/1000)+"千米";
            obj.lnglat=position.data.lnglat;
            this.user = obj;
        }
        ,
        shareLocation:function () {
            alert(container.window.position.position);
        }
    }
});
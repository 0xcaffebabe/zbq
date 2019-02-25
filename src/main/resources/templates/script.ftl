<script src="js/vue.js"></script>
<script src="js/jquery.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/common.js"></script>

<script>

    var header = new Vue({
        el:"#header",
        created:function(){
          this.getInfo();
        },
        data:{
            profile:''
        },
        methods:{
            getInfo:function () {
                var that = this;
                common.ajax.get(common.data.getCurrentUserDataUrl,function (response) {
                    if (response.success){
                        that.profile = response.data.profile;

                    }else{
                        alert(response.msg);
                    }
                })

            }
        }
    })
</script>
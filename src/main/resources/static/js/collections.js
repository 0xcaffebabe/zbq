
var collections = new Vue({
    el:"#collections",
    data:{
        collectionList:[],kw:''
    }
    ,
    created:function () {

        moment.locale("zh-cn");
        this.getCollectionList();
    }
    ,
    methods:{
        getCollectionList:function () {

            var that = this;


            common.ajax.get(common.data.getCollectionListUrl,function (r) {

                if (r.success){

                    var list = r.data;

                    list.forEach((e)=>{

                        e.createTime = moment(e.createTime).fromNow();
                    });
                    that.collectionList = list;
                }else{
                    alert(r.msg);
                }
            },{page:1,length:10});

        }
        ,
        searchVideo:function () {

        }
        ,
        clickHandler:function (collection) {
            if (collection.collectionType == 1){
                window.location = '/article/'+collection.contentId;
            }
        }
    }
});
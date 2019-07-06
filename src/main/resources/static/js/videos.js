var video = new Vue({
    el: "#videos",
    data: {
        videoList: [],
        kw: '',
        page: 1,
        length: 20,
        engine: {},
        engineList: [],
        hotKw: [],
        enginePage: {}
    }
    ,
    created: function () {
        this.getEngines()
        this.getHotKw();
    }
    ,
    methods: {
        searchVideo: function () {
            if (this.kw) {

                var that = this;
                common.ajax.get(common.data.searchVideoUrl, function (r) {
                    if (r.success) {
                        var list = r.data;

                        if (list.length == 0) {
                            alert("没有更多数据");
                            return;
                        }

                        that.videoList = that.videoList.concat(list);
                    } else {
                        alert(r.msg);
                    }
                }, {
                    kw: this.kw,
                    engine: this.engine.code,
                    page: this.page,
                    length: this.length
                });
            } else {
                alert("关键词不得为空");
            }
        }
        ,
        getEngines: function () {

            var that = this;
            common.ajax.get(common.data.getVideoSearchEngineUrl, function (r) {
                if (r.success) {
                    that.engineList = r.data;
                    that.engine = that.engineList[0];
                } else {
                    alert(r.msg);
                }
            })
        }
        ,
        changeEngine: function (engine) {
            this.engine = engine;
            this.videoList = [];
            this.page =1;

        }
        ,
        getHotKw: function () {

            var that = this;
            common.ajax.get(common.data.getHotKwUrl, function (r) {
                if (r.success) {
                    that.hotKw = r.data;
                } else {
                    alert(r.msg);
                }
            })
        }
        ,
        changeKw: function (kw) {
            this.kw = kw;
        }
        ,
        getMore: function () {
            this.page++;
            this.searchVideo();

        }
    }
});
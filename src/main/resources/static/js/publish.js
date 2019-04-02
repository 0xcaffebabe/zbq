var publish = new Vue({
    el: "#publish",
    data: {
        editor: null,
        title: '', content: '', tags: [], rowTag: ''
    }
    ,
    mounted: function () {
        var E = window.wangEditor;
        this.editor = new E('#editor');
        this.editor.create();
    }
    ,
    methods: {

        processTag: function () {

            this.tags = this.tags.concat(this.rowTag);
            this.rowTag = '';
        }
        ,
        removeTag: function (index) {
            this.tags.splice(index, 1);
        }
        ,
        publishContent: function () {

            var obj = {
                title: this.title,
                content: this.editor.txt.html(),
                tags: this.tags.reduce(function (prev,cur,index,arr) {

                    if (index == arr.length-1){
                        return prev+cur;
                    }else{
                        return prev+cur+";";
                    }
                })
            };

            common.ajax.put(common.data.publishContentUrl,function (response) {
                if (response.success){
                    alert(response.data);
                }else{
                    alert(response.msg);
                }
            },obj);
        }
    }
});